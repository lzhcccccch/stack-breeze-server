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
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/12/19
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
     * @param request 注册请求
     * @return 用户资料
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
     * @param request 登录请求
     * @return 登录响应
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
