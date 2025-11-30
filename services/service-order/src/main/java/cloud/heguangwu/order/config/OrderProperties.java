package cloud.heguangwu.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
//支持动态刷新
@ConfigurationProperties(prefix = "order")
@Component
public class OrderProperties {
    // 对应order.timeout
    String timeout;
    //对应order.auto-confirm
    Boolean autoConfirm;
}
