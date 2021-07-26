package crust.explorer.enums.hint;


/**
 * 无法预料的异常枚举：
 * 调某接口异常;
 * 解析某json串异常;
 * 解析某时间异常；
 * ……
 */
public enum UnforeseenEnum {

    INTERFACE_SYNC_DATA_ERROR("1005", "同步数据失败!"),
    JSON_PARSE_ERROR("1006", "数据格式错误!"),
    TIME_PARSE_ERROR("1007", "时间格式错误!"),
    SERVER_INIT_ERROR("1008", "服务器初始化异常!"),
    SERVER_ENV_INIT_ERROR("1009", "服务器初始化环境异常!"),
    SYNC_DATA_ERROR("1010", "同步数据接口调用失败!"),
    SYNC_DATA_SLEEP_ERROR("1011", "同步数据sleep出错!"),
    SYNC_DATA_BODY_PARSE_IS_NULL("1012", "同步数据 body 解析为空!"),
    SYNC_DATA_BODY_IS_NULL("1013", "同步数据 body 为空!"),

    ;

    private String code;
    private String msg;

    UnforeseenEnum(String code, String msg) {
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
