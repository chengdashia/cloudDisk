package com.example.cloudDisk.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@TableName("file_history")
@ApiModel(value = "FileHistory对象", description = "")
public class FileHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableId("history_id")
    private String historyId;

    @ApiModelProperty("文件Id")
    @TableField("file_id")
    private String fileId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("浏览时间")
    @TableField("view_time")
    private Date viewTime;


}
