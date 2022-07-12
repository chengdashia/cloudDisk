package com.example.cloudDisk.common.result.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.example.cloudDisk.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author 成大事
 * @since 2022/6/2 11:47
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public R<String> exceptionHandler(Exception e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.INTERNAL_SERVER_ERROR.getResultCode()),ExceptionEnum.INTERNAL_SERVER_ERROR.getResultMsg());
    }

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public R<String> baseExceptionHandler(BaseException e){
        log.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return R.error(Integer.parseInt(e.getErrorCode()),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public R<String> exceptionHandler(NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.BODY_NOT_MATCH.getResultCode()),ExceptionEnum.BODY_NOT_MATCH.getResultMsg());
    }


    /**
     * 处理没登录异常
     */
    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public R<String> notLoginExceptionHandler(NotLoginException e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.NOT_FOUND.getResultCode()), ExceptionEnum.NOT_LOGIN.getResultMsg());
    }

    /**
     * 处理校验异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public R<String> constraintViolationExceptionHandler(ConstraintViolationException e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.CONSTRAINT_VIOLATION_EXCEPTION.getResultCode()),ExceptionEnum.CONSTRAINT_VIOLATION_EXCEPTION.getResultMsg());
    }



    /**
     * 处理请求参数异常
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public R<String> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e){
        log.error("未知异常！原因是:",e);
        return R.error(Integer.parseInt(ExceptionEnum.REQUEST_PARAMETER_EXCEPTION.getResultCode()),ExceptionEnum.REQUEST_PARAMETER_EXCEPTION.getResultMsg());
    }





}
