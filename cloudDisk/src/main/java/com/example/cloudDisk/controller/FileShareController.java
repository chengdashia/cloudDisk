package com.example.cloudDisk.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Api(tags = "分享")
@Slf4j
@Validated
@RestController
@RequestMapping("/fileShare")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileShareController {


}

