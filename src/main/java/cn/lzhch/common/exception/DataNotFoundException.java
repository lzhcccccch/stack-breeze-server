package cn.lzhch.common.exception;


import cn.lzhch.common.response.ErrorCode;

import java.io.Serial;

/**
 * 数据未找到异常
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 15:00
 */

public class DataNotFoundException extends BusinessException {

    @Serial
    private static final long serialVersionUID = -4872947393142624172L;


    public DataNotFoundException() {
        super(ErrorCode.SERVICE_DATA_NOT_FOUND.getCode(), ErrorCode.SERVICE_DATA_NOT_FOUND.getMessage(), null);
    }

    public DataNotFoundException(Throwable throwable) {
        super(ErrorCode.SERVICE_DATA_NOT_FOUND.getCode(), ErrorCode.SERVICE_DATA_NOT_FOUND.getMessage(), throwable);
    }

}
