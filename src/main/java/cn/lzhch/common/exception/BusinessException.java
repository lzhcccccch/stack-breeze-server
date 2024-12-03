package cn.lzhch.common.exception;


import cn.lzhch.common.response.ErrorCode;

import java.io.Serial;

/**
 * 业务异常
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 15:01
 */

public class BusinessException extends AbstractException {
    @Serial
    private static final long serialVersionUID = 1814548112324468416L;


    public BusinessException() {
        this(ErrorCode.SERVICE_ERROR.getCode(), ErrorCode.SERVICE_ERROR.getMessage(), null);
    }

    public BusinessException(Throwable throwable) {
        this(ErrorCode.SERVICE_ERROR.getCode(), ErrorCode.SERVICE_ERROR.getMessage(), throwable);
    }

    public BusinessException(String message) {
        this(ErrorCode.SERVICE_ERROR.getCode(), message, null);
    }

    public BusinessException(ErrorCode errorCode, Throwable throwable) {
        this(errorCode.getCode(), errorCode.getMessage(), throwable);
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public BusinessException(String code, String message, Throwable throwable) {
        super(code, message, throwable);
    }

}
