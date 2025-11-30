package cloud.heguangwu.system;

import cloud.heguangwu.system.entity.User;
import cloud.heguangwu.system.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest(properties = "spring.cloud.nacos.config.import-check.enabled=false")
class MybatisPlusBaseServiceTest {
    @Autowired
    UserService userService;

    @Test
    void testAgeBetween() {
        int minAge = 5;
        int maxAge = 15;
        User user = new User();
        user.setAge(minAge-1);
        user.setName("min_user_sub_1");
        User user2 = new User();
        user2.setAge(minAge);
        user2.setName("min_user");
        User user3 = new User();
        user3.setAge((maxAge+minAge) / 2);
        user3.setName("mid_user");
        User user4 = new User();
        user4.setAge(maxAge);
        user4.setName("max_user");
        User user5 = new User();
        user5.setAge(maxAge + 1);
        user5.setName("max_user_plus_1");
        List<User> users = List.of(user, user2, user3, user4, user5);
        assertTrue(userService.saveBatch(users));

        List<User> query = userService.selectAgeBetween(minAge, maxAge);
        assertEquals(3, query.size());

        assertTrue(userService.removeBatchByIds(users.stream().map(User::getId).toList()));
    }

    @Test
    void testUpdateNameGeAge() {
        User user = new User();
        user.setAge(20);
        user.setName("user1");
        User user2 = new User();
        user2.setAge(25);
        user2.setName("user2");
        User user3 = new User();
        user3.setAge(40);
        user3.setName("user3");
        assertTrue(userService.saveBatch(List.of(user, user2, user3)));

        assertTrue(userService.updateUserNameGeAge(25, "更改后的新名字"));
    }

    @Test
    void testPage() {
        IPage<User> userIPage = userService.selectUserPage(2, 2);
        assertEquals(2, userIPage.getCurrent());
        assertEquals(2, userIPage.getSize());
        userIPage.getRecords().forEach(System.out::println);
    }

    @Test
    void testInSubQuery() {
        List<User> users = userService.selectInSubQuery(25, 40);
        users.forEach(System.out::println);
        assertEquals(2, users.size());
    }

    @Test
    void testSelectWithStream() {
        userService.selectWithStream();
        System.out.println("------------------------------------------");
        userService.selectWithStreamCursor();
        assertTrue(true);
    }
}
