package cn.lzhch.service;

import cn.lzhch.dto.auth.UserLoginRequest;
import cn.lzhch.dto.auth.UserLoginResponse;
import cn.lzhch.dto.auth.UserProfileResponse;
import cn.lzhch.dto.auth.UserRegisterRequest;
import cn.lzhch.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户表 Service
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/12/19
 */

public interface IUserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param request 注册请求
     * @return 用户资料
     */
    UserProfileResponse register(UserRegisterRequest request);

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应（包含JWT令牌）
     */
    UserLoginResponse login(UserLoginRequest request);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    User findByEmail(String email);

    /**
     * 根据用户名或邮箱查找用户
     *
     * @param usernameOrEmail 用户名或邮箱
     * @return 用户信息
     */
    User findByUsernameOrEmail(String usernameOrEmail);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 获取当前用户资料
     *
     * @return 用户资料
     */
    UserProfileResponse getCurrentUserProfile();

}
