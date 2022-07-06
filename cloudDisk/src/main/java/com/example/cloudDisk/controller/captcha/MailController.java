package com.example.cloudDisk.controller.captcha;

import cn.hutool.core.util.RandomUtil;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.utils.mail.MailUtils;
import com.example.cloudDisk.utils.redis.RedisUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author 成大事
 * @since 2022/7/3 21:03
 */
@Api(tags = "邮箱")
@Slf4j
@Validated
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MailController {

    private final MailUtils mailUtils;

    private final RedisUtil redisUtil;

    /**
     * 发送邮箱验证码注册时
     * @param mailbox 用户邮箱
     */
    @ApiOperation("发送邮箱验证码注册时")
    @PostMapping("/sendMailCodeForRegister")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="mailbox",dataTypeClass = String.class,required=true,value="用户邮箱")
    })
    public R<Object> sendMailCodeForRegister(
            @ApiParam(value = "用户邮箱",required = true) @RequestParam("mailbox")  @NotBlank(message = "用户邮箱不能为空") @Email String mailbox
    ){

        String random = RandomUtil.randomNumbers(6);
        mailUtils.sendEmailVerificationCode(random,mailbox,true);
        mailbox = getRegisterKey(mailbox);
        redisUtil.set(mailbox,random,60 * 3);
        return R.ok();
    }


    /**
     * 发送邮箱验证码登录时
     * @param mailbox 用户邮箱
     */
    @ApiOperation("发送邮箱验证码登录时")
    @PostMapping("/sendMailCodeForLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="mailbox",dataTypeClass = String.class,required=true,value="用户邮箱")
    })
    public R<Object> sendMailCodeForLogin(
            @ApiParam(value = "用户邮箱",required = true) @RequestParam("mailbox")  @NotBlank(message = "用户邮箱不能为空") @Email String mailbox
    ){

        String random = RandomUtil.randomNumbers(6);
        mailUtils.sendEmailVerificationCode(random,mailbox,false);
        mailbox = getLoginKey(mailbox);
        redisUtil.set(mailbox,random,60 * 3);
        return R.ok();
    }

    public static String getLoginKey(String mailbox){
        return "login:"+mailbox;
    }

    public static String getRegisterKey(String mailbox){
        return "register:"+mailbox;
    }
}
