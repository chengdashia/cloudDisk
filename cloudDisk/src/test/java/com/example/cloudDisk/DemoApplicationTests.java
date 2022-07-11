package com.example.cloudDisk;

import com.example.cloudDisk.utils.hdfs.HdfsUtil;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Slf4j
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private HdfsUtil hdfsUtil;


    @Test
    void contextLoads() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Date date = new Date();


    }

}
