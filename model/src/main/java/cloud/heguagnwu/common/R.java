package cloud.heguagnwu.common;

import lombok.Data;

@Data
public class R {
    private int code;
    private String msg;
    private Object data;

    public R(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R success() {
        return new R(200, "success", null);
    }
    public static R success(Object data) {
        return new R(200, "success", data);
    }
    public static R error() {
        return new R(500, "error", null);
    }
    public static R error(String msg) {
        return new R(500, msg, null);
    }
    public static R error(String msg, Object data) {
        return new R(500, msg, data);
    }
}
