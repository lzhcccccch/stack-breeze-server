package cn.lzhch.common.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 14:31
 */

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 错误码通常是一个包含5个字符的字符串，它分为两部分：错误来源标识（1个字符）和错误编号（4个数字）。错误来源标识可以是A、B或C：
     * <p>
     * A 表示错误源于用户，例如参数错误、版本过低或支付超时。
     * B 表示错误源于当前系统，通常是由于业务逻辑错误或程序健壮性不足。
     * C 表示错误源于第三方服务，例如 CDN 服务故障或消息投递超时。
     * 错误编号是一个在0001到9999之间的四位数，用于进一步细化错误的类别。
     */

    OK("00000", "操作已成功"),

    /**
     * 客户端错误
     */
    CLIENT_ERROR("A0001", "客户端错误"),
    USER_NOT_FOUND("A0010", "用户不存在"),
    USER_ALREADY_EXISTS("A0011", "用户已存在"),
    USERNAME_PASSWORD_INCORRECT("A0012", "用户名或密码错误"),
    VERIFICATION_CODE_EXPIRED("A0013", "验证码已过期"),
    BAD_CREDENTIALS_EXPIRED("A0014", "用户认证异常"),
    USERNAME_ALREADY_EXISTS("A0015", "用户名已存在"),
    EMAIL_ALREADY_EXISTS("A0016", "邮箱已存在"),
    INVALID_TOKEN("A0017", "无效的令牌"),
    TOKEN_EXPIRED("A0018", "令牌已过期"),
    UNAUTHORIZED("A0019", "未授权访问"),

    /**
     * 服务端错误
     */
    SERVICE_ERROR("B0001", "系统内部错误"),
    SERVICE_TIMEOUT_ERROR("B0010", "系统执行超时"),
    SERVICE_DATA_NOT_FOUND("B0011", "数据不存在"),

    /**
     * 第三方服务错误
     */
    REMOTE_ERROR("C0001", "第三方服务错误");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

}
