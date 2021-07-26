package crust.explorer.enums;

import crust.explorer.util.RedisKeys;

public enum TableEnum {

    CE_BLOCK("ce_block", RedisKeys.BLOCK_TOTAL_COUNT, "区块信息表"),
    CE_EVENT("ce_event", RedisKeys.EVENT_TOTAL_COUNT, "事件信息表"),
    CE_EXTRINSIC("ce_extrinsic", RedisKeys.EXTRINSIC_TOTAL_COUNT, "交易信息表"),
    CE_REWARD_SLASH("ce_reward_slash", RedisKeys.REWARD_SLASH_TOTAL_COUNT, "奖励惩罚表"),
    CE_TRANSFER("ce_transfer", RedisKeys.TRANSFER_TOTAL_COUNT, "转账信息表"),

    ;
    private String tableName;
    private String totalCountKey;
    private String desc;

    TableEnum(String tableName, String totalCountKey, String desc) {
        this.tableName = tableName;
        this.totalCountKey = totalCountKey;
        this.desc = desc;
    }

    public static TableEnum getEnum(String tableName) {
        TableEnum[] values = values();
        for (TableEnum e : values) {
            if (e.tableName.equals(tableName)) {
                return e;
            }
        }
        return null;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTotalCountKey() {
        return totalCountKey;
    }

    public String getDesc() {
        return desc;
    }
}
