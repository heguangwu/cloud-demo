package cloud.heguangwu.system.service.impl;

import cloud.heguangwu.system.entity.User;
import cloud.heguangwu.system.mapper.UserMapper;
import cloud.heguangwu.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> selectAgeBetween(Integer minAge, Integer maxAge) {
        //两种方式，传统方式 QueryWrapper 现代方式 LambdaQueryWrapper
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(User::getAge, minAge, maxAge);
        return this.list(queryWrapper);
    }

    @Override
    public boolean updateUserNameGeAge(Integer age, String newName) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        // 如果age不为空，则会判断 age是否大于指定age，否则该条件不会带入
        updateWrapper.set(User::getName, newName)
                .ge(age != null, User::getAge, age);
        return this.update(updateWrapper);
    }

    @Override
    public User selectWithLambda() {
        return this.lambdaQuery()
                .ge(User::getAge, 5)
                .like(User::getName, "张")
                .orderByAsc(User::getAge)
                .last("limit 1")
                .one();
    }

    @Override
    public IPage<User> selectUserPage(int pageNo, int limit) {
        Page<User> page = new Page<>(pageNo, limit);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(User::getAge, 5);
        return this.page(page, queryWrapper);
    }

    //In子查询
    @Override
    public List<User> selectInSubQuery(Integer minAge, Integer maxAge) {
        LambdaQueryWrapper<User> subQuery = new LambdaQueryWrapper<>();
        subQuery.select(User::getId)
                .ge(minAge != null, User::getAge, minAge)
                .le(maxAge != null, User::getAge, maxAge);

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        return this.list(wrapper.in(User::getId, userMapper.selectObjs(subQuery)));
    }

    @Override
    public void selectWithStream() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(User::getAge, 5);
        // selectList 第一个参数可以设置为分页
        baseMapper.selectList(queryWrapper, resultContext -> {
            int resultCount = resultContext.getResultCount();
            User resultObject = resultContext.getResultObject();
            log.info("resultCount = {}, resultObject ======> {}", resultCount, resultObject);
        });
    }

    @Override
    @Transactional // 注意，这里必须要加上事务
    public void selectWithStreamCursor() {
        try(Cursor<User> scan = baseMapper.scan(5)) {
            scan.forEach(user -> log.info("user ==> {}", user));
        } catch (Exception e) {
            log.error("stream cursor 执行异常：{}", e.getMessage());
        }
    }
}
