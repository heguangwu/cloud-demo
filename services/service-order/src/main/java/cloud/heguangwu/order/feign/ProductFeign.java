package cloud.heguangwu.order.feign;

import cloud.heguangwu.order.feign.fallback.ProductFeignFallback;
import cloud.heguangwu.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// feign调用前在application @EnableFeignClients
// value为远程应用名称，openfeign 自动实现了负载均衡
// fallback为错误处理
@FeignClient(value = "service-product", path = "/product", fallback = ProductFeignFallback.class)
public interface ProductFeign {

    // GetMapping后面的参数是发送对应的请求路径，和Controller相反（接收请求路径）
    @GetMapping("/{id}")
    Product getProductById(@PathVariable("id") Long id);
}
