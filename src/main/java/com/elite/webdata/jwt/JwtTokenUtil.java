package com.elite.webdata.jwt;

import io.jsonwebtoken.*;
import org.apache.logging.log4j.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * JWT工具类
 */
public class JwtTokenUtil {
    private static Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    public static final String AUTH_HEADER_KEY = "Authorization";

    //Bearer后的空格不可缺少
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 解析JWT
     *
     * @param jsonWebToken   token
     * @param base64Security 加密
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security){
        try {
            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (ExpiredJwtException e) {
            log.error("===== Token过期 =====", e);
            //抛出异常
            throw new CustomException(ResultCode.PERMISSION_TOKEN_EXPIRED);
        } catch (Exception e) {
            log.error("===== token解析异常 =====", e);
            throw new CustomException(ResultCode.PERMISSION_TOKEN_INVALID);
        }
    }

    /**
     * 构建jwt
     * @param userId
     * @param username
     * @param role
     * @param audience
     * @return
     */
    public static String createJWT(String userId, String username, String role, Audience audience){
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            //生成签名密钥
            byte[] apiKeySecretBytes  = DatatypeConverter.parseBase64Binary(audience.getBase64Secret());
            Key signingKey  = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            //userId是重要信息，进行加密下
            String encryId = Base64Util.encode(userId);
            JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("type", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("role", role)
                    .claim("userId", userId)
                    .setSubject(username)    // 代表这个JWT的主体，即它的所有人
                    .setIssuer(audience.getClientId())  // 代表这个JWT的签发主体
                    .setIssuedAt(new Date())    // 是一个时间戳，代表这个JWT的签发时间
                    .setAudience(audience.getName()) // 代表这个JWT的接收对象
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            int TTLMillis = audience.getExpiresSecond();
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                jwtBuilder.setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
                        .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
            }
            //生成JWT
            return jwtBuilder.compact();
        } catch (Exception e) {
            log.error("签名失败", e);
            throw new CustomException(ResultCode.PERMISSION_SIGNATURE_ERROR);
        }
    }

    /**
     * 从token中获取用户ID
     * @param token
     * @param base64Security
     * @return
     * @throws Exception
     */
    public static String getUserId(String token ,String base64Security) throws Exception {
        String userId = parseJWT(token, base64Security).get("userId", String.class);
        return Base64Util.encode(userId);
    }

    /**
     * 是否已过期
     * @param token
     * @param base64Security
     * @return
     */
    public static boolean isExpiration(String token, String base64Security) throws Exception {
        return parseJWT(token, base64Security).getExpiration().before(new Date());
    }

}