package cloud.heguangwu.product.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component
public class Producer {
    @Bean
    public Function<String, String> orderProcess(){
        return value -> {
            log.info("发送队列数据：{}", value);
            return "ORDER ===> " +value;
        };
    }
}
