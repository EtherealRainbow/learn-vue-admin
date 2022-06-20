package com.learn.jwt.entity;


import io.jsonwebtoken.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.osgi.framework.ServiceException;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Date;
import java.util.List;


/**
 * date: 2021-01-05 08:48
 * description token管理
 *
 * @author lijun
 */
@Slf4j
@ApiModel("token提供者")
public class TokenProvider {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @ApiModelProperty("盐")
    private static final String SALT_KEY = "links";

    @ApiModelProperty("令牌有效期毫秒")
    private static final long TOKEN_VALIDITY = 86400000;

    @ApiModelProperty("权限密钥")
    private static final String AUTHORITIES_KEY = "auth";

    @ApiModelProperty("Base64 密钥")
    private final static String SECRET_KEY = Base64.getEncoder().encodeToString(SALT_KEY.getBytes(StandardCharsets.UTF_8));

    private static final String ISS = "leopard";
    /**
     * 生成token
     *
     * @param userId   用户id
     * @param username 用户名称
     * @param role     角色权限
     */
    public static String createToken(String userId, String username, String role) {
        Date validity = new Date((new Date()).getTime() + TOKEN_VALIDITY);
        return Jwts.builder()
                // 代表这个JWT的主体，即它的所有人
                .setSubject(username)
                // 代表这个JWT的签发主体
                .setIssuer(ISS)
                // 是一个时间戳，代表这个JWT的签发时间；
                .setIssuedAt(new Date())
                .setId(userId)
                // 代表这个JWT的接收对象
//                .setAudience(clientId)
                .claim("role", role)

                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 校验token
     */
    public static JwtUser checkToken(String token) {
        if (validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String userId = claims.getId();
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            JwtUser jwtUser = new JwtUser().setUserId(userId).setRole(role).setValid(true);
            log.info("当前登陆人：{},===token有效{}", username,jwtUser);
            return jwtUser;
        }
        log.error("***token无效***");
        return new JwtUser();
    }


    private static boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("无效的token：" + authToken);
        }
        return false;
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 从token中获取ID，同时做解密处理
     * @param token
     * @return
     */
    public static String getObjectId(String token){
        return getTokenBody(token).getId();
    }


    /**
     * 获取token信息，同时也做校验处理
     * @param token
     * @return
     */
    public static Claims getTokenBody(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch(ExpiredJwtException expired){
            //过期
            throw new ServiceException("token过期");
        } catch(MalformedJwtException malformedJwt){
            //无效
            throw new ServiceException("token无效");
        }
    }

}
