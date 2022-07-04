package com.example.cloudDisk.utils.tx;

/**
 * @author 成大事
 * @date 2022/3/5 14:30
 */
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 腾讯云发送短信
 * 参数配置类
 */
@Data
// 读取application.yml中的项目名称txsms的属性
@ConfigurationProperties(prefix = "txsms")
public class TxProperties {
    // AppId  1400开头的
    private int AppId;

    // 短信应用SDK AppKey
    private String AppKey;

    // 短信模板ID，需要在短信应用中申请
    // NOTE:真实的模板ID需要在短信控制台中申请
    private int TemplateId;

    // 签名
    // NOTE:真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
    private String signName;

}

