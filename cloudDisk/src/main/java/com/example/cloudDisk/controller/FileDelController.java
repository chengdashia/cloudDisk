package com.example.cloudDisk.controller;


import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.service.FileDelService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Api(tags = "回收站接口")
@Slf4j
@Validated
@RestController
@RequestMapping("/fileDel")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileDelController {


    private final FileDelService fileDelService;

    /**
     * 获取回收站  自己删除的文件信息
     * @return  R
     */
    @ApiOperation("获取回收站  自己删除的文件信息")
    @PostMapping("/getRecycleMyFileList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header",name="token",dataTypeClass = String.class,required=true,value="校验")
    })
    public R<Object> getRecycleMyFileList(){
        // 文件名称   文件备注  删除时间 文件id
        return fileDelService.getRecycleMyFileList();

    }

    /**
     * 彻底删除回收站  自己删除的文件信息
     * @return  R
     */
    @ApiOperation("彻底删除回收站  自己删除的文件信息")
    @PostMapping("/delRecycleMyFile")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件的id")
    })
    public R<Object> delRecycleMyFile(
            @ApiParam(value = "文件的id",required = true) @RequestParam("fileId") @NotBlank(message = "文件的id") @NotNull String fileId
    ){
        return fileDelService.delRecycleMyFile(fileId);
    }

    /**
     * 从回收站回复自己删除的文件信息
     * @return  R
     */
    @ApiOperation("从回收站回复自己删除的文件信息")
    @PostMapping("/recoveryMyFile")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件的id")
    })
    public R<Object> recoveryMyFile(
            @ApiParam(value = "文件的id",required = true) @RequestParam("fileId") @NotBlank(message = "文件的id") @NotNull String fileId
    ){
        return fileDelService.recoveryMyFile(fileId);

    }
}
