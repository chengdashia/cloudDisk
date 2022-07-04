package com.example.cloudDisk.controller;


import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.service.LabelInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api(tags = "标签信息")
@RestController
@RequestMapping("/labelInfo")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LabelInfoController {

    private final LabelInfoService labelInfoService;

    /**
     * 随机获取二十条标签信息
     * @return  R
     */
    @ApiOperation("随机获取二十条标签信息")
    @PostMapping("/getLabelInfoRandom20")
    public R<Object> getLabelInfoRandom20(){
        return labelInfoService.getLabelInfoRandom20();
    }



}

