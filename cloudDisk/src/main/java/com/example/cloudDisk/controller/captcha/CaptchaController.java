package com.example.cloudDisk.controller.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import com.example.cloudDisk.common.result.R;
import com.example.cloudDisk.utils.math.MathAlgorithm;
import com.example.cloudDisk.utils.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 成大事
 * @since 2022/5/26 8:32
 */
@Api(tags = "验证码")
@Validated
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CaptchaController {

    private final RedisUtil redisUtil;

    private final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    /**
     * 生成图像验证码
     * @param response response请求对象
     */
    @ApiOperation("生成图像验证码")
    @GetMapping(value = "/generateValidateCode")
    public void generateValidateCode(HttpServletRequest request,HttpServletResponse response){
        try {
            ////LineCaptcha 线段干扰的验证码 定义图形验证码的长和宽
            //LineCaptcha captchaImg = CaptchaUtil.createLineCaptcha(126, 30,4,50);
            //String captcha = captchaImg.getCode();


            ////CircleCaptcha 圆圈干扰验证码 定义图形验证码的长、宽、验证码字符数、干扰元素个数
            //CircleCaptcha captchaImg = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
            //String captcha = captchaImg.getCode();

            ////ShearCaptcha 扭曲干扰验证码  定义图形验证码的长、宽、验证码字符数、干扰线宽度
            //ShearCaptcha captchaImg = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
            //String captcha = captchaImg.getCode();

            //四则运算 图形验证码
            ShearCaptcha captchaImg = CaptchaUtil.createShearCaptcha(200, 45, 4, 4);
            // 自定义验证码内容为四则运算方式
            captchaImg.setGenerator(new MathGenerator());
            // 重新生成code
            captchaImg.createCode();
            //获取四则运算的字符串
            String code = captchaImg.getCode().substring(0, captchaImg.getCode().lastIndexOf("="));
            //字符串的四则运算得出结果
            String captcha = MathAlgorithm.getValue(code);


            //设置response响应
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");

            //将验证码的信息 写入session
            //request.getSession().setAttribute("CAPTCHA_KEY", captcha);

            logger.info("session 信息{}"+request.getSession().getId());

            //将验证码的信息 写入redis
            redisUtil.set(request.getSession().getId(),captcha,60 * 2);

            //输出浏览器
            OutputStream out=response.getOutputStream();
            captchaImg.write(out);
            //关闭
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对验证码进行输入验证
     */
    @ApiOperation("对验证码进行输入验证")
    @RequestMapping(value = "/verify")
    public R verify(
            @NotNull @NotBlank(message = "验证码不能为空") @Size(min = 2,max = 6) String code,
            HttpSession session
    ){
        Object captcha = redisUtil.get(session.getId());
        //Object captcha_key = session.getAttribute("CAPTCHA_KEY");
        //用session保存的验证码和输入的验证码对比
        boolean b = captcha.equals(code);
        try {
            if (b){
                return R.error("验证成功");
            }else {
                return R.error("验证失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error("验证异常");
        }
    }

}
