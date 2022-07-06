package com.example.cloudDisk.utils.mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 成大事
 * @since 2022/5/28 15:49
 */
@Component
public class MailUtils {
    private static final Logger log = LoggerFactory.getLogger(MailUtils.class);

    /**
     * Spring官方提供的集成邮件服务的实现类，目前是Java后端发送邮件和集成邮件服务的主流工具。
     */
    @Resource
    private JavaMailSender mailSender;


    @Resource
    private TemplateEngine templateEngine;



    /**
     * 从配置文件中注入发件人的姓名
     */
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 发送文本邮件
     * @param to      收件人
     * @param subject 标题
     * @param content 正文
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送html邮件
     */
    public void sendHtmlMail(String to, String subject, String content) {
        try {
            //注意这里使用的是MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            //第二个参数：格式是否为html
            helper.setText(content, true);
            mailSender.send(message);
        }catch (MessagingException e){
            log.error("发送邮件时发生异常！", e);
        }
    }

    /**
     * 发送模板邮件
     * @param to
     * @param subject
     * @param template
     */
    public void sendTemplateMail(String to, String subject, String template){
        try {
            // 获得模板
            Template template1 = freeMarkerConfigurer.getConfiguration().getTemplate(template);
            // 使用Map作为数据模型，定义属性和值
            Map<String,Object> model = new HashMap<>(2);
            //model.put("myname","Ray。");
            //model.put("blog","https://chengdashia.github.io");
            model.put("code",123456);
            // 传入数据模型到模板，替代模板中的占位符，并将模板转化为html字符串
            String templateHtml = FreeMarkerTemplateUtils.processTemplateIntoString(template1,model);
            // 该方法本质上还是发送html邮件，调用之前发送html邮件的方法
            this.sendHtmlMail(to, subject, templateHtml);
        } catch (TemplateException e) {
            log.error("发送邮件时发生异常！", e);
        } catch (IOException e) {
            log.error("发送邮件时发生异常！", e);
        }
    }

    public boolean sendEmailVerificationCode(String verifyCode,String toAddress,boolean isRegister) {
        //调用 VerificationCodeService 生产验证码

        //创建邮件正文
        Context context = new Context();
        context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));

        //将模块引擎内容解析成html字符串
        String emailContent;
        if (isRegister){
            emailContent = templateEngine.process("register", context);
        }else {
            emailContent = templateEngine.process("login", context);
        }
        MimeMessage message=mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(fromEmail);
            helper.setTo(toAddress);
            if (isRegister){
                helper.setSubject("注册验证码");
            }else {
                helper.setSubject("登录验证码");
            }
            helper.setText(emailContent,true);
            mailSender.send(message);
            return true;
        }catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            //要带附件第二个参数设为true
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
        }catch (MessagingException e){
            log.error("发送邮件时发生异常！", e);
        }

    }
}
