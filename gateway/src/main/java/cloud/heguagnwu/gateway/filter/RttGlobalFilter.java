package cloud.heguagnwu.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RttGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //前置业务逻辑
        String url = request.getURI().toString();
        long start = System.currentTimeMillis();
        log.info("======>>>>> url :{}, start time:{}", url, start);

        //后置业务逻辑
        return chain.filter(exchange).doFinally((result) -> {
            long end = System.currentTimeMillis();
            log.info("======>>>>> url :{}, end time:{}", url, end);
            String rtt = (end - start) + "ms";
            response.getHeaders().set("X-RoundTrip-Time", rtt);
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
