package crust.explorer.enums;

import crust.explorer.util.RedisKeys;

public enum CountMapEnum {

    ACTIVE_PLEDGE_MAP(1, RedisKeys.ACTIVE_PLEDGE_MAP_KEY, "有效质押分布图"),
    WORK_CAPABILITY_MAP(2, RedisKeys.WORK_CAPABILITY_MAP_KEY, "有效算分布图"),
    ;

    private Integer code;
    private String mapKey;
    private String desc;

    CountMapEnum(Integer code, String mapKey, String desc) {
        this.code = code;
        this.mapKey = mapKey;
        this.desc = desc;
    }

    public static CountMapEnum getEnum(Integer code) {
        CountMapEnum[] values = values();
        for (CountMapEnum e : values) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMapKey() {
        return mapKey;
    }

    public String getDesc() {
        return desc;
    }
}
