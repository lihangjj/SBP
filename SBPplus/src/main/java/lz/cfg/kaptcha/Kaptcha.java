package lz.cfg.kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class Kaptcha {
    @Bean
    public DefaultKaptcha getKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");//是否要边框
        properties.setProperty("kaptcha.border.color", "0,162,233");//边框颜色
        properties.setProperty("kaptcha.textproducer.font.color", "0,162,233");//字体颜色
        properties.setProperty("kaptcha.image.width", "130");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.session.key", "rand");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
