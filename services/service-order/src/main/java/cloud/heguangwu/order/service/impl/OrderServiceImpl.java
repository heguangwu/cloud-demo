package cloud.heguangwu.order.service.impl;

import cloud.heguangwu.order.Order;
import cloud.heguangwu.order.annotation.MyLog;
import cloud.heguangwu.order.feign.ProductFeign;
import cloud.heguangwu.order.service.OrderService;
import cloud.heguangwu.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final ProductFeign productFeign;

    @Autowired
    public OrderServiceImpl(ProductFeign productFeign) {
        this.productFeign = productFeign;
    }

    @MyLog(desc="创建订单", type="INSERT")
    @Override
    public Order createOrder(Long productId, Long userId) {
        //调用Feign完成远程调用
        Product product = productFeign.getProductById(productId);
        BigDecimal total = new BigDecimal(String.valueOf(product.price().multiply(new BigDecimal(product.num()))));
        String username = "user-" + userId;
        return new Order(1L, total, userId, username, List.of(product));
    }
}
