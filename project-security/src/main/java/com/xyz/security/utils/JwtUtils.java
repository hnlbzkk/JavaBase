package com.xyz.security.utils;

import com.alibaba.fastjson.JSON;
import com.xyz.db.redis.RedisUtil;
import com.xyz.security.model.entity.LoginUser;
import com.xyz.security.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static com.xyz.base.constant.Constant.LOGIN_PREFIX;

/**
 * JWT工具类
 */
public class JwtUtils {

    private static RedisUtil redisUtil;

    @Resource
    public void JwtUtils(RedisUtil redisUtil) {
        JwtUtils.redisUtil = redisUtil;
    }

    // todo: ttl
    /**
     * 有效期 24小时
     */
    private static final Long JWT_TTL = 24 * 60 * 60 * 1000L;

    // todo: Jwt Key
    /**
     * 设置秘钥
     */
    private static final String JWT_KEY = "Java_Base^JWT#2023@ZKKzs";


    /**
     * 生成jwt
     *
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT(String subject) {
        return getJwtBuilder(subject, JWT_TTL, getUUID()).compact();
    }

    /**
     * 生成jwt
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        return getJwtBuilder(subject, ttlMillis, getUUID()).compact();
    }

    /**
     * 生成jwt
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis, String id) {
        return getJwtBuilder(subject, ttlMillis, id).compact();
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static UserEntity parseJwtToUserEntity(String token) {
        // 解析 token
        Claims claims;
        try {
            claims = parseJWT(token);
        } catch (Exception e) {
            throw new RuntimeException("token验证失败");
        }
        LoginUser user = JSON.parseObject(claims.getSubject(), LoginUser.class);

        // 从 redis 中获取信息
        String redisKey = LOGIN_PREFIX + user.getUser().getEmail();
        LoginUser redisUser = redisUtil.getCacheObject(redisKey);
        if (Objects.isNull(redisUser)) {
            throw new RuntimeException("用户未登录");
        }
        return redisUser.getUser();
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.getMimeDecoder().decode(JwtUtils.JWT_KEY.replace("\r\n", ""));
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtils.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                // 唯一ID
                .setId(uuid)
                // 主题  可以是JSON数据
                .setSubject(subject)
                // 签发者
                .setIssuer("JavaBase")
                // 签发时间
                .setIssuedAt(now)
                //使用HS256对称加密算法签名, 第二个参数为秘钥
                .signWith(SignatureAlgorithm.HS256, generalKey())
                .setExpiration(expDate);
    }
}