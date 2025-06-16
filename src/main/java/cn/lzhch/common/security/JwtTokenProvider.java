package cn.lzhch.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT 令牌提供者
 * <p>
 * 核心功能：
 * 1. 生成JWT令牌 - 用户登录成功后生成访问令牌
 * 2. 验证JWT令牌 - 验证令牌的有效性和完整性
 * 3. 解析JWT令牌 - 从令牌中提取用户信息
 * <p>
 * 技术选型说明：
 * - 使用JJWT库：成熟稳定，API简洁，安全性高
 * - HMAC-SHA256签名：对称加密，性能好，适合单体应用
 * - 可配置密钥和过期时间：便于不同环境使用不同配置
 * <p>
 * 安全考虑：
 * - 密钥长度足够长，防止暴力破解
 * - 令牌有过期时间，降低泄露风险
 * - 完整的异常处理，防止信息泄露
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/12/19
 */

@Slf4j
@Component
public class JwtTokenProvider {

    /**
     * JWT签名密钥
     * 从配置文件读取，支持环境变量覆盖
     * 默认值足够长，确保安全性
     */
    @Value("${app.jwt.secret:mySecretKey123456789012345678901234567890}")
    private String jwtSecret;

    /**
     * JWT令牌过期时间（秒）
     * 默认24小时，平衡安全性和用户体验
     * -- GETTER --
     * 获取JWT令牌过期时间（秒）
     * <p>
     * 用途：
     * - 返回给前端，让前端知道令牌何时过期
     * - 用于刷新令牌的逻辑判断
     * - 便于监控和日志记录
     */
    @Getter
    @Value("${app.jwt.expiration:86400}")
    private int jwtExpirationInSeconds;

    /**
     * 获取JWT签名密钥
     * <p>
     * 使用HMAC-SHA256算法生成密钥
     * 确保密钥的安全性和一致性
     *
     * @return 签名密钥
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * 生成JWT令牌
     * <p>
     * 令牌结构：
     * - Header: 算法类型和令牌类型
     * - Payload: 用户名、签发时间、过期时间
     * - Signature: 使用密钥签名，确保令牌完整性
     * <p>
     * 安全特性：
     * - 包含过期时间，防止令牌永久有效
     * - 使用强签名算法，防止令牌伪造
     * - 不包含敏感信息，只存储用户名
     *
     * @param authentication Spring Security认证对象
     * @return JWT令牌字符串
     */
    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationInSeconds * 1000L);

        return Jwts.builder()
                .subject(userPrincipal.getUsername()) // 设置主题（用户名）
                .issuedAt(new Date()) // 设置签发时间
                .expiration(expiryDate) // 设置过期时间
                .signWith(getSigningKey()) // 使用密钥签名
                .compact(); // 生成最终的JWT字符串
    }

    /**
     * 从JWT令牌中获取用户名
     * <p>
     * 解析过程：
     * 1. 使用相同的密钥验证令牌签名
     * 2. 解析令牌的Payload部分
     * 3. 提取Subject字段（用户名）
     * <p>
     * 安全性：
     * - 验证签名确保令牌未被篡改
     * - 自动检查令牌是否过期
     *
     * @param token JWT令牌字符串
     * @return 用户名
     * @throws JwtException 如果令牌无效或过期
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey()) // 验证签名
                .build()
                .parseSignedClaims(token) // 解析令牌
                .getPayload(); // 获取载荷

        return claims.getSubject(); // 返回主题（用户名）
    }

    /**
     * 验证JWT令牌的有效性
     * <p>
     * 验证内容：
     * 1. 令牌格式是否正确
     * 2. 签名是否有效（防篡改）
     * 3. 令牌是否过期
     * 4. 令牌是否为空
     * <p>
     * 异常处理：
     * - 记录具体的错误类型，便于调试
     * - 不抛出异常，返回boolean，便于调用方处理
     * - 不在日志中输出令牌内容，防止信息泄露
     *
     * @param authToken JWT令牌字符串
     * @return true-令牌有效，false-令牌无效
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey()) // 使用密钥验证
                    .build()
                    .parseSignedClaims(authToken); // 解析并验证令牌
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token"); // 令牌格式错误
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token"); // 令牌已过期
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token"); // 不支持的令牌类型
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty"); // 令牌内容为空
        }
        return false;
    }

}
