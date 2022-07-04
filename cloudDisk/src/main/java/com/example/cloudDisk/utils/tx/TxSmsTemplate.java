package com.example.cloudDisk.utils.tx;

/**
 * @author 成大事
 * @date 2022/3/5 14:30
 */
import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 腾讯云发送短信模板对象,封装了发送短信的api
 */
@Slf4j
public class TxSmsTemplate {

    private TxProperties txProperties;

    public TxSmsTemplate(TxProperties txProperties) {
        this.txProperties = txProperties;
    }

    /**
     * 指定正文模板id发送短信
     * @param number 用户的手机号码
     * @return OK 成功  null 失败
     * @description  多个
     */
    public String sendMesModel(String number,String value) {
        try {
            // 接收生成的验证码，设置5分钟内填写
            String[] params = {value, "2"};

            // 构建短信发送器
            SmsSingleSender ssender = new SmsSingleSender(txProperties.getAppId(), txProperties.getAppKey());

            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam("86", number,
                    txProperties.getTemplateId(), params, txProperties.getSignName(), "", "");

            //OK
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            log.info("短信发送失败,HTTP响应码错误!");
        } catch (JSONException e) {
            // json解析错误
            log.info("短信发送失败,json解析错误!");
        } catch (IOException e) {
            // 网络IO错误
            log.info("短信发送失败,网络IO错误!");
        }
        return null;
    }


    /**
     * 指定正文模板id发送短信
     * @param number 用户的手机号码
     * @return OK 成功  null 失败
     * @description  单个
     */
    public String sendCaptcha(String number,String value) {
        try {
            // 接收生成的验证码，设置5分钟内填写
            String[] params = {value};

            // 构建短信发送器
            SmsSingleSender ssender = new SmsSingleSender(txProperties.getAppId(), txProperties.getAppKey());

            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam("86", number,
                    txProperties.getTemplateId(), params, txProperties.getSignName(), "", "");

            //OK
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            log.info("短信发送失败,HTTP响应码错误!");
        } catch (JSONException e) {
            // json解析错误
            log.info("短信发送失败,json解析错误!");
        } catch (IOException e) {
            // 网络IO错误
            log.info("短信发送失败,网络IO错误!");
        }
        return null;
    }


}
