package com.example.cloudDisk.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.service.FileInfoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Slf4j
@Api(tags = "文件信息")
@RestController
@RequestMapping("/fileInfo")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileInfoController {

    private final FileInfoService fileInfoService;


    /**
     * 根据文件id获取文件信息
     * @param fileId  文件id
     * @return  R
     */
    @ApiOperation("根据文件id获取文件信息")
    @PostMapping("/getFileInfoById")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件id")
    })
    public R<Object> getFileInfoById(
            @NotBlank(message = "文件id不能为空") @RequestParam("fileId")  String fileId
    ){

        //获取文件信息 不要file_info_id,file_folder_id,file_status,file_del
        //还要有用户名，用户头像。用户id
        return fileInfoService.getFileInfoById(fileId);
    }



    /**
     * 根据页数获取文件信息列表（一页20个）
     * @param page  页码
     * @return  R
     */
    @ApiOperation("根据页数获取文件信息列表（一页20个）")
    @PostMapping("/getFileInfoListByPage")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="Page",dataTypeClass = int.class,required=true,value="页码")
    })
    public R<Object> getFileInfoListByPage(
            @ApiParam(value = "页码",required = true) @RequestParam("Page") @NotNull @Min(0) int page
    ){
        return fileInfoService.getFileInfoListByPage(page);
    }


    /**
     * 获取点击量最高的前十个文件信息
     * @return  R
     */
    @ApiOperation("获取点击量最高的前十个文件信息")
    @PostMapping("/getFileTopTen")
    public R<Object> getFileTopTen(){
        return fileInfoService.getFileTopTen();
    }


    /**
     * 随机获取十条文件信息
     * @return  R
     */
    @ApiOperation("随机获取十条文件信息")
    @PostMapping("/getFileRandomTen")
    public R<Object> getFileRandomTen(){
        return fileInfoService.getFileRandomTen();
    }


    /**
     * 上传文件
     * @param folderId             父文件夹id
     * @param file                 文件
     * @param labelList            标签列表
     * @return         R
     */
    @ApiOperation("上传文件")
    @PostMapping("/uploadFile")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="folderId",dataTypeClass = String.class,required=true,value="父文件夹id"),
            @ApiImplicitParam(paramType="query",name="fileName",dataTypeClass = String.class,required=true,value="文件名字"),
            @ApiImplicitParam(paramType="query",name="remarks",dataTypeClass = String.class,required=true,value="文件备注"),
            @ApiImplicitParam(paramType="form",name="file",dataTypeClass = MultipartFile.class,required=true,value="文件"),
            @ApiImplicitParam(paramType="query",name="labelList",dataTypeClass = ArrayList.class,value="标签列表")
    })
    public R<Object> uploadFile(
            @RequestParam("folderId") @NotBlank(message = "父文件夹id不能为空") String folderId,
            @RequestParam("fileName") @NotBlank(message = "文件名字不能为空") String fileName,
            @RequestParam(value = "remarks",required = false,defaultValue = "") String remarks,
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "labelList",required = false) List<String> labelList
    ){
        String id = (String) StpUtil.getLoginId();
        log.info("上传文件，用户id："+id);
        log.info("上传文件，父文件夹id："+folderId);
        log.info("上传文件，文件名字："+fileName);
        log.info("上传文件，标签列表："+labelList);
        log.info("上传文件，文件："+file.getOriginalFilename());
        return fileInfoService.uploadFile(folderId, file, labelList, fileName,remarks);
    }


    /**
     * 逻辑 删除文件
     * @param fileId  文件id
     * @return          R
     */
    @ApiOperation("逻辑 删除文件")
    @PostMapping("/delFile")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件的id")
    })
    public R<Object> delFile(
           @RequestParam("fileId") @NotBlank(message = "文件id") String fileId
    ){
        return fileInfoService.delFile(fileId);
    }


    /**
     * test upload 文件
     * @return          R
     */
    @ApiOperation("test upload 文件")
    @PostMapping("/testUploadFile")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form",name="file",dataTypeClass = MultipartFile.class,value="文件")
    })
    public R<Object> testUploadFile(
            @RequestPart("file") MultipartFile file
    ){

        System.out.println(file.getOriginalFilename());
        return null;
    }



    /**
     * 更改文件状态
     * @param fileId  文件id
     * @param fileStatus  文件id
     * @return          R
     */
    @ApiOperation("更改文件状态")
    @PostMapping("/updateFileStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件的id"),
            @ApiImplicitParam(paramType="query",name="fileStatus",dataTypeClass = Integer.class,required=true,value="文件状态")
    })
    public R<Object> updateFileStatus(
            @RequestParam("fileId") @NotBlank(message = "文件id") String fileId,
            @RequestParam("fileStatus") @Min(0) @Max(2) int fileStatus
    ){

        return fileInfoService.updateFileStatus(fileId,fileStatus);
    }


    /**
     * 根据文件夹id查询文件
     * @param folderId  文件夹id
     * @return          R
     */
    @ApiOperation("根据文件夹id查询文件")
    @PostMapping("/getFileInfoByFolderId")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="folderId",dataTypeClass = String.class,required=true,value="文件夹id")
    })
    public R<Object> getFileInfoByFolderId(
            @RequestParam("folderId") @NotBlank(message = "文件夹id不能为空") String folderId
    ){
        return fileInfoService.getFileInfoByFolderId(folderId);

    }



    /**
     * 获取自己分享的文件
     * @return  R
     */
    @ApiOperation("获取自己分享的文件")
    @PostMapping("/getFileInfoByShare")
    public R<Object> getFileInfoByShare(){
        return fileInfoService.getFileInfoByShare();

    }


    /**
     * 下载文件
     * @return  R
     */
    @ApiOperation("下载文件")
    @PostMapping("/downloadFile")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件id")
    })
    public R<Object> downloadFile(
            @ApiParam(value = "文件id",required = true) @RequestParam("fileId") @NotBlank(message = "文件id不能为空") String fileId
    ){
        return fileInfoService.downloadFile(fileId);
    }

    /**
     * 更改文件名称
     * @return  R
     */
    @ApiOperation("更改文件名称")
    @PostMapping("/updateFileName")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件id"),
            @ApiImplicitParam(paramType="query",name="fileName",dataTypeClass = String.class,required=true,value="文件的新名字")
    })
    public R<Object> updateFileName(
             @RequestParam("fileId") @NotBlank(message = "文件id不能为空") String fileId,
             @RequestParam("fileName") @NotBlank(message = "文件名不能为空") String fileName
    ){
        return fileInfoService.updateFileName(fileId, fileName);
    }


    /**
     * 更改文件备注
     * @return  R
     */
    @ApiOperation("更改文件备注")
    @PostMapping("/updateFileRemark")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件id"),
            @ApiImplicitParam(paramType="query",name="fileRemark",dataTypeClass = String.class,required=true,value="文件的备注")
    })
    public R<Object> updateFileRemark(
            @RequestParam("fileId") @NotBlank(message = "文件id不能为空") String fileId,
            @RequestParam("fileRemark") @NotBlank(message = "文件备注不能为空") String fileRemark
    ){
        return fileInfoService.updateFileRemark(fileId,fileRemark);
    }
}

