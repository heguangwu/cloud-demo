package cloud.heguangwu.system.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

//第二种实现方式 通过实现 IEnum
public enum StatusEnum implements IEnum<Integer> {
    ENABLE(0, "正常"),
    DISABLE(1, "停用");

    private final Integer value;
    private final String desc;

    StatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
