package crust.explorer.enums;

public enum FinalizedEnum {

    FINALIZED(1, "finalized"),
    UN_FINALIZED(0, "unFinalized"),

    ;
    private Integer code;
    private String desc;

    FinalizedEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FinalizedEnum Integer(String code) {
        FinalizedEnum[] values = values();
        for (FinalizedEnum e : values) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
