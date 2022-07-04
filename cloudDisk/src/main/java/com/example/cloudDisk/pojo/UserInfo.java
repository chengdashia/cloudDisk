package com.example.cloudDisk.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 成大事
 * @since 2022-07-01 11:54:54
 */
@Getter
@Setter
@TableName("user_info")
@ApiModel(value = "UserInfo对象", description = "")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableField("user_info_id")
    private String userInfoId;

    @ApiModelProperty("用户唯一标识	")
    @TableId("user_id")
    private String userId;

    @ApiModelProperty("用户名称（默认手机+随机字符串5-7位）")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("登陆凭证")
    @TableField("user_tel")
    private String userTel;

    @ApiModelProperty("用户邮箱")
    @TableField("user_mail")
    private String userMail;

    @ApiModelProperty("登陆密码")
    @TableField("user_pwd")
    private String userPwd;

    @ApiModelProperty("0 未完成；	1 完成")
    @TableField("user_initialize")
    private Integer userInitialize;

    @ApiModelProperty("用户自我描述")
    @TableField("user_introduction")
    private String userIntroduction;

    @ApiModelProperty("用户地址")
    @TableField("user_local")
    private String userLocal;

    @ApiModelProperty("用户头像")
    @TableField("user_avatar")
    private String userAvatar;

    @ApiModelProperty("用户注册时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


}
