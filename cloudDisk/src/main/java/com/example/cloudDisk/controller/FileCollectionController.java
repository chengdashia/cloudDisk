package com.example.cloudDisk.controller;


import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.service.FileCollectionService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
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
 * @since 2022-07-01 11:54:53
 */
@Api(tags = "文件收藏信息")
@RestController
@RequestMapping("/fileCollection")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileCollectionController {

    private final FileCollectionService fileCollectionService;


    /**
     * 文件收藏
     *
     * @param fileId 文件id
     * @return R
     */
    @ApiOperation("文件收藏")
    @PostMapping("/fileCollection")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件的id")
    })
    public R<Object> fileCollection(
            @RequestParam("fileId") @NotBlank(message = "文件的id") @NotNull String fileId
    ) {
        return fileCollectionService.fileCollection(fileId);
    }


    /**
     * 获取自己收藏的文件信息
     *
     * @return R
     */
    @ApiOperation("获取自己收藏的文件信息")
    @PostMapping("/getMyCollection")
    public R<Object> getMyCollection(
    ) {
        // 查询自己收藏的文件
        return fileCollectionService.getMyCollectionFile();
    }

    /**
     * 删除自己收藏的文件信息
     *
     * @param fileId 文件id
     * @return R
     */
    @ApiOperation("删除自己收藏的文件信息")
    @PostMapping("/delMyFileCollection")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="fileId",dataTypeClass = String.class,required=true,value="文件的id")
    })
    public R<Object> delMyFileCollection(
            @ApiParam(value = "文件的id", required = true) @RequestParam("fileId") @NotBlank(message = "文件的id") @NotNull String fileId
    ) {
        //直接删除
        return fileCollectionService.delMyFileCollection(fileId);
    }


}