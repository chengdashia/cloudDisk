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
@TableName("folder_file_info")
@ApiModel(value = "FolderFileInfo对象", description = "")
public class FolderFileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableId("folder_file_info_id")
    private String folderFileInfoId;

    @ApiModelProperty("文件夹唯一标识")
    @TableField("folder_file_id")
    private String folderFileId;

    @ApiModelProperty("1,文件夹;2文件")
    @TableField("folder_file_type")
    private Integer folderFileType;

    @ApiModelProperty("父文件夹Id")
    @TableField("folder_pd")
    private String folderPd;


}
