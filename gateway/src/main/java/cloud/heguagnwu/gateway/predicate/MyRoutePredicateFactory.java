package cloud.heguagnwu.gateway.predicate;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


// 自定义断言，和其它断言一样，配置时只需要去掉后面的RoutePredicateFactory剩下部分即可
// 本类的配置name为 My，配置args为：param, name
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    public MyRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("param", "value");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (GatewayPredicate) serverWebExchange -> {
            ServerHttpRequest request = serverWebExchange.getRequest();
            String first = request.getQueryParams().getFirst(config.getParam());
            return StringUtils.hasText(first) && first.equals(config.getValue());
        };
    }

    @Validated
    public static class Config {
        @NotEmpty
        private String param;

        @NotEmpty
        private String value;

        public @NotEmpty String getParam() {
            return param;
        }
        public void setParam(@NotEmpty String param) {
            this.param = param;
        }
        public @NotEmpty String getValue() {
            return value;
        }
        public void setValue(@NotEmpty String value) {
            this.value = value;
        }
    }
}
