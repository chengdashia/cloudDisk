package com.example.cloudDisk.controller;


import com.example.cloudDisk.service.FileLabelService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Api(tags = "文件标签 信息")
@RestController
@RequestMapping("/fileLabel")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileLabelController {

    private final FileLabelService fileLabelService;



}

