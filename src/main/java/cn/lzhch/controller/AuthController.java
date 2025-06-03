package cn.lzhch.controller;

import cn.lzhch.dto.auth.UserLoginRequest;
import cn.lzhch.dto.auth.UserLoginResponse;
import cn.lzhch.dto.auth.UserProfileResponse;
import cn.lzhch.dto.auth.UserRegisterRequest;
import cn.lzhch.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证控制器
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/12/19
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final IUserService userService;

    /**
     * 用户注册接口
     *
     * @param request 用户注册请求，包含用户名、邮箱、密码
     * @return 注册成功的用户资料（不包含密码）
     */
    @PostMapping("/register")
    public UserProfileResponse register(/*@Valid*/ @RequestBody UserRegisterRequest request) {
        log.info("用户注册请求: {}", request.getUsername());
        return userService.register(request);
    }

    /**
     * 用户登录接口
     * <p>
     *
     * @param request 用户登录请求，包含用户名/邮箱和密码
     * @return 登录响应，包含JWT令牌和用户信息
     */
    @PostMapping("/login")
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest request) {
        log.info("用户登录请求: {}", request.getUsername());
        return userService.login(request);
    }

    /**
     * 获取当前用户资料接口
     * <p>
     * 认证机制：
     * 1. JWT过滤器验证Authorization头中的令牌
     * 2. 从令牌中提取用户名
     * 3. 设置Spring Security上下文
     * 4. 业务方法从上下文获取当前用户
     *
     * @return 当前登录用户的资料信息
     */
    @GetMapping("/profile")
    public UserProfileResponse getProfile() {
        log.info("获取当前用户资料");
        return userService.getCurrentUserProfile();
    }

}
