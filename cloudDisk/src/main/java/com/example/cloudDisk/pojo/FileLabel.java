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
@TableName("file_label")
@ApiModel(value = "FileLabel对象", description = "")
public class FileLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableId("file_lable_id")
    private String fileLableId;

    @ApiModelProperty("文件唯一标识")
    @TableField("file_id")
    private String fileId;

    @ApiModelProperty("文件标签Id")
    @TableField("file_label_id")
    private String fileLabelId;


}
