package crust.explorer.enums.hint;

/**
 * 系统默认异常提示枚举：不允许再扩展
 */
public enum DefaultEnum {

    SYSTEM_OK("200", "OK"),
    SYSTEM_ERROR("500", "系统异常，请重试"),
    ;

    private String code;
    private String msg;

    DefaultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
