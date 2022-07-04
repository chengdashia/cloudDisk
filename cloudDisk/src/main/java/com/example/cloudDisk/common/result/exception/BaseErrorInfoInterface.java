package com.example.cloudDisk.common.result.exception;

/**
 * @author 成大事
 * @since 2022/6/13 21:35
 */
public interface BaseErrorInfoInterface {
    /**
     *  错误码
     * @return
     */
    String getResultCode();

    /**
     * 错误描述
     * @return
     */
    String getResultMsg();
}
