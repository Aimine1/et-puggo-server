package com.etrade.puggo.utils;

import com.etrade.puggo.filter.UserInfoDO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author crq JWT校验工具类
 * <ol>
 * <li>iss: jwt签发者</li>
 * <li>sub: jwt所面向的用户</li>
 * <li>aud: 接收jwt的一方</li>
 * <li>exp: jwt的过期时间，这个过期时间必须要大于签发时间</li>
 * <li>nbf: 定义在什么时间之前，该jwt都是不可用的</li>
 * <li>iat: jwt的签发时间</li>
 * <li>jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击</li>
 * </ol>
 */

public class JwtUtils {

    private final static Logger log = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * JWT 加解密类型
     */
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    /**
     * JWT 生成密钥使用的密码
     */
    private static final String SECRET = "8qqmG30XfQiLmTJZ";

    /**
     * JWT 添加至HTTP HEAD中的前缀
     */
    private static final String SEPARATOR = "Bearer ";

    /**
     * JWT 添加至PAYLOAD的签发者
     */
    private static final String ISSUE = "everything-tradeltd";

    /**
     * JWT 添加至PAYLOAD的有效期(秒) 24小时
     */
    private static final int TIMEOUT = 60 * 60 * 24;

    private JwtUtils() {
    }

    /**
     * 生成JWT
     */
    public static String genJwt(UserInfoDO user) {
        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>(16);

        addUserData(user, claims);

        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(new Date(currentTime))
            .setIssuer(ISSUE)
            .signWith(ALGORITHM, SECRET)
            .setClaims(claims)
            .setExpiration(new Date(currentTime + TIMEOUT * 1000))
            .compact();
    }


    /**
     * 获取token中的claims信息
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token);
    }

    public static String getSignature(String token) {
        try {
            return getJws(token).getSignature();
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 获取token中head信息
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeader(String token) {
        try {
            return getJws(token).getHeader();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        return getJws(token).getBody();
    }

    /**
     * 获取body某个值
     *
     * @param token
     * @param key
     * @return
     */
    public static Object getVal(String token, String key) {
        return getJws(token).getBody().get(key);
    }

    /**
     * 是否过期
     */
    public static boolean isExpired(String token) {
        try {
            Claims claimsBody = getClaimsBody(token);
            Date expiration = claimsBody.getExpiration();
            boolean before = expiration.before(new Date());

            return before;
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

    private static void addUserData(UserInfoDO user, Map<String, Object> claims) {
        Class<UserInfoDO> clazz = UserInfoDO.class;
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String value = null;
            try {
                value = String.valueOf(field.get(user));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            claims.put(field.getName(), value);
        }
    }

}
