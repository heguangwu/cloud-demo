package cloud.heguangwu.system;

import cloud.heguangwu.system.entity.User;
import cloud.heguangwu.system.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.cloud.nacos.config.import-check.enabled=false")
class MybatisPlusFormatTest {
    @Autowired
    UserMapper userMapper;


    @Test
    void insertMap2JsonDate() {
        User user = new User();
        user.setName("JSON测试用户");
        user.setContact(Map.of(
                "tel", "073199999999",
                "phone", "13888888888"
        ));
        assertEquals(1, userMapper.insert(user));
    }

    @Test
    void queryJsonDate() {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getName, "JSON测试用户"));
        assertEquals("JSON测试用户", user.getName());
        Map<String, String> contact = user.getContact();
        assertEquals("13888888888", contact.get("phone"));
        assertEquals("073199999999", contact.get("tel"));
    }

    @Test
    void insertEncryptDate() {
        User user = new User();
        user.setName("test");
        user.setPassword("123456");
        assertEquals(1, userMapper.insert(user));
    }

    @Test
    void updateDecryptDate() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, "加密测试用户");
        User user = userMapper.selectOne(lambdaQueryWrapper);
        assertEquals("123456", user.getPassword());
        user.setPassword("654321");
        userMapper.updateById(user);
    }

    @Test
    void queryDecryptDate() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, "加密测试用户");
        User user = userMapper.selectOne(lambdaQueryWrapper);
        assertEquals("654321", user.getPassword());
    }

    @Test
    void testTenantId() {
        List<User> users = userMapper.selectList(null);
        assertEquals(0, users.size());
    }
}
