package cloud.heguangwu.product;

import java.math.BigDecimal;

public record Product(Long id, BigDecimal price, String name, Integer num) {
}
