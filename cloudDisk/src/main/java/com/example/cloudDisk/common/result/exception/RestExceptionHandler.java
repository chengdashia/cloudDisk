package com.example.cloudDisk.common.result.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.example.cloudDisk.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @author 成大事
 * @since 2022/6/2 11:47
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {



    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public R<String> bizExceptionHandler(HttpServletRequest req, BaseException e){
        log.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return R.error(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public R<String> exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return R.error(ExceptionEnum.BODY_NOT_MATCH.getResultCode(),ExceptionEnum.BODY_NOT_MATCH.getResultMsg());
    }

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public R<String> exceptionHandler(HttpServletRequest req, Exception e){
        log.error("未知异常！原因是:",e);
        return R.error(ExceptionEnum.INTERNAL_SERVER_ERROR.getResultCode(),ExceptionEnum.INTERNAL_SERVER_ERROR.getResultMsg());
    }

    /**
     * 处理没登录异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public R<String> notLoginExceptionHandler(HttpServletRequest req, NotLoginException e){
        log.error("未知异常！原因是:",e);
        return R.error(e.getMessage());
    }

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public R<String> ConstraintViolationExceptionHandler(HttpServletRequest req, NotLoginException e){
        log.error("未知异常！原因是:",e);
        return R.error(e.getMessage());
    }




}
