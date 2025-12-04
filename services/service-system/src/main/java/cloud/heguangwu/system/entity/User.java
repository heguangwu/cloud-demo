package cloud.heguangwu.system.entity;

import cloud.heguangwu.system.config.EncryptTypeHandler;
import cloud.heguangwu.system.enums.GenderEnum;
import cloud.heguangwu.system.enums.StatusEnum;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@TableName(value = "user", autoResultMap = true)
public class User {
    //自增主键，AUTO 是数据库自增
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private GenderEnum gender;
    private Integer age;
    private String email;
    private StatusEnum status;
    //插入操作时自动填充，需要实现MetaObjectHandler
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //插入和更新操作时自动填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //更新操作时自动填充
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime opTime;
    //内置的JSON序列化转换操作
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String,String> contact;
    private String userExt;
    //自定义类型处理
    @TableField(typeHandler = EncryptTypeHandler.class)
    private String password;
    //乐观锁使用
    @Version
    private int version;

    private Long tenantId; //不能用基本类型long
    /* 逻辑删除，也可以在配置文件中进行配置 logic-delete-field，
     * 该标志实际就是将删除操作自动转换为该列的设值
     * 默认1为删除，0是未删除
     */
    @TableLogic
    private Integer deleted;
}
