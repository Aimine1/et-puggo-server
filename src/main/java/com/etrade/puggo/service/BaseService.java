package com.etrade.puggo.service;

import com.etrade.puggo.common.exception.CommonError;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.constants.UserRoleConstants;
import com.etrade.puggo.filter.AuthContext;
import com.etrade.puggo.utils.IpAddressDO;
import com.etrade.puggo.utils.IpUtils;
import com.etrade.puggo.utils.OptionalUtils;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author zhengbaole
 */
@Slf4j
@Service
public class BaseService {

    /**
     * 用来记录重要日志的Logger
     **/
    protected static final Logger coreLog = LoggerFactory.getLogger("coreLog");
    /**
     * 用来在排查问题时记录临时日志的Logger，排查完后需要删除日志输出
     **/
    protected static final Logger tempLog = LoggerFactory.getLogger("tempLog");

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;


    /**
     * 从缓存中拿当前员工名称
     *
     * @return 当前员工名称
     */
    protected String userName() {
        return OptionalUtils.valueOrDefault(AuthContext.getUserName());
    }

    protected long userId() {
        return OptionalUtils.valueOrDefault(AuthContext.getUserId());
    }

    public AuthDTO getAuthDTO() {

        AuthDTO authDTO = new AuthDTO();

        authDTO.setUserId(AuthContext.getUserId());
        authDTO.setUserName(AuthContext.getUserName());
        authDTO.setDeviceId(AuthContext.getDeviceId());
        return authDTO;
    }

    protected void setAuthDTO(AuthDTO authDTO) {
        AuthContext.setUserId(authDTO.getUserId());
        AuthContext.setUserName(authDTO.getUserName());
        AuthContext.setDeviceId(authDTO.getDeviceId());
    }

    /**
     * 设置authDTO并返回
     *
     * @return authDTO
     */
    protected AuthDTO cacheAuthDTO() {
        AuthDTO authDTO = getAuthDTO();
        setAuthDTO(authDTO);
        return authDTO;
    }


    /**
     * 是否admin账号
     */
    protected void isAdminRole() {
        boolean b = AuthContext.getUserRole().equals(UserRoleConstants.ADMIN);
        if (!b) {
            throw new ServiceException(CommonError.USER_PERMISSION_DENIED);
        }
    }


    /**
     * 当前事务提交后执行（异步线程内）
     *
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2021/10/26 4:17 PM
     * @editTime 2021/10/26 4:17 PM
     */
    protected void doAfterTxCommitted(Runnable runnable) {

        AuthDTO authDTO = getAuthDTO();

        TransactionSynchronizationManager
            .registerSynchronization(new TransactionSynchronizationAdapter() {

                @Override
                public void afterCommit() {
                    setAuthDTO(authDTO);
                    runnable.run();
                }
            });
    }


    /**
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2022/3/25 7:42 PM
     * @editTime 2022/3/25 7:42 PM
     */
    protected <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {

        AuthDTO authDTO = getAuthDTO();

        return CompletableFuture.supplyAsync(() -> {
            try {
                setAuthDTO(authDTO);
                return supplier.get();
            } catch (Exception e) {
                log.error("supplyAsync exception ", e);
                throw e;
            } finally {
                AuthContext.remove();
            }
        }, taskExecutor);
    }

    /**
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2022/3/28 10:18 AM
     * @editTime 2022/3/28 10:18 AM
     */
    protected CompletableFuture<Void> runAsync(Runnable runnable) {

        AuthDTO authDTO = getAuthDTO();

        return CompletableFuture.runAsync(() -> {
            try {
                setAuthDTO(authDTO);
                runnable.run();
            } catch (Exception e) {
                log.error("runAsync exception ", e);
                throw e;
            } finally {
                AuthContext.remove();
            }
        }, taskExecutor);
    }

    /**
     * 获取用户ip地址
     *
     * @param request HttpServletRequest
     * @return IpAddressDO
     */
    protected IpAddressDO getUserIpAddress(HttpServletRequest request) {
        IpAddressDO address = null;
        try {
            String ip = IpUtils.getIpAddress(request);
            address = IpUtils.getIpInfo(ip);
        } catch (Exception e) {
            log.error("[GOODS] 获取用户地址失败", e);
        }
        return address == null ? new IpAddressDO() : address;
    }

}
