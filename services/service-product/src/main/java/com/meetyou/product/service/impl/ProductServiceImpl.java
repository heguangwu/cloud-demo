package com.meetyou.product.service.impl;

import com.meetyou.product.Product;
import com.meetyou.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private Long sleepSeconds = 1L;
    private int tick = 1;
    private static final Map<Long, Product> productList = Map.of(
            1L, new Product(1L, new BigDecimal("1.99"), "剪刀", 100),
            2L, new Product(2L, new BigDecimal("2.99"), "apple", 120),
            3L, new Product(3L, new BigDecimal("1999"), "TV", 10)
    );
    @Override
    public Product getProductById(Long productId) {
        try {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            if(tick % 3 == 0) {
                if (sleepSeconds < 10) {
                    sleepSeconds = 10L;
                } else {
                    sleepSeconds = 1L;
                }
            }
            tick++;
        }  catch (InterruptedException e) {
            log.info("sleepSeconds exception: {}", e.toString());
        }
        return productList.get(productId);
    }
}
