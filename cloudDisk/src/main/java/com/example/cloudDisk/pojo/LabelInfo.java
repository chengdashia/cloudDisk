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
@TableName("label_info")
@ApiModel(value = "LabelInfo对象", description = "")
public class LabelInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableField("label_info_id")
    private String labelInfoId;

    @ApiModelProperty("标签唯一标识")
    @TableId("interest_label_id")
    private String interestLabelId;

    @ApiModelProperty("标签名称")
    @TableField("label_name")
    private String labelName;


}
