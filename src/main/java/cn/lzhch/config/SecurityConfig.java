package cn.lzhch.config;

import cn.lzhch.security.CustomUserDetailsService;
import cn.lzhch.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 安全配置类
 * <p>
 * 配置说明：
 * 1. 无状态会话管理 - 适合JWT令牌认证
 * 2. 自定义认证提供者 - 使用数据库用户信息
 * 3. JWT过滤器集成 - 自动验证请求中的JWT令牌
 * 4. 路径权限控制 - 区分公开和受保护的接口
 * <p>
 * 安全策略：
 * - 禁用CSRF：因为使用JWT，不需要CSRF保护
 * - 无状态会话：不使用HttpSession，完全依赖JWT
 * - BCrypt密码加密：使用强哈希算法保护密码
 * - 自定义过滤器：在标准认证过滤器前添加JWT验证
 * <p>
 * 相比传统Session方案的优势：
 * 1. 无状态：服务器不需要存储会话信息，便于水平扩展
 * 2. 跨域友好：JWT可以在不同域名间传递
 * 3. 移动端友好：不依赖Cookie，适合移动应用
 * 4. 微服务架构：JWT可以在多个服务间共享认证信息
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/12/19
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 密码编码器Bean
     * <p>
     * 选择BCrypt的原因：
     * 1. 自适应哈希算法，可以调整计算复杂度
     * 2. 内置盐值生成，每次加密结果都不同
     * 3. 时间复杂度高，抵抗彩虹表攻击
     * 4. Spring Security官方推荐
     * <p>
     * 相比MD5/SHA1的优势：
     * - 抗暴力破解能力更强
     * - 自动处理盐值，避免开发者犯错
     * - 算法成熟稳定，广泛使用
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 数据访问对象认证提供者
     * <p>
     * 功能：
     * 1. 整合自定义UserDetailsService和密码编码器
     * 2. 提供完整的用户认证逻辑
     * 3. 支持密码验证和用户状态检查
     * <p>
     * 工作流程：
     * 1. 接收用户名和密码
     * 2. 通过UserDetailsService加载用户信息
     * 3. 使用PasswordEncoder验证密码
     * 4. 返回认证结果
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // 设置用户详情服务
        authProvider.setPasswordEncoder(passwordEncoder()); // 设置密码编码器
        return authProvider;
    }

    /**
     * 认证管理器Bean
     * <p>
     * 作用：
     * - 协调多个认证提供者
     * - 处理认证请求的入口点
     * - 在Controller中进行手动认证时使用
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 安全过滤器链配置
     * <p>
     * 这是Spring Security的核心配置，定义了整个应用的安全策略
     * <p>
     * 配置要点：
     * 1. CSRF禁用：JWT是无状态的，不需要CSRF保护
     * 2. 无状态会话：不创建HttpSession，完全依赖JWT
     * 3. 路径权限：精确控制哪些路径需要认证
     * 4. 过滤器顺序：JWT过滤器在标准认证过滤器之前执行
     * <p>
     * 路径设计说明：
     * - /api/auth/* : 认证相关接口，允许匿名访问
     * - /dailyLifeRecords/* : 业务接口，暂时开放（可根据需要调整）
     * - 其他路径：需要JWT认证
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // 禁用CSRF，JWT不需要
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 无状态会话
                .authorizeHttpRequests(authz -> authz
                        // 公开的认证端点 - 注册和登录不需要认证
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        // 其他公开端点 - 根据业务需要调整
                        .requestMatchers("/api/**").permitAll()
                        // 所有其他请求需要JWT认证
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider()) // 设置认证提供者
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 添加JWT过滤器

        return http.build();
    }

}
