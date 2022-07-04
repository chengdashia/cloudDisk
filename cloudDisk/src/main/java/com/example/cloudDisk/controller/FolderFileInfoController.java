package com.example.cloudDisk.controller;


import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.service.FolderFileInfoService;
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
 * @since 2022-07-01 11:54:54
 */
@Api(tags = "文件 文件夹信息")
@RestController
@RequestMapping("/folderFileInfo")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FolderFileInfoController {

    private final FolderFileInfoService folderFileInfoService;


    /**
     * 根据文件夹id查询文件夹下的文件
     * @param folderId 文件夹id
     * @return               R
     */
    @ApiOperation("根据文件夹id查询文件夹下的文件")
    @PostMapping("/getFolderFileByFolderId")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="folderId",dataTypeClass = String.class,required=true,value="文件夹id")
    })
    public R<Object> getFolderFileByFolderId(
            @RequestParam("folderId") @NotBlank(message = "文件夹id不能为空") @NotNull String folderId
    ){
        return folderFileInfoService.getFolderFileByFolderId(folderId);
    }
}

