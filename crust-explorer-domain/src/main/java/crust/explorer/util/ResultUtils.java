package crust.explorer.util;

import crust.explorer.pojo.Result;

import java.util.Objects;

public class ResultUtils {

    public static boolean isOk(Result result) {
        return Objects.nonNull(result) && Result.SUCCESS_CODE.equals(result.getCode());
    }

    public static boolean isFail(Result result) {
        return Objects.nonNull(result) && !Result.SUCCESS_CODE.equals(result.getCode());
    }

    public static boolean bodyIsEmpty(Result result) {
        return Objects.isNull(result);
    }

    public static boolean hasBody(String result) {
        return StringUtils.isNotBlank(result);
    }

}
