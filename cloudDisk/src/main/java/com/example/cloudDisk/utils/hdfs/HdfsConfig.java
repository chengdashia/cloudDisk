package com.example.cloudDisk.utils.hdfs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 成大事
 * @since 2022/7/6 9:06
 */


@Component
public class HdfsConfig {
    /** hdfs nameNode连接URL*/
    @Value("${hdfs.url}")
    private String url;

    /**操作用户*/
    @Value("${hdfs.userName}")
    private String userName;

    /**操作存储节点路径*/
    @Value("${hdfs.port}")
    private String port;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
