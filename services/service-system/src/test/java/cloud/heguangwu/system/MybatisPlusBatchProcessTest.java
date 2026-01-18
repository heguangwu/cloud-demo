package cloud.heguangwu.system;

import cloud.heguangwu.system.entity.User;
import cloud.heguangwu.system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.cloud.nacos.config.import-check.enabled=false")
class MybatisPlusBatchProcessTest {
    @Autowired
    UserService userService;

    @Test
    void insertMap2JsonDate() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(201);
        user.setName("用户-201");
        userList.add(user);

        User user2 = new User();
        user2.setId(202);
        user2.setName("用户-202");
        userList.add(user2);

        boolean b = userService.saveBatch(userList);
        System.out.println("=======================1111=========================");
        userList.forEach(System.out::println);
        System.out.println("=======================11111=========================");
        userList.forEach(u -> u.setAge(99));
        userService.updateBatchById(userList);
        System.out.println("=========================222222=======================");
        userList.forEach(System.out::println);
        System.out.println("==========================222222======================");
        assertTrue(b);
    }
}
