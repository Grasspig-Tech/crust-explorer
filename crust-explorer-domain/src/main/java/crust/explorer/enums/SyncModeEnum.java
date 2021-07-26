package crust.explorer.enums;

public enum SyncModeEnum {

    AUTO("auto"),
    JOB("job"),
    REFRESH("refresh"),
    MANUAL("manual"),
    ;

    private String type;

    SyncModeEnum(String type) {
        this.type = type;
    }

    public static SyncModeEnum getEnum(String type) {
        SyncModeEnum[] values = values();
        for (SyncModeEnum e : values) {
            if (e.type.equals(type)) {
                return e;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

}
