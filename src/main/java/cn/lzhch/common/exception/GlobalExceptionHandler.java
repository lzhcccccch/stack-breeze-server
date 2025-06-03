package cn.lzhch.common.exception;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.lzhch.common.response.ErrorCode;
import cn.lzhch.common.response.Result;
import cn.lzhch.common.response.ResultHelper;
import com.google.common.base.Throwables;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;


/**
 * 全局异常处理
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 15:06
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数验证异常
     */
    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Void> handleValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError firstFieldError = CollUtil.getFirst(bindingResult.getFieldErrors());
        String exceptionStr = Optional.ofNullable(firstFieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(CharSequenceUtil.EMPTY);

        log.error("MethodArgumentNotValidException: [{}] {} [ex] {}", request.getMethod(), request.getRequestURI(), Throwables.getStackTraceAsString(ex));
        return ResultHelper.fail(ErrorCode.CLIENT_ERROR.getCode(), exceptionStr);
    }

    /**
     * 处理认证异常
     */
    @ExceptionHandler(value = {AuthenticationException.class, BadCredentialsException.class})
    public Result<Void> handleAuthenticationException(HttpServletRequest request, AuthenticationException ex) {
        log.error("AuthenticationException: [{}] {} [ex] {}", request.getMethod(), request.getRequestURI(), Throwables.getStackTraceAsString(ex));
        return ResultHelper.fail(ErrorCode.USERNAME_PASSWORD_INCORRECT);
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = {AbstractException.class})
    public Result<Void> handleAbstractException(HttpServletRequest request, AbstractException ex) {
        String requestURL = request.getRequestURI();

        log.error("AbstractException: [{}] {} [ex] {}", request.getMethod(), requestURL, Throwables.getStackTraceAsString(ex));
        return ResultHelper.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(value = Throwable.class)
    public Result<Void> handleThrowable(HttpServletRequest request, Throwable throwable) {
        log.error("Throwable: [{}] {} ", request.getMethod(), request.getRequestURI(), throwable);
        return ResultHelper.fail();
    }

}
