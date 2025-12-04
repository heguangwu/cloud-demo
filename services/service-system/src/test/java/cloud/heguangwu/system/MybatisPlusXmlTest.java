package cloud.heguangwu.system;

import cloud.heguangwu.system.entity.User;
import cloud.heguangwu.system.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.cloud.nacos.config.import-check.enabled=false")
public class MybatisPlusXmlTest {
    @Resource
    UserMapper userMapper;

    @Test
    void selectUserByNameLike() {
        User user = userMapper.selectUserByNameLike("测试");
        System.out.println(user);
        assertEquals("加密测试用户",user.getName());
    }

    @Test
    void selectUserByAgeBetween() {
        User user = userMapper.selectUserByAgeBetween(null, null);
        System.out.println(user);
        assertEquals("加密测试用户",user.getName());
    }
}
