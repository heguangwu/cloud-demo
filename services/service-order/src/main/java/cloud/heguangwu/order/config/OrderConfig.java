package cloud.heguangwu.order.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    // feign 日志配置
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    // 失败重试策略
    @Bean
    Retryer retryer() {
        return new Retryer.Default(100, 1000, 2);
    }
}
