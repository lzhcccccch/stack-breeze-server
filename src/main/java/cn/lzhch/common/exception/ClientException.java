package cn.lzhch.common.exception;


import cn.lzhch.common.response.ErrorCode;

import java.io.Serial;

/**
 * 客户端异常
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 14:46
 */

public class ClientException extends AbstractException {
    @Serial
    private static final long serialVersionUID = 7156770058390131002L;


    public ClientException() {
        this(ErrorCode.CLIENT_ERROR.getCode(), ErrorCode.CLIENT_ERROR.getMessage(), null);
    }

    public ClientException(Throwable throwable) {
        this(ErrorCode.CLIENT_ERROR.getCode(), ErrorCode.CLIENT_ERROR.getMessage(), throwable);
    }

    public ClientException(String message) {
        this(ErrorCode.CLIENT_ERROR.getCode(), message, null);
    }

    public ClientException(ErrorCode errorCode, Throwable throwable) {
        this(errorCode.getCode(), errorCode.getMessage(), throwable);
    }

    public ClientException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public ClientException(String code, String message, Throwable throwable) {
        super(code, message, throwable);
    }

}
