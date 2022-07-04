package com.example.cloudDisk.utils.Constants;

/**
 * @author 成大事
 * @date 2022/3/20 16:41
 */
public class Constant {

    //word文档类
    public static final int WORD_TYPE = 1;

    //音乐类
    public static final int AUDIO_TYPE = 2;

    //视频类
    public static final int VIDEO_TYPE = 3;

    //图片类
    public static final int IMAGE_TYPE = 4;

    //其他类型
    public static final int OTHER_TYPE = 5;



    //文件夹
    public static final int FOLDER = 1;

    //文件
    public static final int FILE = 2;


    //逻辑删除
    public static final int DEL = 0;

    //存在
    public static final int NOT_DEL = 1;


    //私密
    public static final int PRIVATE_TYPE = 0;

    //公开
    public static final int PUBLIC_TYPE = 1;

    //在售
    public static final int SALE_TYPE = 2;

    //商家
    public static final int BUSINESS = 1;

    //初始化了
    public static final int INIT = 1;

    //没有初始化
    public static final int NOT_INIT = 0;

    //获取浏览记录list
    public static String getHistoryList(String uuid){
    	return uuid+":historyList:";
    }

    //获取浏览记录set
    public static String getHistorySet(String uuid){
        return uuid+":historySet:";
    }

    //获取下载路径
    public static String getDownLoadPath(String filePath){
        String localUrl="http://hadoop";
        String port="9864";
        String node=Integer.toString((int)(Math.random() *3) + 1);
        return  localUrl+node+":"+port+"/webhdfs/v1"+filePath+"?op=OPEN&namenoderpcaddress=hadoop1:8020&offset=0";
    }

}
