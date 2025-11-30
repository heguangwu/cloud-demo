package cloud.heguangwu.order.controller;

import com.alibaba.cloud.nacos.annotation.NacosConfig;
import cloud.heguangwu.order.Order;
import cloud.heguangwu.order.config.OrderProperties;
import cloud.heguangwu.order.feign.ThirdPartFeign;
import cloud.heguangwu.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/order")
//@RefreshScope
@Slf4j
@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderProperties orderProperties;
    private final ThirdPartFeign thirdPartFeign;

    //Value 默认不支持运行期动态更新，需要结合@RefreshScope注解实现动态刷新
    @Value("${order.my-name}")
    private String myName;
    //NacosConfig 默认支持运行期动态更新
    @NacosConfig(dataId = "service-order.yml", group = "order", key = "order.total")
    private Double total;

    @Autowired
    public OrderController(OrderService orderService, OrderProperties orderProperties, ThirdPartFeign thirdPartFeige) {
        this.orderService = orderService;
        this.orderProperties = orderProperties;
        this.thirdPartFeign = thirdPartFeige;
    }

    @GetMapping("/create")
    public Order createOrder(@RequestParam("pid") Long productId, @RequestParam("uid") Long userId) {
        return orderService.createOrder(productId, userId);
    }

    @GetMapping("/config")
    public String config() {
        return "order.timeout:" + orderProperties.getTimeout()
                + ", auto-confirm:" + orderProperties.getAutoConfirm()
                + ", total:" + total
                + ", my-name:" + myName;
    }

    @GetMapping("/post/{id}")
    public String weather(@PathVariable("id") Long id) {
        String str = thirdPartFeign.getPost(id);
        log.info("POST ======> {}", str);
        return str;
    }
}
