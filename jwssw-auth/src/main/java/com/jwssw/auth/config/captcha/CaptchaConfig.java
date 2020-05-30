package com.jwssw.auth.config.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码的配置类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 9:53
 * @since JDK 11
 */
@Configuration
public class CaptchaConfig {

    /**
     * 初始化绘制验证码图像
     * <pre>
     *     默认的Producer实现，它使用WordRenderer、GimpyEngine和BackgroundProducer来绘制验证码图像。
     *     文本创建使用TextProducer
     * </pre>
     *
     * @return 绘制验证码图像类
     */
    @Bean
    public DefaultKaptcha producer() {
        // 设置属性值
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "5");

        // 将属性值设置到验证码图片中
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(new Config(properties));

        return defaultKaptcha;
    }
}
