package crust.explorer.enums;

/**
 * websocket module 枚举
 */
public enum ModuleEnum {

    INDEX("index", "首页", "首页"),

    BLOCK("block", "区块链", "区块"),
    EXTRINSIC("extrinsic", "区块链", "交易"),
    TRANSFER("transfer", "区块链", "转账"),

    ACTIVE_PLEDGE("active_pledge", "统计", "有效质押"),
    BASE_FEE("base_fee", "统计", "手续费");

    private String module;
    private String htmlLabel;
    private String desc;

    ModuleEnum(String module, String htmlLabel, String desc) {
        this.module = module;
        this.htmlLabel = htmlLabel;
        this.desc = desc;
    }

    public static ModuleEnum getEnum(String module) {
        ModuleEnum[] values = values();
        for (ModuleEnum e : values) {
            if (e.module.equals(module)) {
                return e;
            }
        }
        return null;
    }

    public String getModule() {
        return module;
    }

    public String getHtmlLabel() {
        return htmlLabel;
    }

    public String getDesc() {
        return desc;
    }
}
