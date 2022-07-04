package com.example.cloudDisk.controller.captcha;

import cn.hutool.core.util.RandomUtil;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.utils.redis.RedisUtil;
import com.example.cloudDisk.utils.tx.TxSmsTemplate;
import com.example.cloudDisk.utils.validate.phone.Phone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 成大事
 * @date 2022/3/5 10:41
 */
@Api(tags = "手机验证码")
@RequestMapping("/captcha")
@RestController
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SMSController {

    private final TxSmsTemplate txSmsTemplate;

    private final RedisUtil redisUtil;

    /**
     * 腾讯云发送短信测试
     * @param phone 手机号
     */
    @ApiOperation("发送验证码")
    @PostMapping("/sendSmsCaptcha")
    public R sendSmsCaptcha(
            @ApiParam(value = "手机号",required = true) @RequestParam("tel")  @NotBlank(message = "手机号不能为空") @Phone @Size(max = 11,min = 11)String phone){
//        if(redisUtil.hasKey(phone)){
//            return new R(CodeType.PHONE_EXISTS, MsgType.PHONE_EXISTS,null);
//        }
        // 参数1: 手机号(正文模板中的参数{1})
        // 参数2: 验证码(正文模板中的参数{2})
        String random = RandomUtil.randomNumbers(6);

//        String Msg = txSmsTemplate.sendMesModel(phone, random);
        String Msg = txSmsTemplate.sendCaptcha(phone, random);
//        JSONArray objects = JSONUtil.parseArray(Msg);
        System.out.println(Msg);


        // Msg不为null 发送成功
        // Msg为null  发送失败
        redisUtil.set(phone,random,60 * 3);
//        return new R(CodeType.SUCCESS, MsgType.SUCCESS,random);
        return R.ok();
    }

}
