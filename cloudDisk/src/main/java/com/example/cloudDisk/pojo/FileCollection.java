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
 * @since 2022-07-01 11:54:53
 */
@Getter
@Setter
@TableName("file_collection")
@ApiModel(value = "FileCollection对象", description = "")
public class FileCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableField("collection_id")
    private String collectionId;

    @ApiModelProperty("文件id")
    @TableId("file_id")
    private String fileId;

    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("收藏时间")
    @TableField("collection_time")
    private Date collectionTime;


}
