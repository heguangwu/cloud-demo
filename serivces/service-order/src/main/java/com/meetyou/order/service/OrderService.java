package com.meetyou.order.service;


import com.meetyou.order.Order;

public interface OrderService {
    Order createOrder(Long productId, Long userId);
}
