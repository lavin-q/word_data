package com.elite.webdata.jwt;

import com.alibaba.fastjson.JSONObject;
import com.elite.webdata.util.AESSecretUtil;
import com.elite.webdata.util.SecretConstant;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * */

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

   /* *
     * 构建jwt
     * @param userId
     * @param username
     * @param role
     * @param audience
     * @return
     * */

    public static String createJWT(String userId, String username, String role, AudienceProperties audience){
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
     * */

    public static String getUserId(String token ,String base64Security) throws Exception {
        String userId = parseJWT(token, base64Security).get("userId", String.class);
        return Base64Util.encode(userId);
    }

    /**
     * 是否已过期
     * @param
     * @param
     * @return
     * */

    public static boolean isExpiration(String token, String base64Security) throws Exception {
        return parseJWT(token, base64Security).getExpiration().before(new Date());
    }

    /*public static String createJWT(String userName, String password) {
        //签名算法，选择SHA-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);
        //将BASE64SECRET常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecretConstant.BASE64SECRET);
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        Map<String, Object> headMap = new HashMap<>();
        *//*Header{ "alg": "HS256", "typ": "JWT"}*//*
        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headMap.put("typ", "JWT");
        JwtBuilder builder = Jwts.builder().setHeader(headMap)
                //加密后登录名
                .claim("userName", AESSecretUtil.encryptToStr(userName, SecretConstant.DATAKEY))
                //加密后的密码
                .claim("password", AESSecretUtil.encryptToStr(password, SecretConstant.DATAKEY))
                //客户端浏览器信息
                //.claim("userAgent", AESSecretUtil.encryptToStr(identities[0], SecretConstant.DATAKEY))
                //Signature
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    *//**
     * 解析JWT
     *
     * @param jsonWebToken jwt
     * @return
     *//*
    public static Claims parseJWT(String jsonWebToken) {
        Claims claims = null;
        try {
            if (StringUtils.isNotBlank(jsonWebToken)) {
                //解析jwt
                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SecretConstant.BASE64SECRET))
                        .parseClaimsJws(jsonWebToken).getBody();
            } else {
                logger.warn("[JWTHelper]-json web token 为空");
            }
        } catch (Exception e) {
            logger.error("[JWTHelper]-JWT解析异常：非法token");
        }
        return claims;
    }

    *//**
     * AES加密反转，验证token是否有效
     * @param jsonWebToken
     * @return
     *//*
    public static String validateLogin(String jsonWebToken) {
        Map<String, Object> resultMap = null;
        Claims claims = parseJWT(jsonWebToken);
        if (claims != null) {
            //解密用户姓名
            String decryptStudentId = AESSecretUtil.decryptToStr((String) claims.get("userName"), SecretConstant.DATAKEY);
            //解密用户密码
            String decryptStudentName = AESSecretUtil.decryptToStr((String) claims.get("password"), SecretConstant.DATAKEY);
            //String decryptUserAgent = AESSecretUtil.decryptToStr((String) claims.get("userAgent"),SecretConstant.DATAKEY);
            resultMap = new HashMap<>();
            //解密后的客户编号
            resultMap.put("userName", decryptStudentId);
            //客户名称
            resultMap.put("password", decryptStudentName);

        } else {
            logger.warn("[JWTHelper]-JWT解析出claims为空");
        }
        return resultMap != null ? JSONObject.toJSONString(resultMap) : new HashMap<String, Object>().toString();
    }


    public static void main(String[] args) {
        String qhm = createJWT("qhm", "123");
        System.out.println(qhm);

        System.out.println(validateLogin("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IkQ3RjcyREZFODQ5QUNFNkQ0NDk5MDA3QTlGQjU3M0ExIiwicGFzc3dvcmQiOiI0QzA0ODI5N0I5NEQ4NzlGMTE5QThDM0RDODA2RThBNSJ9.nmTuWhCq1-Xc0zevQyJo2v-YT86iR9NkuIq6AEu_180"));
    }*/


}