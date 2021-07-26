package crust.explorer.enums;

public enum CompositeEnum {

    /**
     * 1-区块，2-交易，3-验证人
     */
    BLOCK(1, "区块详情"),
    EXTRINSIC(2, "交易详情"),
    ACCOUNT(3, "账户详情"),
    ;

    private Integer type;
    private String typeDesc;

    CompositeEnum(Integer type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static CompositeEnum getEnum(Integer type) {
        CompositeEnum[] values = values();
        for (CompositeEnum e : values) {
            if (e.type.equals(type)) {
                return e;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }
}
