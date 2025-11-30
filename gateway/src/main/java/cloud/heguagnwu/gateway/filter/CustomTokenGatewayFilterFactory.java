package cloud.heguagnwu.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CustomTokenGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders headers = exchange.getResponse().getHeaders();
            String value = config.getValue();
            if("uuid".equalsIgnoreCase(value)) {
                value = UUID.randomUUID().toString();
            } else if("jwt".equalsIgnoreCase(value)) {
                value = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Ind3dy5iZWpzb24uY29tIiwic3ViIjoiZGVtbyIsImlhdCI6MTc2NDA0MTUzOSwibmJmIjoxNzY0MDQxNTM5LCJleHAiOjE3NjQxMjc5Mzl9.NWFpVwpdGs6BK-Cg20S7LvU3mzN6-OLldbwiAU-rZQk";
            }
            headers.set(config.getName(), value);
        }));
    }
}
