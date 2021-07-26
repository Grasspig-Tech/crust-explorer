package crust.explorer.enums;


import java.util.Calendar;

public enum CountEnum {


    EXTRINSIC_COUNT_1H("extrinsic_count_1h", "extrinsic", 1, Calendar.HOUR_OF_DAY),
    EXTRINSIC_COUNT_6H("extrinsic_count_6h", "extrinsic", 6, Calendar.HOUR_OF_DAY),
    EXTRINSIC_COUNT_1D("extrinsic_count_1d", "extrinsic", 1, Calendar.DAY_OF_MONTH),

    TRANSFER_COUNT_1H("transfer_count_1h", "transfer", 1, Calendar.HOUR_OF_DAY),
    TRANSFER_COUNT_6H("transfer_count_6h", "transfer", 6, Calendar.HOUR_OF_DAY),
    TRANSFER_COUNT_1D("transfer_count_1d", "transfer", 1, Calendar.DAY_OF_MONTH),
    ;

    private String redisKey;
    private String type;
    private Integer range;
    private Integer offset;

    CountEnum(String redisKey, String type, Integer range, Integer offset) {
        this.redisKey = redisKey;
        this.type = type;
        this.range = range;
        this.offset = offset;
    }

    public static CountEnum getEnum(String channel) {
        CountEnum[] values = values();
        for (CountEnum e : values) {
            if (e.redisKey.equals(channel)) {
                return e;
            }
        }
        return null;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public String getType() {
        return type;
    }

    public Integer getRange() {
        return range;
    }

    public Integer getOffset() {
        return offset;
    }
}
