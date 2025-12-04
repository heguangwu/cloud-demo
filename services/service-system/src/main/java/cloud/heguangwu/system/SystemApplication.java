package cloud.heguangwu.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 排除 MyBatis-Plus 及数据源相关的自动配置，避免其初始化逻辑执行
//@SpringBootApplication(exclude = {MybatisPlusAutoConfiguration.class,DataSourceAutoConfiguration.class })

@SpringBootApplication
@MapperScan("cloud.heguangwu.system.mapper")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
