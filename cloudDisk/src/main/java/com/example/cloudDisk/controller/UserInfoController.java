package com.example.cloudDisk.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.service.UserInfoService;
import com.example.cloudDisk.utils.validate.phone.Phone;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Slf4j
@Api(tags = "用户信息")
@RestController
@RequestMapping("/userInfo")
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserInfoController {

    private final UserInfoService userInfoService;

    /**
     * 普通登录,使用手机号+密码  或者 邮箱+密码
     * @param userAccount  手机号 或 邮箱
     * @param userPwd    密码
     * @return  R
     */
    @ApiOperation("使用手机号+密码 或者 邮箱+密码 进行登录")
    @PostMapping("/loginByPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userAccount",dataTypeClass = String.class,required=true,value="用户的手机号 或者 邮箱"),
            @ApiImplicitParam(paramType="query",name="userPwd",dataTypeClass = String.class,required=true,value="用户的密码")
    })
    public R<Object> loginByPassword(
            @RequestParam("userAccount") @NotBlank(message = "手机号不能为空") String userAccount,
            @RequestParam("userPwd") @NotBlank(message = "密码不能为空") String userPwd
    ){
       return userInfoService.loginByPwd(userAccount,userPwd);
    }


    /**
     * 手机号登录,使用手机号+验证码
     * @param tel  手机号
     * @param code    手机验证码
     * @return  R
     */
    @ApiOperation("使用手机号+验证码登录")
    @PostMapping("/loginByVerificationCode")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="user_tel",dataTypeClass = String.class,required=true,value="用户的手机号"),
            @ApiImplicitParam(paramType="query",name="verification_code",dataTypeClass = String.class,required=true,value="手机验证码")
    })
    public R<Object> loginByVerificationCode(
            @RequestParam("user_tel") @NotBlank(message = "手机号不能为空") @Phone String tel,
            @RequestParam("verification_code") @NotBlank(message = "验证码不能为空") @Size(max = 6,min = 6) String code
    ){
        return userInfoService.loginByCaptcha(tel,code);
    }


    /**
     * 使用邮箱验证码登录
     * @param mailbox  手机号
     * @param mailCode 邮箱验证码
     * @return  R
     */
    @ApiOperation("使用邮箱验证码登录")
    @PostMapping("/loginByMail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="mailbox",dataTypeClass = String.class,required=true,value="用户的邮箱"),
            @ApiImplicitParam(paramType="query",name="mailCode",dataTypeClass = String.class,required=true,value="邮箱验证码")
    })
    public R<Object> loginByMail(
            @RequestParam("mailbox") @NotBlank(message = "用户的邮箱不能为空") String mailbox,
            @RequestParam("mailCode") @NotBlank(message = "邮箱验证码不能为空") @Size(max = 6,min = 6) String mailCode
    ){
        return userInfoService.loginByMail(mailbox,mailCode);
    }

    /**
     * 注册 通过邮箱
     * @param mailbox       邮箱
     * @param pwd           密码
     * @param mailCode      验证码
     * @return R
     */
    @ApiOperation("注册 通过邮箱")
    @PostMapping("/registerByMail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="mailbox",dataTypeClass = String.class,required=true,value="用户的邮箱"),
            @ApiImplicitParam(paramType="query",name="pwd",dataTypeClass = String.class,required=true,value="用户的密码"),
            @ApiImplicitParam(paramType="query",name="mailCode",dataTypeClass = String.class,required=true,value="验证码")
    })
    public R<Object> registerByMail(
            @RequestParam("mailbox") @NotBlank(message = "邮箱不能为空") @Email String mailbox,
            @RequestParam("pwd") @NotBlank(message = "密码不能为空") String pwd,
            @RequestParam("mailCode") @NotBlank(message = "短信验证码不能为空") String mailCode
    ){
        return userInfoService.registerByMail(mailbox, pwd, mailCode);
    }

    /**
     * 注册
     * @param tel       手机号
     * @param pwd         密码
     * @param code     验证码
     * @return R
     */
    @ApiOperation("注册 通过手机号")
    @PostMapping("/registerByTel")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="phone",dataTypeClass = String.class,required=true,value="用户的手机号"),
            @ApiImplicitParam(paramType="query",name="pwd",dataTypeClass = String.class,required=true,value="用户的密码"),
            @ApiImplicitParam(paramType="query",name="smsCode",dataTypeClass = String.class,required=true,value="手机验证码")
    })
    public R<Object> registered(
            @RequestParam("phone") @NotBlank(message = "手机号不能为空") @Phone @Size(max = 11,min = 11) String tel,
            @RequestParam("pwd") @NotBlank(message = "密码不能为空") String pwd,
            @RequestParam("smsCode") @NotBlank(message = "短信验证码不能为空") String code
    ){
        return userInfoService.register(tel, pwd, code);
    }


    /**
     * 用户获取个人信息
     * @return  R
     */
    @ApiOperation(value = "用户获取个人信息",notes = "获取用户的详细信息")
    @PostMapping("/getUserInfo")
    public R<Object> getUserInfoNew(){
        int uInit = (int) StpUtil.getExtra("uInit");
        //1.通过token获取个人信息
        //先查user_label 查insterest_lable_id 再去label info里面查label_name
        String uId = (String) StpUtil.getLoginId();
        return userInfoService.getUserInfo(uId,uInit);
    }


    /**
     * 用户上传头像
     * @param  file 用户头像图片
     * @return  R
     */
    @PostMapping("/updateUserAvatar")
    @ApiOperation(value = "用户上传头像",notes = "更换头像")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form",name="file",dataTypeClass = MultipartFile.class,required=true,value="用户头像图片")
    })
    public R<Object> updateUserAvatar(
            @RequestPart("file")MultipartFile file
    ) throws Exception {
        return userInfoService.updateUserAvatar(file);
    }

    /**
     * 用户注销登录
     */
    @ApiOperation(value = "用户注销登录",notes = "根据id让用户退出登录状态")
    @PostMapping("/loginOut")
    public void loginOut(
    ){
        Object id = StpUtil.getLoginId();
        StpUtil.logout(id);
    }

}
