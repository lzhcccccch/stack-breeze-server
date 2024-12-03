package cn.lzhch.common.exception;


import cn.lzhch.common.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.util.Optional;

/**
 * 异常抽象类
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 14:39
 */

@Getter
@AllArgsConstructor
public abstract class AbstractException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1897606977619421576L;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;


    public AbstractException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = Optional.ofNullable(message).orElse(ErrorCode.SERVICE_ERROR.getMessage());
    }

}
