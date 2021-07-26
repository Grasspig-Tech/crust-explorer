package crust.explorer.config;


import crust.explorer.enums.hint.DefaultEnum;
import crust.explorer.enums.hint.UnforeseenEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.exception.BusinessException;
import crust.explorer.pojo.Result;
import crust.explorer.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("GlobalExceptionHandler.handleException,e:", e);
        if (e instanceof BadSqlGrammarException || e instanceof SQLSyntaxErrorException) {
            if (StringUtils.isNotBlank(e.getMessage())
                    && e.getMessage().contains("Table")
                    && e.getMessage().contains("doesn't exist")) {
                return Result.failure(ValidateEnum.TABLE_IS_NOT_EXIST);
            }
        }
        return Result.failure();
    }

    /**
     * 针对 @Valid 和 @Validated 用在方法参数  &&  验证参数是一个对象
     * eg: public Result<User> edit(@Validated UserAuthVo authVo, HttpSession session)
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        return handleParamBindExceptionmain(e.getBindingResult());
    }

    /**
     * 针对 @Valid 和 @Validated 用在方法参数  &&  验证参数是一个对象
     * eg: public Result modifyMoneyFlow(@RequestBody @Validated MoneyFlowVo moneyFlowVo)
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleBindException(MethodArgumentNotValidException e) {
        return handleParamBindExceptionmain(e.getBindingResult());
    }

    /**
     * 针对 @Valid 和 @Validated 用在方法参数  &&  验证参数是单个参数
     * eg: public Result modifyMoneyFlow(@Validated @RequestParam("birthday") @NotNull(message="生日不能为空") Date birthday)
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        ValidateEnum paramIsNull = ValidateEnum.PARAM_IS_ERROR;
        String message = e.getMessage();
        if (StringUtils.isBlank(message)) {
            message = e.getLocalizedMessage();
        }
        if (StringUtils.isBlank(message)) {
            message = paramIsNull.getMsg();
        }
        return Result.failure(paramIsNull.getCode(), message);
    }

    private Result handleParamBindExceptionmain(BindingResult bindingResult) {
        ValidateEnum paramIsNull = ValidateEnum.PARAM_IS_ERROR;
        /**
         * 我们一般只是在实体用到javax.validation的注解自动校验字段，
         * 故我们先处理字段异常，不行再处理所有异常，最后用枚举兜底
         */
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            for (int i = 0; i < fieldErrors.size(); i++) {
                String defaultMessage = fieldErrors.get(i).getDefaultMessage();
                if (StringUtils.isBlank(defaultMessage)) {
                    defaultMessage = paramIsNull.getMsg();
                }
                // 有时候会有嵌套异常，导致提示很乱，只取有用提示
                if (defaultMessage.contains(BusinessException.class.getName())) {
                    String[] split = defaultMessage.split(":");
                    defaultMessage = split[split.length - 1];
                }
                return Result.failure(paramIsNull.getCode(), defaultMessage);
            }
        }
        List<ObjectError> errors = bindingResult.getAllErrors();
        if (!CollectionUtils.isEmpty(errors)) {
            for (int i = 0; i < errors.size(); i++) {
                String defaultMessage = errors.get(i).getDefaultMessage();
                if (StringUtils.isBlank(defaultMessage)) {
                    defaultMessage = paramIsNull.getMsg();
                }
                return Result.failure(paramIsNull.getCode(), defaultMessage);
            }
        }
        return Result.failure(paramIsNull);
    }

    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        return Result.failure(e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (e.getMessage().contains("JSON parse error")) {
            return Result.failure(UnforeseenEnum.JSON_PARSE_ERROR);
        }
        return Result.failure(DefaultEnum.SYSTEM_ERROR);
    }

    public static String getRealMessage(Throwable e) {
        // 如果e不为空，则去掉外层的异常包装
        while (e != null) {
            Throwable cause = e.getCause();
            if (cause == null) {
                return e.getMessage();
            }
            e = cause;
        }
        return "";
    }

}
