package com.example.cloudDisk.controller.file;

import com.example.cloudDisk.common.minio.MinioConfig;
import com.example.cloudDisk.common.minio.MinioUtil;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.common.result.exception.BaseException;
import com.example.cloudDisk.common.result.exception.ExceptionEnum;
import com.example.cloudDisk.utils.hdfs.HdfsUtil;
import io.minio.ObjectWriteResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;

/**
 * @author 成大事
 * @since 2022/7/10 8:32
 */
@Api(tags = "测试minio 的文件上传")
@Slf4j
@Validated
@RestController
@RequestMapping("/fileUpload")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileController {

    private final MinioUtil minioUtil;

    private final HdfsUtil hdfsUtil;

    private final MinioConfig minioConfig;


    @PostMapping("/upload")
    public String testUpload(@RequestPart("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        minioUtil.uploadFile(minioConfig.getBucketName(), fileName, file.getInputStream());
        //返回图片链接
        String imgUrl = minioConfig.getFileHost()+"/"+minioConfig.getBucketName()+"/"+fileName;
        return imgUrl;
    }

    @PostMapping("/mkdir")
    public String mkdir(@RequestParam("folderName") String folderName) throws Exception {
        ObjectWriteResponse folder = minioUtil.createFolder(minioConfig.getBucketName(), folderName);
        return folder.toString();
    }

    @PostMapping("/upload2")
    public String testUpload2(@RequestPart("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        minioUtil.uploadFile(minioConfig.getBucketName(), "222/"+fileName, file.getInputStream());
        //返回图片链接
        String imgUrl = minioConfig.getFileHost()+"/"+minioConfig.getBucketName()+"/" + "222/"+fileName;
        return imgUrl;
    }

    @PostMapping("/upload3")
    public R testUpload3(@RequestPart("file") MultipartFile file) throws Exception {
        hdfsUtil.upload(file,"/1.jpg");
        return R.ok();
    }


    @PostMapping("/upload4")
    public R testUpload4(@Email @RequestParam("code") String code){
        if(code == null){
            return R.error();
        }else if("1847085602@qq.com".equals(code)){
            return R.ok();

        }else {
            throw new BaseException(ExceptionEnum.SIGNATURE_NOT_MATCH.getResultCode(),ExceptionEnum.SIGNATURE_NOT_MATCH.getResultMsg());
        }

    }
}
