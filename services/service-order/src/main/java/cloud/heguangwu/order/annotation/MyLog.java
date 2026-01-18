package cloud.heguangwu.order.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    //操作描述
    String desc() default "";
    //操作类型
    String type() default "default";
    //时间字段名
    String timeField() default "createTime";
}
