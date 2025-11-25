package com.meetyou.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * feign 请求拦截器，Component 默认全局生效
 * 要局部生效，需要配置 spring.cloud.openfeign.client.config.${SERVER_NAME}.request-interceptors
 */
@Component
@Slf4j
public class TokenRequestInterceptor implements RequestInterceptor {
    /**
     * 请求拦截器操作
     * @param requestTemplate：请求模板，可以增加http head、body等操作
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("-----> token request interceptor <-----");
        requestTemplate.header("X-Token", "order-" + UUID.randomUUID());
    }
}
