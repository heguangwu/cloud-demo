package com.meetyou.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//第三方调用通过Feign实现，和微服务内的调用差异，url中填写第三方url基地址即可
//如下例子是请求 https://jsonplaceholder.typicode.com/posts/{id} 数据
@FeignClient(value = "test-client", url = "https://jsonplaceholder.typicode.com")
public interface ThirdPartFeign {
    @GetMapping("/posts/{id}")
    String getPost(@PathVariable("id") Long id);
}
