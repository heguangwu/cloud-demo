package cloud.heguangwu.order;

import java.math.BigDecimal;
import java.util.List;

public record Order(Long id, BigDecimal total, Long userId, String customName, List<Object> products) {
}
