package cloud.heguangwu.system.service;

import cloud.heguangwu.system.entity.User;
import cloud.heguangwu.system.enums.GenderEnum;
import cloud.heguangwu.system.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(19);
        user.setId(1001);
        user.setName("admin");
        user.setPassword("Admin123");
        user.setGender(GenderEnum.MALE);
        user.setEmail("admin@heguangwu.cloud");
        user.setTenantId(1L);
        user.setContact(Map.of("key001", "value-001"));
    }

    @Test
    void save() {
        when(userMapper.insert(any(User.class))).thenReturn(1);
        boolean v = userService.save(user);
        assertTrue(v);
    }

    @Test
    void selectList() {
        when(userMapper.selectList(any())).thenReturn(List.of(user));
        List<User> users = userService.selectAgeBetween(1, 100);
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }
}
