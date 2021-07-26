package crust.explorer.enums;

/**
 * websocket channel 枚举
 */
public enum ChannelEnum {

    NETWORK("network", ModuleEnum.INDEX.getModule(), "信息概览"),
    LAST_BLOCKS("last_blocks", ModuleEnum.INDEX.getModule(), "最新区块"),

    EXTRINSIC_RECORD_lH("extrinsic_record_1h", ModuleEnum.EXTRINSIC.getModule(), "1h历史交易记录"),
    EXTRINSIC_RECORD_6H("extrinsic_record_6h", ModuleEnum.EXTRINSIC.getModule(), "6h历史交易记录"),
    EXTRINSIC_RECORD_1D("extrinsic_record_1d", ModuleEnum.EXTRINSIC.getModule(), "1d历史交易记录"),

    ACTIVE_POWER("active_power", ModuleEnum.ACTIVE_PLEDGE.getModule(), "矿工有效算力分布"),
    MINING_REWARD("mining_reward", ModuleEnum.ACTIVE_PLEDGE.getModule(), "矿工有效算力分布"),

    BASE_FEE_D("base_fee_d", ModuleEnum.BASE_FEE.getModule(), "基础费率24h走势"),
    BASE_FEE_W("base_fee_w", ModuleEnum.BASE_FEE.getModule(), "基础费率7d走势"),
    BASE_FEE_M("base_fee_m", ModuleEnum.BASE_FEE.getModule(), "基础费率30d走势"),
    BASE_FEE_Y("base_fee_y", ModuleEnum.BASE_FEE.getModule(), "基础费率1年走势"),
    ;

    private String channel;
    private String module;
    private String desc;

    ChannelEnum(String channel, String module, String desc) {
        this.channel = channel;
        this.module = module;
        this.desc = desc;
    }

    public static ChannelEnum getEnum(String channel) {
        ChannelEnum[] values = values();
        for (ChannelEnum e : values) {
            if (e.channel.equals(channel)) {
                return e;
            }
        }
        return null;
    }

    public String getChannel() {
        return channel;
    }

    public String getModule() {
        return module;
    }

    public String getDesc() {
        return desc;
    }
}
