package crust.explorer.enums;

public enum SyncLogTypeEnum {

    DONE(1, "done"),
    ERROR(2, "error"),
    SYNC_TO(3, "已同步到:"),
    ;

    private Integer type;
    private String typeDesc;

    SyncLogTypeEnum(Integer type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static SyncLogTypeEnum getEnum(Integer type) {
        SyncLogTypeEnum[] values = values();
        for (SyncLogTypeEnum e : values) {
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
