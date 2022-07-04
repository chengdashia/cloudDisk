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
@TableName("folder_info")
@ApiModel(value = "FolderInfo对象", description = "")
public class FolderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableField("folder_info_id")
    private String folderInfoId;

    @ApiModelProperty("文件夹唯一标识")
    @TableId("folder_id")
    private String folderId;

    @ApiModelProperty("该文件夹属于哪一个用户")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("该文件夹在Hdfs中的路劲")
    @TableField("folder_url")
    private String folderUrl;

    @ApiModelProperty("文件夹名称")
    @TableField("folder_name")
    private String folderName;

    @ApiModelProperty("文件夹描述")
    @TableField("folder_tips")
    private String folderTips;

    @ApiModelProperty("文件夹创建时间")
    @TableField("folder_create_time")
    private Date folderCreateTime;


}
