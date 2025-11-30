package cloud.heguangwu.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

//第一种实现方式，通过 EnumValue
public enum GenderEnum {
    MALE("M", "男"),
    FEMALE("F", "女");

    // 标注枚举值和数据库字段映射
    @EnumValue
    private final String code;
    private final String desc;

    GenderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
