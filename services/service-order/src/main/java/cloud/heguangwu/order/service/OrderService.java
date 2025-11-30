package cloud.heguangwu.order.service;


import cloud.heguangwu.order.Order;

public interface OrderService {
    Order createOrder(Long productId, Long userId);
}
