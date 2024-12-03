package cn.lzhch.common.response;


import lombok.*;
import lombok.experimental.Accessors;

/**
 * 返回结果
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 14:13
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result<T> {

    /**
     * 成功状态码
     */
    public static final String SUCCESS_CODE = "0";

    /**
     * 成功消息
     */
    public static final String SUCCESS_MESSAGE = "请求成功";

    /**
     * 状态码
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 时间戳
     */
    private long timestamp;

}
