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
@TableName("file_share")
@ApiModel(value = "FileShare对象", description = "")
public class FileShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableId("file_share_id")
    private String fileShareId;

    @ApiModelProperty("文件唯一标识")
    @TableField("file_id")
    private String fileId;

    @ApiModelProperty("文件分享时间")
    @TableField("file_share_time")
    private Date fileShareTime;


}
