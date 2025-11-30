package cloud.heguangwu.system;

import cloud.heguangwu.system.entity.User;
import cloud.heguangwu.system.enums.GenderEnum;
import cloud.heguangwu.system.enums.StatusEnum;
import cloud.heguangwu.system.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.cloud.nacos.config.import-check.enabled=false")
class MybatisPlusEnumFiledTest {

    @Autowired
    private UserMapper useMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setName("王五");
        user.setAge(33);
        user.setGender(GenderEnum.MALE);
        user.setStatus(StatusEnum.DISABLE);
        assertEquals(1, useMapper.insert(user));
    }

    @Test
    void testUpdateByStatus() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStatus, StatusEnum.DISABLE)
                .eq(User::getName, "王五");
        User user = userMapper.selectOne(wrapper);
        user.setAge(1990);
        // 为什么直接调用 userMapper.update(LambdaUpdateWrapper)不会更新字段
        assertEquals(1, userMapper.updateById(user));
    }

    @Test
    void testUpdateDate() {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getStatus, StatusEnum.DISABLE)
                .eq(User::getName, "王五");
        User user = userMapper.selectOne(wrapper);
        user.setAge(1990);
        // 为什么直接调用 userMapper.update(LambdaUpdateWrapper)不会更新字段
        assertEquals(1, userMapper.updateById(user));
    }
}
