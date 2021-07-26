package crust.explorer.enums.hint;

/**
 * 逻辑校验枚举：
 * 被查询记录的状态不对；
 * 被查询记录的状态不对；
 * ……
 */
public enum LogicEnum {

    ORDER_IS_EXIST("1001", "订单已存在"),
    ORDER_NOT_EXIST("1002", "订单不存在"),
    ORDER_STATUS_ERROR("1003", "订单状态错误"),
    SIGN_ERROR("1004", "签名验证失败"),
    HINT_ENUM_TYPE_ERROR("1005", "枚举类型错误"),
    MODULE_TYPE_ERROR("1006", "频道类型错误"),
    NOT_SYNC_LOG("1007", "没有同步日志"),
    NOT_SYNC_RESERVED_TABLE("1008", "预留表不同步"),
    LAST_SYNC_LOG_ERROR("1009", "上次同步日志异常，请排查后重试"),
    THIS_TABLE_SYNC_DONE("1010", "该表已同步完成"),
    DATA_IS_NULL("1011", "暂无数据"),
    START_SYNC_BLOCK_NUM_ERROR("1012", "获取开始同步块失败"),
    RETRY_SYNC_BLOCK_ERROR("1013", "同步重试异常"),
    ;

    private String code;
    private String msg;

    LogicEnum(String code, String msg) {
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
