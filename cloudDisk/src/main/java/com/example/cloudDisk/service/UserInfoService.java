package com.example.cloudDisk.service;

import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.pojo.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Transactional
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 获取用户信息
     * @param uId       用户id
     * @param uInit     用户是否初始化
     * @return      R
     */
    R<Object> getUserInfo(String uId, int uInit);

    /**
     * 注册
     * @param tel        电话号
     * @param pwd        密码
     * @param code       验证码
     * @return             R
     */
    R<Object> register(String tel, String pwd,String code);

    /**
     * 通过密码登录
     * @param tel        电话号
     * @param pwd        密码
     * @return           R
     */
    R<Object> loginByPwd(String tel, String pwd);

    /**
     * 通过验证码登录
     * @param tel        电话号
     * @param code        验证码
     * @return           R
     */
    R<Object> loginByCaptcha(String tel, String code);


    /**
     * 手机号登录,使用手机号+邮箱验证码
     * @param mailbox  手机号
     * @param mailCode 邮箱验证码
     * @return  R
     */
    R<Object> loginByMail(String mailbox, String mailCode);
}
