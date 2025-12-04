package cloud.heguangwu.system.mapper;

import cloud.heguangwu.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

//自带基本的CRUD功能
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE age > #{age}")
    Cursor<User> scan(@Param("age")int age);

    // 自定义方法：根据姓名模糊查询（演示参数传递）
    User selectUserByNameLike(@Param("name") String name);

    // 自定义方法：根据字段是否存在进行查询
    User selectUserByAgeBetween(Integer minAge, Integer maxAge);
}
