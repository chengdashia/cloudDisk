package com.example.cloudDisk.common.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 成大事
 * @since 2022/7/9 22:03
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endpoint;

    private String fileHost;

    private String bucketName;

    private String accessKey;

    private String secretKey;

    private Integer imgSize;

    private Integer fileSize;


    @Bean
    public MinioClient creatMinioClient() {
        return MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }


}
