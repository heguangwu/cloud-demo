package cloud.heguangwu.order.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Slf4j
@Component
public class MyConsumer {
    @Bean
    public Consumer<String> orderConsumerReceive() {
        return message ->
            log.info("=====> ORDER =====> Received: {} ", message);
    }
}
