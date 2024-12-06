package cn.lzhch.common.response;


import static cn.lzhch.common.response.Result.SUCCESS_CODE;
import static cn.lzhch.common.response.Result.SUCCESS_MESSAGE;

/**
 * 返回结果
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 14:14
 */

public class ResultHelper {

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return success(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> success(String code, String message, T data) {
        return success(code, message, data, System.currentTimeMillis());
    }

    public static <T> Result<T> success(String code, String message, T data, long timestamp) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message)
                .setData(data)
                .setTimestamp(timestamp);
    }

    public static <T> Result<T> fail() {
        return fail(ErrorCode.SERVICE_ERROR.getCode(), ErrorCode.SERVICE_ERROR.getMessage());

    }

    public static <T> Result<T> fail(ErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMessage(), System.currentTimeMillis());
    }

    public static <T> Result<T> fail(String message) {
        return fail(ErrorCode.SERVICE_ERROR.getCode(), message, System.currentTimeMillis());
    }

    public static <T> Result<T> fail(String code, String message) {
        return fail(code, message, System.currentTimeMillis());
    }

    public static <T> Result<T> fail(String code, String message, long timestamp) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message)
                .setTimestamp(timestamp);

    }

}
