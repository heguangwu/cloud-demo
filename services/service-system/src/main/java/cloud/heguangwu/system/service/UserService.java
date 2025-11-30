package cloud.heguangwu.system.service;

import cloud.heguangwu.system.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

//比BaseMapper更强大
public interface UserService extends IService<User> {
    //自定义方法
    List<User> selectAgeBetween(Integer minAge, Integer maxAge);

    boolean updateUserNameGeAge(Integer age, String newName);

    User selectWithLambda();

    IPage<User> selectUserPage(int pageNo, int limit);

    List<User> selectInSubQuery(Integer minAge, Integer maxAge);

    void selectWithStream();
    void selectWithStreamCursor();
}
