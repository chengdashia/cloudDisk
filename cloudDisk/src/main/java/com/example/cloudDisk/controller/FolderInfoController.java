package com.example.cloudDisk.controller;


import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.service.FolderInfoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Api(tags = "文件夹信息")
@RestController
@RequestMapping("/folderInfo")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FolderInfoController {

    private final FolderInfoService folderInfoService;

    /**
     * 创建文件夹
     *
     * @param folderName     文件夹名称
     * @param folderDesc     文件夹描述
     * @param parentFolderId 父文件夹id
     * @return R
     */
    @ApiOperation("创建文件夹")
    @PostMapping("/createFolder")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="folderName",dataTypeClass = String.class,required=true,value="文件夹名称"),
            @ApiImplicitParam(paramType="query",name="folderDesc",dataTypeClass = String.class,required=true,value="文件夹描述"),
            @ApiImplicitParam(paramType="query",name="parentFolderId",dataTypeClass = String.class,value="父文件夹id")
    })
    public R<Object> createFolder(
            @RequestParam("folderName") @NotBlank(message = "文件夹名称不能为空") String folderName,
            @RequestParam(value = "folderDesc", required = false, defaultValue = "") String folderDesc,
            @RequestParam("parentFolderId") @NotBlank(message = "父文件夹id不能为空") String parentFolderId
    ) {
        return folderInfoService.createFolder(folderName,parentFolderId,folderDesc);
    }


    /**
     * 删除文件夹
     * @param folderId 文件id
     * @return R
     */
    @ApiOperation("删除文件夹")
    @PostMapping("/delFolder")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="folderId",dataTypeClass = String.class,required=true,value="文件夹id")
    })
    public R<Object> delFolder(
            @RequestParam("folderId") @NotBlank(message = "文件夹id不能为空") String folderId
    ) {
        return folderInfoService.deleteFolder(folderId);

    }


    /**
     * 更改文件夹名称
     * @return  R
     */
    @ApiOperation("更改文件夹名称")
    @PostMapping("/updateFolderName")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="folderId",dataTypeClass = String.class,required=true,value="文件夹id"),
            @ApiImplicitParam(paramType="query",name="folderName",dataTypeClass = String.class,required=true,value="文件夹的名称")
    })
    public R<Object> updateFolderName(
            @RequestParam("folderId") @NotBlank(message = "文件id不能为空") String folderId,
            @RequestParam("folderName") @NotBlank(message = "文件id不能为空") String folderName
    ){
        return folderInfoService.updateFolderName(folderId, folderName);
    }


    /**
     * 更改文件夹备注
     * @param folderId 文件夹id
     * @param folderRemark 文件夹的备注
     * @return  R
     */
    @ApiOperation("更改文件夹备注")
    @PostMapping("/updateFolderRemark")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="folderId",dataTypeClass = String.class,required=true,value="文件夹id"),
            @ApiImplicitParam(paramType="query",name="folderRemark",dataTypeClass = String.class,required=true,value="文件夹的备注")
    })
    public R<Object> updateFolderRemark(
            @RequestParam("folderId") @NotBlank(message = "文件id不能为空") String folderId,
            @RequestParam("folderRemark") @NotBlank(message = "文件id不能为空") String folderRemark
    ){

        return folderInfoService.updateFolderRemark(folderId,folderRemark);
    }

}

