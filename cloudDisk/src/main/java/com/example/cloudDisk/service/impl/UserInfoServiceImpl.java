package com.example.cloudDisk.service.impl;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.common.result.ResultCode;
import com.example.cloudDisk.controller.captcha.MailController;
import com.example.cloudDisk.mapper.*;
import com.example.cloudDisk.pojo.*;
import com.example.cloudDisk.service.UserInfoService;
import com.example.cloudDisk.utils.Constants.Constant;
import com.example.cloudDisk.utils.hdfs.HdfsUtil;
import com.example.cloudDisk.utils.redis.RedisUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserLabelMapper userLabelMapper;

    @Resource
    private RootDirectoryInfoMapper rootDirectoryInfoMapper;

    @Resource
    private FolderInfoMapper folderInfoMapper;

    @Resource
    private FolderFileInfoMapper folderFileInfoMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private HdfsUtil hdfsUtil;


    /**
     * 通过密码登录
     * @param tel        电话号
     * @param pwd        密码
     * @return           R
     */
    @Override
    public R<Object> loginByPwd(String tel, String pwd) {
        try {
            UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                    .select("user_id", "user_pwd", "user_initialize")
                    .eq("user_tel", tel));
            if(userInfo != null){
                if(userInfo.getUserPwd().equals(pwd)){
                    StpUtil.login(userInfo.getUserId(), SaLoginConfig.setExtra("uInit", userInfo.getUserInitialize()));
                    log.info("用户{}登录成功",userInfo.getUserId());
                    return R.ok(StpUtil.getTokenInfo());
                }else {
                    // 密码错误
                    return R.error(ResultCode.PWD_ERROR.getCode(), ResultCode.PWD_ERROR.getMessage());
                }
            }else {
                // 用户不存在 请先注册
                return R.error(ResultCode.NOT_EXIST.getCode(),ResultCode.NOT_EXIST.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 通过验证码登录
     * @param tel        电话号
     * @param code        验证码
     * @return           R
     */
    @Override
    public R<Object> loginByCaptcha(String tel, String code) {
        if (redisUtil.hasKey(tel)) {
            if (redisUtil.get(tel).equals(code)) {
                try {
                    UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                            .select("user_id", "user_initialize")
                            .eq("user_tel", tel));
                    if (userInfo != null){
                        StpUtil.login(userInfo.getUserId(), SaLoginConfig
                                .setExtra("uInit", userInfo.getUserInitialize()));
                        return R.ok(StpUtil.getTokenInfo());
                    }else {
                        // 用户不存在 请先注册
                        return R.error(ResultCode.NOT_EXIST.getCode(), ResultCode.NOT_EXIST.getMessage());
                    }
                } catch (Exception e) {
                    // 服务器错误
                    e.printStackTrace();
                    return R.error();
                }
            }else {
                //验证码错误
                return R.error(ResultCode.CAPTCHA_ERROR.getCode(),ResultCode.CAPTCHA_ERROR.getMessage());
            }
        }else {
            //验证码过期
            return R.error(ResultCode.CAPTCHA_EXPIRE.getCode(),ResultCode.CAPTCHA_EXPIRE.getMessage());
        }
    }

    /**
     * 使用邮箱验证码登录
     * @param mailbox  手机号
     * @param mailCode 邮箱验证码
     * @return  R
     */
    @Override
    public R<Object> loginByMail(String mailbox, String mailCode) {
        if (redisUtil.hasKey(MailController.getLoginKey(mailbox))) {
            if (redisUtil.get(MailController.getLoginKey(mailbox)).equals(mailCode)) {
                try {
                    UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                            .select("user_id", "user_initialize")
                            .eq("user_mail", mailbox));
                    if (userInfo != null){
                        StpUtil.login(userInfo.getUserId(), SaLoginConfig
                                .setExtra("uInit", userInfo.getUserInitialize()));
                        return R.ok(StpUtil.getTokenInfo());
                    }else {
                        // 用户不存在 请先注册
                        return R.error(ResultCode.NOT_EXIST.getCode(), ResultCode.NOT_EXIST.getMessage());
                    }
                } catch (Exception e) {
                    // 服务器错误
                    e.printStackTrace();
                    return R.error();
                }
            }else {
                //验证码错误
                return R.error(ResultCode.CAPTCHA_ERROR.getCode(),ResultCode.CAPTCHA_ERROR.getMessage());
            }
        }else {
            //验证码过期
            return R.error(ResultCode.CAPTCHA_EXPIRE.getCode(),ResultCode.CAPTCHA_EXPIRE.getMessage());
        }
    }

    /**
     * 注册 通过邮箱
     * @param mailbox       邮箱
     * @param pwd           密码
     * @param mailCode      验证码
     * @return R
     */
    @Override
    public R<Object> registerByMail(String mailbox, String pwd, String mailCode) {
        //如果redis有这个验证码
        if(redisUtil.hasKey(MailController.getRegisterKey(mailbox))){
            String redisSmsCode = (String) redisUtil.get(MailController.getRegisterKey(mailbox));
            if(redisSmsCode.equals(mailCode)){
                UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_mail", mailbox));
                if (user == null) {
                    user = new UserInfo();
                    user.setUserInfoId(IdUtil.fastUUID());
                    user.setUserId(IdUtil.simpleUUID());
                    user.setUserMail(mailbox);
                    user.setUserPwd(pwd);
                    user.setUserName(mailbox + RandomUtil.randomString(IdUtil.simpleUUID(), 5));
                    int userInsert = userInfoMapper.insert(user);
                    //如果注册成功，则创建根目录
                    if (userInsert == 1) {
                        //先在文件表中创建
                        FolderInfo folderInfo = new FolderInfo();
                        folderInfo.setFolderInfoId(IdUtil.fastUUID());
                        folderInfo.setFolderId(IdUtil.simpleUUID());
                        folderInfo.setUserId(user.getUserId());
                        folderInfo.setFolderUrl("/"+System.currentTimeMillis() + user.getUserId());
                        folderInfo.setFolderName("我的资源");
                        folderInfo.setFolderCreateTime(new Date());
                        int folderInsert = folderInfoMapper.insert(folderInfo);
                        //创建根目录
                        if (folderInsert == 1) {
                            //初始化根目录
                            RootDirectoryInfo rootDirectoryInfo = new RootDirectoryInfo();
                            rootDirectoryInfo.setRootDirectoryId(IdUtil.fastUUID());
                            rootDirectoryInfo.setUserId(user.getUserId());
                            rootDirectoryInfo.setFolderId(folderInfo.getFolderId());
                            //插入根目录
                            int rootInsert = rootDirectoryInfoMapper.insert(rootDirectoryInfo);
                            if(rootInsert == 1){
                                FolderFileInfo folderFileInfo = new FolderFileInfo();
                                folderFileInfo.setFolderFileInfoId(IdUtil.fastUUID());
                                folderFileInfo.setFolderFileId(folderInfo.getFolderId());
                                folderFileInfo.setFolderFileType(Constant.FOLDER);
                                folderFileInfo.setFolderPd(folderInfo.getFolderId());
                                int insert = folderFileInfoMapper.insert(folderFileInfo);
                                if(insert == 1){
                                    hdfsUtil.createFolder(folderInfo.getFolderUrl());
                                    return R.ok();
                                }else {
                                    return R.error();
                                }
                            }else {
                                return R.error();
                            }
                        } else {
                            // 根目录创建不成功
                            return R.error();
                        }
                    }else {
                        // 注册失败了
                        return R.error();
                    }
                }else {
                    //手机号已经注册
                    return R.error(ResultCode.REGISTERED.getCode(), ResultCode.REGISTERED.getMessage());
                }

            }else {
                //验证码错误
                return R.error(ResultCode.CAPTCHA_ERROR.getCode(), ResultCode.CAPTCHA_ERROR.getMessage());
            }
        }else {
            //如果redis没有这个验证码   过期了。重新发送
            return R.error(ResultCode.CAPTCHA_EXPIRE.getCode(), ResultCode.CAPTCHA_EXPIRE.getMessage());
        }
    }

    /**
     * 获取用户信息
     * @param uId       用户id
     * @param uInit     用户是否初始化
     * @return      R
     */
    @Override
    public R<Object> getUserInfo(String uId, int uInit) {
        Map<String, Object> map = new HashMap<>(2);
        try {

            Map<String, Object> userInfo = userInfoMapper.selectJoinMap(new MPJLambdaWrapper<>()
                    .select(UserInfo.class, i -> !"user_info_id".equals(i.getColumn()))
                    .select(RootDirectoryInfo::getFolderId)
                    .leftJoin(RootDirectoryInfo.class, RootDirectoryInfo::getUserId, UserInfo::getUserId)
                    .eq(UserInfo::getUserId,uId));
            if (userInfo != null) {
                List<Map<String, Object>> infoMaps = userLabelMapper.selectJoinMaps(new MPJLambdaWrapper<>()
                        .select(LabelInfo::getLabelName)
                        .leftJoin(LabelInfo.class, LabelInfo::getInterestLabelId, UserLabel::getInterestLabelId)
                        .eq(UserInfo::getUserId, uId));
                if (uInit == Constant.INIT) {
                    List<String> labelList = new ArrayList<>();
                    for (Map<String, Object> fileLabelMap : infoMaps) {
                        Object labelName = fileLabelMap.get("labelName");
                        labelList.add(String.valueOf(labelName));
                    }
                    map.put("data", userInfo);
                    map.put("label", labelList);
                } else {
                    map.put("data", userInfo);
                }
                return R.ok(map);

            } else {
                return R.error(ResultCode.NOT_EXIST.getCode(), ResultCode.NOT_EXIST.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 注册
     * @param tel        电话号
     * @param pwd        密码
     * @param code       验证码
     * @return             R
     */
    @Override
    public R<Object> register(String tel, String pwd, String code ) {
        //如果redis有这个验证码
        if(redisUtil.hasKey(tel)){
            String redisSmsCode = (String) redisUtil.get(tel);
            if(redisSmsCode.equals(code)){
                UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_tel", tel));
                if (user == null) {
                    user = new UserInfo();
                    user.setUserInfoId(IdUtil.fastUUID());
                    user.setUserId(IdUtil.simpleUUID());
                    user.setUserTel(tel);
                    user.setUserPwd(pwd);
                    user.setUserName(tel + RandomUtil.randomString(IdUtil.simpleUUID(), 5));
                    int userInsert = userInfoMapper.insert(user);
                    //如果注册成功，则创建根目录
                    if (userInsert == 1) {
                        //先在文件表中创建
                        FolderInfo folderInfo = new FolderInfo();
                        folderInfo.setFolderInfoId(IdUtil.fastUUID());
                        folderInfo.setFolderId(IdUtil.simpleUUID());
                        folderInfo.setUserId(user.getUserId());
                        folderInfo.setFolderUrl("/"+System.currentTimeMillis() + user.getUserId());
                        folderInfo.setFolderName("我的资源");
                        folderInfo.setFolderCreateTime(new Date());
                        int folderInsert = folderInfoMapper.insert(folderInfo);
                        //创建根目录
                        if (folderInsert == 1) {
                            //初始化根目录
                            RootDirectoryInfo rootDirectoryInfo = new RootDirectoryInfo();
                            rootDirectoryInfo.setRootDirectoryId(IdUtil.fastUUID());
                            rootDirectoryInfo.setUserId(user.getUserId());
                            rootDirectoryInfo.setFolderId(folderInfo.getFolderId());
                            //插入根目录
                            int rootInsert = rootDirectoryInfoMapper.insert(rootDirectoryInfo);
                            if(rootInsert == 1){
                                FolderFileInfo folderFileInfo = new FolderFileInfo();
                                folderFileInfo.setFolderFileInfoId(IdUtil.fastUUID());
                                folderFileInfo.setFolderFileId(folderInfo.getFolderId());
                                folderFileInfo.setFolderFileType(Constant.FOLDER);
                                folderFileInfo.setFolderPd(folderInfo.getFolderId());
                                int insert = folderFileInfoMapper.insert(folderFileInfo);
                                if(insert == 1){
                                    hdfsUtil.createFolder(folderInfo.getFolderUrl());
                                    return R.ok();
                                }else {
                                    return R.error();
                                }
                            }else {
                                return R.error();
                            }
                        }   else {
                            return R.error();
                        }
                    }else {
                        return R.error();
                    }
                }else {
                    //手机号已经注册
                    return R.error(ResultCode.REGISTERED.getCode(), ResultCode.REGISTERED.getMessage());
                }

            }else {
                //验证码错误
                return R.error(ResultCode.CAPTCHA_ERROR.getCode(), ResultCode.CAPTCHA_ERROR.getMessage());
            }
        }else {
            //如果redis没有这个验证码   过期了。重新发送
            return R.error(ResultCode.CAPTCHA_EXPIRE.getCode(), ResultCode.CAPTCHA_EXPIRE.getMessage());
        }
    }



}
