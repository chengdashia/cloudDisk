package com.example.cloudDisk.common.result.project;

/**
 * @author 成大事
 * @since 2022/7/10 15:31
 */
public enum ConstantEnum {

    /**文件类型有误*/
    FILE_TYPE_ERROR(0,"文件类型有误"),
    /**暂时不支持当前格式上传！*/
    FILE_TYPE_NOT_SUPPORTED(-4,"暂时不支持当前格式上传！"),
    /**没有选择文件*/
    FILE_IS_NULL(-1,"没有选择文件！"),
    /**文件后缀被改过！*/
    SUFFIX_CHANGED(-2,"文件后缀被改过！"),
    /**菜单*/
    UPLOAD_ERROR(-3,"上传有误！"),
    /**本地路径*/
    LOCAL_PATH(0,"E:\\temp\\"),
    /** 路径分隔符 */
    SEPARATOR(0,"/"),
    /**按钮*/
    SUCCESS(1,"上传成功");

    private final int value;
    private final String msg;

    ConstantEnum(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }
    public int getValue() {
        return value;
    }
    public String getMsg() {
        return msg;
    }
}
