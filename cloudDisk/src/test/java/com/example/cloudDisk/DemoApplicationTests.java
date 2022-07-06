package com.example.cloudDisk;

import com.example.cloudDisk.utils.hdfs.HdfsConfig;
import com.example.cloudDisk.utils.hdfs.HdfsUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private HdfsConfig hdfsConfig;


    @Test
    void contextLoads() {
        //boolean b = HdfsUtil.checkFileExist("/165034235099284957cb857d04c12ae63cb9dab7287e5/董博阳的文件夹/y");
        //log.info("b：  {}",b);
        //
        //log.info(hdfsConfig.getPort());
        //log.info(hdfsConfig.getUrl());

        HdfsUtil.deleteAllFilesInThisFolder("/165034235099284957cb857d04c12ae63cb9dab7287e5m    ");

    }

}
