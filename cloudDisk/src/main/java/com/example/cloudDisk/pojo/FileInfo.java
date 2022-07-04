package com.example.cloudDisk.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("file_info")
@ApiModel(value = "FileInfo对象", description = "")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableField("file_info_id")
    private String fileInfoId;

    @ApiModelProperty("文件唯一标识")
    @TableId("file_id")
    private String fileId;

    @ApiModelProperty("文件在hdfs中的路劲")
    @TableField("file_path")
    private String filePath;

    @ApiModelProperty("所属文件夹Id")
    @TableField("file_folder_id")
    private String fileFolderId;

    @ApiModelProperty("文件名称")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty("1 word文档类;2 音乐类;3 视频类;4 图片类;5 其他类别")
    @TableField("file_type")
    private Integer fileType;

    @ApiModelProperty("文件上传者Id")
    @TableField("file_upload_id")
    private String fileUploadId;

    @ApiModelProperty("文件上传时间")
    @TableField("file_upload_time")
    private Date fileUploadTime;

    @ApiModelProperty("0 私密;1 公开;2 在售")
    @TableField("file_status")
    private Integer fileStatus;

    @ApiModelProperty("文件备注")
    @TableField("file_others")
    private String fileOthers;

    @ApiModelProperty("只要该文件被访问一次之后，点击量+1")
    @TableField("file_click_nums")
    private String fileClickNums;

    @ApiModelProperty("文件下载次数")
    @TableField("file_download_nums")
    private String fileDownloadNums;

    @ApiModelProperty("文件的封面地址")
    @TableField("file_avatar")
    private String fileAvatar;

    @TableLogic
    @ApiModelProperty("0:删除；1:存在")
    @TableField("file_del")
    private Integer fileDel;


}
