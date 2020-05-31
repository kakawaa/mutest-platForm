package com.mutest.advice;

import com.mutest.model.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/8/9 11:55
 * @description
 * @modify
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 拦截业务异常，返回业务异常信息
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult handleBusinessError(BusinessErrorException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        return new JsonResult(code, message);
    }
}
