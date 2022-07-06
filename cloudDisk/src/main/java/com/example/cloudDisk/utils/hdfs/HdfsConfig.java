package com.example.cloudDisk.utils.hdfs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author 成大事
 * @since 2022/7/6 9:06
 */
@Data
@Configuration
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
}
