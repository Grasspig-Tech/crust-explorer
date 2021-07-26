package crust.explorer.pojo;

import crust.explorer.enums.hint.DefaultEnum;
import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.enums.hint.UnforeseenEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ToString
@Getter
@ApiModel("总出参")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 123152879210199618L;

    public static final String SUCCESS_CODE = DefaultEnum.SYSTEM_OK.getCode();
    private static final String SUCCESS_MSG = DefaultEnum.SYSTEM_OK.getMsg();
    private static final String FAIL_CODE = DefaultEnum.SYSTEM_ERROR.getCode();
    private static final String FAIL_MSG = DefaultEnum.SYSTEM_ERROR.getMsg();

    private static final String PUT_MAP_ERROR_MSG = "map is required";
    @ApiModelProperty("返回code: '200'-成功，'其他code'-失败")
    private String code;
    @ApiModelProperty("返回提示")
    private String msg;
    @ApiModelProperty("返回数据")
    private T data;

    /**
     * 该方法一定不要声明成泛型，原因是没有入参的方法用泛型接收，会默认把 T 当成Object，具体分析如下：
     * 1、该方法声明成泛型接收返回：public static <T> Result<T> success()，
     * 只能写：
     * Result put = Result.success().put(new User());
     * Result<Object> put1 = Result.success().put(new User());
     * 写：
     * Result<User> put2 = Result.success().put(new User()); 会编译报错
     * 也可以先写
     * Result<User> put2 = Result.success(); 或
     * Result put2 = Result.success();
     * 再写
     * Result<User> put3 = put2.put(new User())分两步来间接接收;
     * 2、该方法按当前方式不声明成泛型：public static Result success()，
     * 能用三种方式写（能直接接收，也能间接接收，十分灵活）：
     * Result put = Result.success().put(new User());
     * Result<Object> put1 = Result.success().put(new User());
     * Result<User> put2 = Result.success().put(new User());
     *
     * @return
     */
    public static Result success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(SUCCESS_CODE);
        r.setMsg(SUCCESS_MSG);
        r.setData(data);
        return r;
    }

    public static Result failure() {
        return failure(FAIL_CODE, FAIL_MSG);
    }

    public static Result failure(String msg) {
        return failure(FAIL_CODE, msg);
    }

    public static Result failure(String code, String msg) {
        Result r = new Result<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static Result failure(BusinessException e) {
        Result r = new Result<>();
        r.setCode(e.getCode());
        r.setMsg(e.getMsg());
        return r;
    }

    public static Result failure(DefaultEnum defaultEnum) {
        Result r = new Result<>();
        r.setCode(defaultEnum.getCode());
        r.setMsg(defaultEnum.getMsg());
        return r;
    }

    public static Result failure(LogicEnum logicEnum) {
        Result r = new Result<>();
        r.setCode(logicEnum.getCode());
        r.setMsg(logicEnum.getMsg());
        return r;
    }

    public static Result failure(UnforeseenEnum unforeseenEnum) {
        Result r = new Result<>();
        r.setCode(unforeseenEnum.getCode());
        r.setMsg(unforeseenEnum.getMsg());
        return r;
    }

    public static Result failure(ValidateEnum validateEnum) {
        Result r = new Result<>();
        r.setCode(validateEnum.getCode());
        r.setMsg(validateEnum.getMsg());
        return r;
    }

    public Result<T> put(T data) {
        this.setData(data);
        return this;
    }

    public Result<Map> put(String key, Object value) {
        Result<Map> r = null;
        if (Objects.isNull(this.data)) {
            r = new Result<>();
            r.setCode(this.code);
            r.setMsg(this.msg);
            Map data = new HashMap();
            data.put(key, value);
            r.setData(data);
        } else {
            if (this.data instanceof Map) {
                Map data = (Map) this.data;
                data.put(key, value);
                r = (Result<Map>) this;
            } else {
                throw new BusinessException(FAIL_CODE, PUT_MAP_ERROR_MSG);
            }
        }
        return r;
    }

    // public protected private get set
    private void setCode(String code) {
        this.code = code;
    }
    private void setMsg(String msg) {
        this.msg = msg;
    }
    private void setData(T data) {
        this.data = data;
    }
}
