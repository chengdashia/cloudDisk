package com.example.cloudDisk.service;

import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.pojo.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
     * 使用邮箱验证码登录
     * @param mailbox  手机号
     * @param mailCode 邮箱验证码
     * @return  R
     */
    R<Object> loginByMail(String mailbox, String mailCode);

    /**
     * 注册 通过邮箱
     * @param mailbox       邮箱
     * @param pwd           密码
     * @param mailCode      验证码
     * @return R
     */
    R<Object> registerByMail(String mailbox, String pwd, String mailCode);


    /**
     * 用户上传头像
     * @param  file 用户头像图片
     * @return  R
     */
    R<Object> updateUserAvatar(MultipartFile file) throws Exception;
}
