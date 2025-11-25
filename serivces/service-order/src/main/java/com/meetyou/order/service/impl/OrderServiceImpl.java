package com.meetyou.order.service.impl;

import com.meetyou.order.Order;
import com.meetyou.order.feign.ProductFeign;
import com.meetyou.order.service.OrderService;
import com.meetyou.product.Product;
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

    @Override
    public Order createOrder(Long productId, Long userId) {
        //调用Feign完成远程调用
        Product product = productFeign.getProductById(productId);
        BigDecimal total = new BigDecimal(String.valueOf(product.price().multiply(new BigDecimal(product.num()))));
        String username = "user-" + userId;
        return new Order(1L, total, userId, username, List.of(product));
    }
}
