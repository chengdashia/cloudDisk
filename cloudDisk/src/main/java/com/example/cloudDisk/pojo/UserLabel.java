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
@TableName("user_label")
@ApiModel(value = "UserLabel对象", description = "")
public class UserLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    @TableField("user_label_id")
    private String userLabelId;

    @ApiModelProperty("用户唯一标识")
    @TableId("user_id")
    private String userId;

    @ApiModelProperty("标签唯一标识")
    @TableField("interest_label_id")
    private String interestLabelId;


}
