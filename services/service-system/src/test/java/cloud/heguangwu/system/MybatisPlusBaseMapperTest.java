package cloud.heguangwu.system;

import cloud.heguangwu.system.entity.User;
import cloud.heguangwu.system.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.cloud.nacos.config.import-check.enabled=false")
class MybatisPlusBaseMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert(){
        User user = new User();
        user.setAge(18);
        user.setName("Jon");
        user.setEmail("joh@gmail.com");
        assertEquals(1, userMapper.insert(user));
    }

    @Test
    void testSelectByField(){
        List<User> users = userMapper.selectByMap(Map.of("name", "Jon"));
        assertEquals(1,users.size());
        assertEquals(18,users.get(0).getAge());
        assertEquals("joh@gmail.com",users.get(0).getEmail());
    }

    @Test
    void testUpdate() {
        List<User> users = userMapper.selectByMap(Map.of("name", "Jon"));
        User user = users.get(0);
        user.setAge(19);
        user.setEmail("nobody@gmail.com");
        assertEquals(1, userMapper.updateById(user));
    }

    @Test
    void testDelete() {
        List<User> users = userMapper.selectByMap(Map.of("name", "Jon"));
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals(1, userMapper.deleteById(user.getId()));
    }


}
