package com.etrade.puggo.service.account;

import com.etrade.puggo.common.exception.CommonError;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.constants.ClientConstants;
import com.etrade.puggo.constants.RedisKeys;
import com.etrade.puggo.constants.RequestHeaders;
import com.etrade.puggo.constants.UserRoleConstants;
import com.etrade.puggo.dao.user.UserAccountDao;
import com.etrade.puggo.dao.user.UserDao;
import com.etrade.puggo.dao.user.UserFansDao;
import com.etrade.puggo.dao.user.UserIMActionDao;
import com.etrade.puggo.db.tables.records.UserAccountRecord;
import com.etrade.puggo.db.tables.records.UserImActionRecord;
import com.etrade.puggo.common.filter.AuthContext;
import com.etrade.puggo.common.filter.DeviceInfoDO;
import com.etrade.puggo.common.filter.UserInfoDO;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.pojo.*;
import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.etrade.puggo.third.im.YunxinIMApi;
import com.etrade.puggo.utils.EmailUtils;
import com.etrade.puggo.utils.JsonUtils;
import com.etrade.puggo.utils.OptionalUtils;
import com.etrade.puggo.utils.PBKDF2Utils;
import com.etrade.puggo.utils.RedisUtils;
import com.etrade.puggo.utils.StrUtils;
import com.etrade.puggo.utils.VerifyCodeUtils;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author niuzhenyu
 * @description : 用户账号
 * @date 2023/5/22 10:42
 **/
@Slf4j
@Service
public class UserAccountService extends BaseService {

    public static final byte ACCOUNT_ACTIVE = 0;

    @Resource
    private UserDao userDao;
    @Resource
    private UserAccountDao userAccountDao;
    @Resource
    private UserIMActionDao userIMActionDao;
    @Resource
    private PBKDF2Utils pbkdf2Utils;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private EmailUtils emailUtils;
    @Resource
    private AuthorizeService authorizeService;
    @Resource
    private RecordLoginInfoService recordLoginInfoService;
    @Resource
    private YunxinIMApi yunxinIMApi;
    @Resource
    private UserFansDao userFansDao;


    /**
     * 用户登录
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/22 10:57
     * @editTime 2023/5/22 10:57
     **/
    @Transactional(rollbackFor = Throwable.class)
    public LoginResponse login(LoginAccountParam param, HttpServletRequest request) {

        String account = OptionalUtils.valueOrDefault(param.getAccount());
        String password = OptionalUtils.valueOrDefault(param.getPassword());
        DeviceInfoDO device = param.getDeviceInfo();
        String deviceId = Objects.isNull(device) ? request.getHeader(RequestHeaders.DEVICE) : device.getDeviceId();
        String client = request.getHeader(RequestHeaders.CLIENT);

        UserAccountRecord userAccount = getUserAccount(account);

        if (!userAccount.getDeleted().equals(ACCOUNT_ACTIVE)) {
            throw new ServiceException(CommonError.USER_ACCOUNT_NOT_ALLOWED);
        }

        try {
            boolean auth = pbkdf2Utils.authenticate(password, userAccount.getPassword(), userAccount.getSalt());
            if (!auth) {
                throw new ServiceException(CommonError.USER_LOGIN_FAILURE);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("[AUTH]密码验证失败: account:{},password:{}", account, password);
            throw new ServiceException(CommonError.GLOBAL_ERROR);
        }

        long userId = userAccount.getUserId();

        UserInfoVO user = userDao.findUserInfo(userId);

        String nickname = user.getNickname();
        String role = user.getUserRole();

        UserInfoDO userInfoDO = new UserInfoDO()
            .setUserId(userId).setNickname(nickname).setDeviceId(deviceId).setRole(role);

        log.info("[AUTH]登录客户端：{}", client);
        log.info("[AUTH]登录用户信息：{}", userInfoDO);

        String token = authorizeService.auth(client, userInfoDO);

        boolean isWeb = ClientConstants.PC.equals(client);

        if (isWeb) {
            // 检查是否有权限登录
            if (!UserRoleConstants.ADMIN.equals(role) && !UserRoleConstants.OPERATOR.equals(role)) {
                throw new ServiceException(CommonError.USER_NOT_ALLOWED_LOGIN_IN);
            }
        }

        String deviceJson = isWeb ? "{}" : JsonUtils.toJson(device);
        log.info("[AUTH]登录App设备信息：{}", deviceJson);

        // 记录最近一次登录IP、时间、地点、设备信息
        recordLoginInfoService.lastLogin(request, deviceJson, userAccount.getId());

        // 查询im token
        UserImActionRecord imActionRecord = userIMActionDao.findIMAction(userId);
        String imToken = "";
        String imAccid = "";

        try {
            if (imActionRecord != null) {
                imToken = imActionRecord.getToken();
                imAccid = imActionRecord.getAccid();
                if (StrUtils.isBlank(imToken)) {
                    imToken = yunxinIMApi.refreshToken(imActionRecord.getAccid(), userId);
                }
            } else {
                // 重新注册
                String accid = yunxinIMApi.randomAccid(nickname);
                UserImActionRecord action = yunxinIMApi.createAction(accid, nickname, userId);
                if (action != null) {
                    imToken = action.getToken();
                    imAccid = accid;
                }
            }
        } catch (Exception e) {
            log.error("登录时获取imToken失败", e);
        }

        return LoginResponse.builder().userId(userId).nickname(nickname).token(token).imToken(imToken).imAccid(imAccid)
            .build();
    }


    private UserAccountRecord getUserAccount(String account) {
        UserAccountRecord userAccount;

        userAccount = userAccountDao.findUserAccount(account);
        if (userAccount == null) {
            // 可能账号是昵称
            Long userId = userDao.findUserIdByNickname(account);
            if (userId != null) {
                userAccount = userAccountDao.findUserAccount(userId);
            }
        }

        if (userAccount == null) {
            throw new ServiceException(CommonError.USER_LOGIN_FAILURE);
        }
        return userAccount;
    }


    /**
     * 用户登出
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/22 11:50
     * @editTime 2023/5/22 11:50
     **/
    public void logout(HttpServletRequest request) {
        // 删除redis token信息
        long userId = AuthContext.getUserId();
        redisUtils.del(RedisKeys.USER_LOGIN_TOKEN_PREFIX + userId);
    }


    /**
     * 注销用户
     *
     * @param request
     */
    public void delete(HttpServletRequest request) {
        // 删除redis token信息
        long userId = AuthContext.getUserId();
        redisUtils.del(RedisKeys.USER_LOGIN_TOKEN_PREFIX + userId);

        // 注销用户
        userDao.delete(userId);
        userAccountDao.delete(userId);
    }


    /**
     * 验证昵称是否存在
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/22 14:59
     * @editTime 2023/5/22 14:59
     **/
    public void verifyNicknameExists(String nickname) {
        Long userId = userDao.findUserIdByNickname(nickname);

        if (userId != null && userId != 0L) {
            throw new ServiceException(CommonError.USER_ALREADY_EXISTS);
        }
    }


    /**
     * 验证邮箱是否存在
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/22 14:59
     * @editTime 2023/5/22 14:59
     **/
    public void verifyEmailExists(String email) {
        Long userId = userDao.findUserIdByEmail(email);

        if (userId != null && userId != 0L) {
            throw new ServiceException(CommonError.USER_EMAIL_ALREADY_EXISTS);
        }
    }


    /**
     * 注册并登录
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/22 15:07
     * @editTime 2023/5/22 15:07
     **/
    @Transactional(rollbackFor = Throwable.class)
    public void register(RegisterAccountParam param) {

        String nickname = OptionalUtils.valueOrDefault(param.getNickname());
        String email = OptionalUtils.valueOrDefault(param.getEmail());
        String password = OptionalUtils.valueOrDefault(param.getPassword());
        String confirmPassword = OptionalUtils.valueOrDefault(param.getConfirmPassword());

        if (!password.equals(confirmPassword)) {
            throw new ServiceException(CommonError.ENTERED_PASSWORD_DIFFER);
        }

        // 1. 验证用户昵称是否存在
        verifyNicknameExists(nickname);

        // 1.1. 验证用户邮箱是否存在
        verifyEmailExists(email);

        // 2. 注册用户，返回userId
        Long userId = userDao.insertNewUser(nickname, email, UserRoleConstants.USER);

        // 3.1. 生成密码
        String salt;
        String encryptedPassword;
        try {
            salt = pbkdf2Utils.generateSalt();
            encryptedPassword = pbkdf2Utils.getEncryptedPassword(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("[AUTH]密码加密失败: account:{},password:{}", email, password);
            throw new ServiceException(CommonError.GLOBAL_ERROR);
        }

        UserAccountParam userAccount = UserAccountParam.builder()
            .userId(userId).account(email).password(encryptedPassword).salt(salt).build();

        // 3.2 注册登录账号
        userAccountDao.insertNewAccount(userAccount);

        // 4 注册网易云信账号
        String accid = yunxinIMApi.randomAccid(nickname);
        yunxinIMApi.createAction(accid, nickname, userId);
    }


    /**
     * 发送邮件验证码
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/22 18:34
     * @editTime 2023/5/22 18:34
     **/
    @Async
    public void sendVerificationCode(String email, Byte type) {
        // 验证码生成
        String code = VerifyCodeUtils.generate();

        boolean b = Objects.equals(type, (byte) 1) ? emailUtils.SendEmailVerifyCodeForRegister(email, code)
            : emailUtils.SendEmailVerifyCodeForRetrievePassword(email, code);

        if (b) {
            log.info("[SEND VERIFY CODE]验证码发送成功 {}", code);
            // code 存入redis
            redisUtils.set(String.format(RedisKeys.VERIFY_CODE_KEY, email), code, 15 * 60);
        } else {
            throw new ServiceException(CommonError.GLOBAL_ERROR);
        }

        log.info("[SEND VERIFY CODE]code存入redis成功 {}", code);
    }


    /**
     * 验证邮箱验证码
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/22 18:40
     * @editTime 2023/5/22 18:40
     **/
    public void verifyCode(String email, String code) {
        String key = String.format(RedisKeys.VERIFY_CODE_KEY, email);
        String codeFromRedis = redisUtils.getString(key);

        if (codeFromRedis == null || !Objects.equals(codeFromRedis, code)) {
            throw new ServiceException(CommonError.USER_VERIFICATION_CODE_ERROR);
        }

        redisUtils.del(key);
    }


    /**
     * 重置密码
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/22 19:37
     * @editTime 2023/5/22 19:37
     **/
    public void resetPassword(ResetPasswordParam param) {

        String account = OptionalUtils.valueOrDefault(param.getAccount());
        String oldPassword = OptionalUtils.valueOrDefault(param.getOldPassword());
        String newPassword = OptionalUtils.valueOrDefault(param.getNewPassword());
        String confirmPassword = OptionalUtils.valueOrDefault(param.getConfirmPassword());

        UserAccountRecord userAccount = getUserAccount(account);

        try {
            if (!pbkdf2Utils.authenticate(oldPassword, userAccount.getPassword(), userAccount.getSalt())) {
                throw new ServiceException(CommonError.USER_ACCOUNT_OLD_PASSWORD_ERROR);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new ServiceException(CommonError.ENTERED_PASSWORD_DIFFER);
        }

        if (oldPassword.equals(newPassword)) {
            throw new ServiceException(CommonError.NEW_PASSWORD_IS_SAME_OLD);
        }

        String salt;
        String encryptedPassword;
        try {
            salt = pbkdf2Utils.generateSalt();
            encryptedPassword = pbkdf2Utils.getEncryptedPassword(newPassword, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("[RESET PASSWORD]密码加密失败: account:{},password:{}", account, newPassword);
            throw new ServiceException(CommonError.GLOBAL_ERROR);
        }

        userAccountDao.updatePassword(userAccount.getId(), encryptedPassword, salt);

    }


    /**
     * 找回密码
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/22 20:53
     * @editTime 2023/5/22 20:53
     **/
    public void retrievePassword(RetrievePasswordParam param) {

        String account = OptionalUtils.valueOrDefault(param.getAccount());
        String newPassword = OptionalUtils.valueOrDefault(param.getNewPassword());
        String confirmPassword = OptionalUtils.valueOrDefault(param.getConfirmPassword());

        UserAccountRecord userAccount = getUserAccount(account);

        if (!newPassword.equals(confirmPassword)) {
            throw new ServiceException(CommonError.ENTERED_PASSWORD_DIFFER);
        }

        String salt;
        String encryptedPassword;
        try {
            salt = pbkdf2Utils.generateSalt();
            encryptedPassword = pbkdf2Utils.getEncryptedPassword(newPassword, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("[RETRIEVE PASSWORD]密码加密失败: account:{},password:{}", account, newPassword);
            throw new ServiceException(CommonError.GLOBAL_ERROR);
        }

        userAccountDao.updatePassword(userAccount.getId(), encryptedPassword, salt);
    }

    public UserInfoVO getUserInfo() {
        UserInfoVO userInfo = userDao.findUserInfo(userId());

        Integer fansNum = userFansDao.findFansCount(userId());
        Integer followNum = userFansDao.findFollowCount(userId());
        userInfo.setFansNum(fansNum);
        userInfo.setFollowNum(followNum);

        return userInfo;
    }


    public SellerInfoVO getSellerInfo(Long userId) {
        return userDao.findSellerInfo(userId);
    }

    public int updateUserAvatar(String url) {
        return userDao.updateUserAvatar(userId(), url);
    }


    public List<LaunchUserDO> getUserList(List<Long> userIdList) {
        return userDao.findUserList(userIdList);
    }


    public PageContentContainer<UserInfoVO> getUserPageList(WebUserSearchParam param) {
        return userDao.findUserPageList(param);
    }


    /**
     * 用户验证
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/7/6 21:04
     * @editTime 2023/7/6 21:04
     **/
    public Integer verifyUser(Long userId) {
        return userDao.updateUserVerified(userId, (byte) 1);
    }
}
