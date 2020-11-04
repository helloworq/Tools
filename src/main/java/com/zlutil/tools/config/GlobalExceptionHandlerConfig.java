package com.zlutil.tools.config;

import com.zlutil.tools.toolpackage.ResponseUtil.ResponseData;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 参数校验工具-全局异常捕获处理类，此处作用是只提取注解里的message信息，剔除其余信息
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerConfig {
    /**
     * 方法参数校验
     */
    @ExceptionHandler(BindException.class)
    public ResponseData handleMethodArgumentNotValidException(BindException e) {
        log.error(e.getMessage(), e);
        return ResponseUtil.success(e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
