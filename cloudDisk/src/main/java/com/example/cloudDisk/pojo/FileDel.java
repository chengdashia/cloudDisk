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
@TableName("file_del")
@ApiModel(value = "FileDel对象", description = "")
public class FileDel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableField("file_del_id")
    private String fileDelId;

    @ApiModelProperty("文件Id")
    @TableId("file_id")
    private String fileId;

    @ApiModelProperty("用户的id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("文件删除时间")
    @TableField("file_del_time")
    private Date fileDelTime;

    @ApiModelProperty("1:未过期；2：过期")
    @TableField("file_del_status")
    private Integer fileDelStatus;


}
