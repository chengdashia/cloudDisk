package com.example.cloudDisk;

import com.example.cloudDisk.utils.hdfs.HdfsUtil;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private HdfsUtil hdfsUtil;


    @Test
    void contextLoads() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String endpoint = "http://10.111.43.55:9000";
        String accessKey = "admin";
        String secretKey = "12345678";
        String bucketName = "file";
        MinioClient build = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        boolean b = build.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        System.out.println(b);


    }

}
