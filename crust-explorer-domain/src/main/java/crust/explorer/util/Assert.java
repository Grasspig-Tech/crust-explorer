/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package crust.explorer.util;

import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.enums.hint.UnforeseenEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.exception.BusinessException;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class Assert {

    public static void isNotBlank(String str, Object hintEnum) {
        if (!StringUtils.isNotBlank(str)) {
            throwBusinessException(hintEnum);
        }
    }

    public static void isBlank(String str, Object hintEnum) {
        if (!StringUtils.isBlank(str)) {
            throwBusinessException(hintEnum);
        }
    }

    public static void isNotNull(Object object, Object hintEnum) {
        if (!Objects.nonNull(object)) {
            throwBusinessException(hintEnum);
        }
    }

    public static void isNull(Object object, Object hintEnum) {
        if (!Objects.isNull(object)) {
            throwBusinessException(hintEnum);
        }
    }

    public static void isTrue(Boolean expression, Object hintEnum) {
        if (!expression) {
            throwBusinessException(hintEnum);
        }
    }

    private static void isTrue(Boolean expression) {
        if (!expression) {
            throw new BusinessException(LogicEnum.HINT_ENUM_TYPE_ERROR.getCode(), LogicEnum.HINT_ENUM_TYPE_ERROR.getMsg());
        }
    }

    public static void isFalse(Boolean expression, Object hintEnum) {
        if (expression) {
            throwBusinessException(hintEnum);
        }
    }

    private static void throwBusinessException(@NonNull Object hintEnum) {
        isTrue(hintEnum instanceof LogicEnum
                || hintEnum instanceof UnforeseenEnum
                || hintEnum instanceof ValidateEnum);
        if (hintEnum instanceof LogicEnum) {
            throw new BusinessException((LogicEnum) hintEnum);
        }
        if (hintEnum instanceof UnforeseenEnum) {
            throw new BusinessException((UnforeseenEnum) hintEnum);
        }
        if (hintEnum instanceof ValidateEnum) {
            throw new BusinessException((ValidateEnum) hintEnum);
        }
    }
}
