package cloud.heguangwu.order.feign.fallback;

import cloud.heguangwu.order.feign.ProductFeign;
import cloud.heguangwu.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// feign 错误回调处理，需要配置 feign.sentinel.enabled: true
@Slf4j
@Component
public class ProductFeignFallback implements ProductFeign {
    @Override
    public Product getProductById(Long id) {
        log.info("!!!!!!! 错误回调: {}", id);
        return new Product(999999999L, new BigDecimal(999999999), "UNKNOW", 999999999);
    }
}
