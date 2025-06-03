package cn.lzhch.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册请求DTO
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/12/19
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    /**
     * 用户名
     * <p>
     * 校验规则：
     * - 不能为空
     * - 长度3-20个字符
     * - 只能包含字母、数字和下划线（防止SQL注入和XSS攻击）
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;

    /**
     * 邮箱
     * <p>
     * 校验规则：
     * - 不能为空
     * - 必须符合邮箱格式（使用标准的邮箱正则表达式）
     * - 长度不超过100个字符（数据库字段限制）
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;

    /**
     * 密码
     * <p>
     * 安全策略：
     * - 最小长度6位，最大50位
     * - 必须包含大写字母、小写字母、数字
     * - 允许特殊字符 @$!%*?& 增强密码强度
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度必须在6-50个字符之间")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{6,}$",
            message = "密码必须包含至少一个大写字母、一个小写字母和一个数字")
    private String password;

}
