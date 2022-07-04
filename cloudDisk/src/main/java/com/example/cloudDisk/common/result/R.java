package com.example.cloudDisk.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 成大事
 * @date 2022/1/2 15:30
 */
@Data
@AllArgsConstructor
public class R<T> implements Serializable {

    /**
     * 是否返回成功
     */
    private boolean success;

    /**
     * 状态码
     */
    private int code;

    /***
     * 信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Date time ;


    public R (){
        this.time = new Date();
    }
    /**
     * 成功的操作
     */
    public static <T> R<T> ok() {
        return  ok(null);
    }

    /**
     * 成 功 操 作 , 携 带 数 据
     */
    public static <T> R<T> ok(T data){
        return ok(ResultCode.SUCCESS.getMessage(),data);
    }

    /**
     * 成 功 操 作, 携 带 消 息
     */
    public static <T> R<T> ok(String message) {
        return ok(message, null);
    }

    /**
     * 成 功 操 作, 携 带 消 息 和 携 带 数 据
     */
    public static <T> R<T> ok(String message, T data) {
        return ok(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成 功 操 作, 携 带 自 定 义 状 态 码 和 消 息
     */
    public static <T> R<T> ok(int code, String message) {
        return ok(code, message, null);
    }

    public static <T> R<T> ok(int code, String message, T data) {
        R<T> result = new R<T>();
        result.setCode(code);
        result.setMsg(message);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * 失 败 操 作, 默 认 数 据
     */
    public static <T> R<T> error() {
        return error(ResultCode.FAILED.getMessage());
    }

    /**
     * 失 败 操 作, 携 带 自 定 义 消 息
     */
    public static <T> R<T> error(String message) {
        return error(message, null);
    }

    /**
     * 失 败 操 作, 携 带 自 定 义 消 息 和 数 据
     */
    public static <T> R<T> error(String message, T data) {
        return error(ResultCode.FAILED.getCode(), message, data);
    }

    /**
     * 失 败 操 作, 携 带 自 定 义 状 态 码 和 自 定 义 消 息
     */
    public static <T> R<T> error(int code, String message) {
        return error(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失 败 操 作, 携 带 自 定 义 状 态 码 , 消 息 和 数 据
     */
    public static <T> R<T> error(int code, String message, T data) {
        R<T> result = new R<T>();
        result.setCode(code);
        result.setMsg(message);
        result.setSuccess(false);
        result.setData(data);
        return result;
    }

    /**
     * Boolean 返 回 操 作, 携 带 默 认 返 回 值
     */
    public static <T> R<T> decide(boolean b) {
        return decide(b, ResultCode.FAILED.getMessage(), ResultCode.FAILED.getMessage());
    }

    /**
     * Boolean 返 回 操 作, 携 带 自 定 义 消 息
     */
    public static <T> R<T> decide(boolean b, String success, String failure) {
        if (b) {
            return ok(success);
        } else {
            return error(failure);
        }
    }
}
