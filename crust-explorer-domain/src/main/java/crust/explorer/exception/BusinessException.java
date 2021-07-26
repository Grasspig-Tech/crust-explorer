package crust.explorer.exception;

import crust.explorer.enums.hint.DefaultEnum;
import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.enums.hint.UnforeseenEnum;
import crust.explorer.enums.hint.ValidateEnum;
import lombok.Getter;
import crust.explorer.util.StringUtils;
import org.springframework.lang.NonNull;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -5987435509748839767L;

    private static final String DEFAULT_CODE = DefaultEnum.SYSTEM_ERROR.getCode();
    private static final String DEFAULT_MSG = DefaultEnum.SYSTEM_ERROR.getMsg();

    @Getter
    private String code;
    @Getter
    private String msg;

    public BusinessException(String code, String message) {
        super(message);
        if (StringUtils.isNotBlank(code)) {
            this.code = code;
        } else {
            this.code = DEFAULT_CODE;
        }
        if (StringUtils.isNotBlank(message)) {
            this.msg = message;
        } else {
            this.msg = DEFAULT_MSG;
        }
    }

    public BusinessException(String message) {
        this(null, message);
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        if (StringUtils.isNotBlank(code)) {
            this.code = code;
        } else {
            this.code = DEFAULT_CODE;
        }
        if (StringUtils.isNotBlank(message)) {
            this.msg = message;
        } else {
            this.msg = DEFAULT_MSG;
        }
    }

    public BusinessException(@NonNull DefaultEnum defaultEnum) {
        this(defaultEnum.getCode(), defaultEnum.getMsg());
    }

    public BusinessException(@NonNull LogicEnum logicEnum) {
        this(logicEnum.getCode(), logicEnum.getMsg());
    }

    public BusinessException(@NonNull UnforeseenEnum unforeseenEnum) {
        this(unforeseenEnum.getCode(), unforeseenEnum.getMsg());
    }

    public BusinessException(@NonNull ValidateEnum validateEnum) {
        this(validateEnum.getCode(), validateEnum.getMsg());
    }

}
