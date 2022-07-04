package com.example.cloudDisk.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("root_directory_info")
@ApiModel(value = "RootDirectoryInfo对象", description = "")
public class RootDirectoryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableId("root_directory_id")
    private String rootDirectoryId;

    @ApiModelProperty("文件夹Id")
    @TableField("folder_id")
    private String folderId;

    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;


}
