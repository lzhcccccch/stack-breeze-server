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
     */
    @PostMapping(value = "/register")
    public UserProfileResponse register(@Valid @RequestBody UserRegisterRequest request) {
        log.info("用户注册请求: {}", request.getUsername());
        return userService.register(request);
    }

    /**
     * 用户登录接口
     */
    @PostMapping(value = "/login")
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest request) {
        log.info("用户登录请求: {}", request.getUsername());
        return userService.login(request);
    }

    /**
     * 获取当前用户资料接口
     */
    @GetMapping(value = "/profile")
    public UserProfileResponse getProfile() {
        log.info("获取当前用户资料");
        return userService.getCurrentUserProfile();
    }

}
