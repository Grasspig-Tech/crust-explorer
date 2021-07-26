package crust.explorer.enums.hint;


/**
 * 参数校验枚举：
 * 缺少必传的入参;
 * 入参类型错误;
 * 入参格式错误；
 * 入参取值范围错误
 * ……
 */
public enum ValidateEnum {

    PARAM_IS_ERROR("5001", "参数错误!!"),
    PARAM_HAS_NULL("5002", "有空参数!!"),
    TEL_FORMAT_ERROR("1005", "电话号码格式错误!!"),
    EMAIL_FORMAT_ERROR("1005", "邮箱格式错误!!"),
    TIME_FORMAT_ERROR("3001", "时间格式不对!!"),
    TIME_FORMAT_MONTH_ERROR("3002", "月份只能是1-12月!!"),
    TIME_FORMAT_DAY_ERROR("3003", "日期只能是1-31号!!"),
    TIME_FORMAT_HOUR_ERROR("3004", "小时只能是0-23点!!"),
    TIME_FORMAT_MINUTE_ERROR("3005", "分钟只能是0-59分!!"),
    TIME_FORMAT_SECOND_ERROR("3006", "秒数只能是0-59秒!!"),
    MODULE_MESSAGE_FIELD_ERROR("3007", "消息字段错误!!"),
    TABLE_ERROR("3008", "table no is error!!"),
    QUERY_LAST_BLOCK_FAIL("3009", "查询最后块高失败!!"),
    COUNT_ENUM_ERROR("3010", "无该统计数据!!"),
    EXTRINSIC_HASH_AND_INDEX_IS_NULL("3011", "交易hash和交易id不能同时为空!!"),
    INDEX_IS_ERROR("3012", "index错误!!"),
    BLOCK_NUM_IS_ERROR("3013", "区块高度不一致!!"),
    TABLE_IS_NOT_EXIST("3014", "区块高不一致!!"),
    MAX_TABLE_IS_NOT_EXIST("3015", "最大值不存在!!"),
    ;

    private String code;
    private String msg;

    ValidateEnum(String code, String msg) {
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
