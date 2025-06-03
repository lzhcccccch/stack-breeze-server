package cn.lzhch.service.impl;

import cn.lzhch.common.exception.ClientException;
import cn.lzhch.common.response.ErrorCode;
import cn.lzhch.dto.auth.UserLoginRequest;
import cn.lzhch.dto.auth.UserLoginResponse;
import cn.lzhch.dto.auth.UserProfileResponse;
import cn.lzhch.dto.auth.UserRegisterRequest;
import cn.lzhch.entity.User;
import cn.lzhch.mapper.UserMapper;
import cn.lzhch.security.JwtTokenProvider;
import cn.lzhch.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 *
 * 核心业务逻辑：
 * 1. 用户注册：参数校验 → 唯一性检查 → 密码加密 → 数据保存
 * 2. 用户登录：认证验证 → JWT生成 → 用户信息返回
 * 3. 用户查询：支持用户名、邮箱、组合查询
 * 4. 当前用户：从Security上下文获取当前登录用户
 *
 * 设计原则：
 * - 事务管理：注册操作使用事务，保证数据一致性
 * - 异常处理：使用自定义异常，提供明确的错误信息
 * - 安全考虑：密码加密存储，敏感信息不返回给前端
 * - 日志记录：关键操作记录日志，便于问题排查
 *
 * 技术特点：
 * - 继承ServiceImpl：获得MyBatis-Plus的基础CRUD功能
 * - 依赖注入：使用构造器注入，保证依赖的不可变性
 * - 密码安全：BCrypt加密，每次加密结果都不同
 * - JWT集成：与Spring Security无缝集成
 *
 * @author lzhch
 * @version v1.0
 * @date 2024/12/19
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    /**
     * 用户注册
     *
     * 业务流程：
     * 1. 参数校验（Controller层的@Valid已完成基础校验）
     * 2. 业务唯一性校验（用户名、邮箱不能重复）
     * 3. 密码加密（使用BCrypt，每次结果都不同）
     * 4. 数据保存（设置默认值，记录创建时间）
     * 5. 返回用户信息（不包含密码等敏感信息）
     *
     * 安全考虑：
     * - 密码明文立即加密，不在内存中长时间保留
     * - 返回的用户信息不包含密码字段
     * - 使用事务确保数据一致性
     *
     * 异常处理：
     * - 用户名重复：抛出USERNAME_ALREADY_EXISTS异常
     * - 邮箱重复：抛出EMAIL_ALREADY_EXISTS异常
     * - 数据库异常：由全局异常处理器处理
     */
    @Override
    @Transactional
    public UserProfileResponse register(UserRegisterRequest request) {
        // 检查用户名是否已存在
        if (existsByUsername(request.getUsername())) {
            throw new ClientException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        // 检查邮箱是否已存在
        if (existsByEmail(request.getEmail())) {
            throw new ClientException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // 创建新用户对象
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // BCrypt加密
                .delFlag("0") // 设置为未删除状态
                .createTime(LocalDateTime.now()) // 设置创建时间
                .build();

        // 保存用户到数据库
        save(user);

        log.info("用户注册成功: {}", request.getUsername());

        // 构建并返回用户资料（不包含敏感信息）
        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createTime(user.getCreateTime())
                .updateTime(user.getUpdateTime())
                .build();
    }

    /**
     * 用户登录
     *
     * 认证流程：
     * 1. 使用Spring Security的AuthenticationManager进行认证
     * 2. 认证成功后生成JWT令牌
     * 3. 查询用户完整信息
     * 4. 构建登录响应（包含令牌和用户信息）
     *
     * 技术细节：
     * - 支持用户名或邮箱登录（在CustomUserDetailsService中实现）
     * - 密码验证由Spring Security自动完成
     * - JWT令牌包含用户名和过期时间
     * - 返回Bearer类型令牌，符合OAuth2标准
     *
     * 安全特性：
     * - 认证失败会抛出BadCredentialsException
     * - 密码验证使用BCrypt，防止时序攻击
     * - JWT签名防止令牌被篡改
     *
     * 异常处理：
     * - 认证失败：由全局异常处理器捕获并返回统一错误
     * - 用户不存在：在UserDetailsService中处理
     */
    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        // 使用Spring Security进行用户认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(), // 用户名或邮箱
                        request.getPassword() // 明文密码，由Spring Security验证
                )
        );

        // 认证成功，生成JWT令牌
        String jwt = tokenProvider.generateToken(authentication);

        // 查询用户完整信息（用于返回给前端）
        User user = findByUsernameOrEmail(request.getUsernameOrEmail());
        UserProfileResponse userProfile = UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createTime(user.getCreateTime())
                .updateTime(user.getUpdateTime())
                .build();

        log.info("用户登录成功: {}", request.getUsernameOrEmail());

        // 构建登录响应
        return UserLoginResponse.builder()
                .accessToken(jwt) // JWT访问令牌
                .tokenType("Bearer") // 令牌类型，符合OAuth2标准
                .expiresIn((long) tokenProvider.getJwtExpirationInSeconds()) // 过期时间（秒）
                .userInfo(userProfile) // 用户基本信息
                .build();
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email).orElse(null);
    }

    @Override
    public User findByUsernameOrEmail(String usernameOrEmail) {
        return userMapper.findByUsernameOrEmail(usernameOrEmail).orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userMapper.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    @Override
    public UserProfileResponse getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ClientException(ErrorCode.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = findByUsername(userDetails.getUsername());
        
        if (user == null) {
            throw new ClientException(ErrorCode.USER_NOT_FOUND);
        }

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createTime(user.getCreateTime())
                .updateTime(user.getUpdateTime())
                .build();
    }

}
