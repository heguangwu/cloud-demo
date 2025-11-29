package com.meetyou.order;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Slf4j
@EnableFeignClients  //开启openfeign
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    //接收配置变化通知
    @Bean
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager) {
        return args -> {
            ConfigService configService = nacosConfigManager.getConfigService();
            configService.addListener("service-order.yml",
                    "DEFAULT_GROUP", new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return Executors.newSingleThreadExecutor();
                        }

                        @Override
                        public void receiveConfigInfo(String configInfo) {
                            //configInfo为全量配置数据
                            log.info("------->配置变化: {}",configInfo);
                            log.info("<======DO Something======>");
                        }
                    });
            log.info("===> ApplicationRunner ===>");
        };
    }
}
