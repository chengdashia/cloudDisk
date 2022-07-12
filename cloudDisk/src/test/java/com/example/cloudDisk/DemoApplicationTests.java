package com.example.cloudDisk;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.cloudDisk.mapper.UserInfoMapper;
import com.example.cloudDisk.pojo.UserInfo;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private UserInfoMapper userInfoMapper;

    @Test
    void test(){
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getUserId,"48e876253e7c401faabcc78c8550cd7e");
        System.out.println(userInfoMapper.selectOne(wrapper));

        wrapper.eq(UserInfo::getUserMail,"2030290987@qq.com");
        System.out.println(userInfoMapper.selectOne(wrapper));
    }


    @Test
    void contextLoads() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String endpoint = "http://10.111.43.55:9000";
        String accessKey = "admin";
        String secretKey = "12345678";
        String bucketName = "img";
        String objectName = "http://10.111.43.55:9000/img/48e876253e7c401faabcc78c8550cd7e/18.jpg";

        objectName = objectName.replace(StringUtils.chop("http://10.111.43.55:9000/img/"),"");
        log.info("objectName :{}",objectName);
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

        boolean isExists = false;
        try {
            minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            isExists = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        log.info("isEsists:  {}",isExists);

        //
        //minioClient.removeObject(
        //        RemoveObjectArgs.builder()
        //                .bucket(bucketName)
        //                .object(objectName)
        //                .build());
        System.out.println(b);


    }

}
