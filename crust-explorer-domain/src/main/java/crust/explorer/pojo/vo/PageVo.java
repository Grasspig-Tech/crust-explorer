package crust.explorer.pojo.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.util.Constants;
import crust.explorer.util.MathUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@ApiModel(value = "分页出参", description = "分页出参")
public class PageVo<T> implements IPage<T>, Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("当前页码")
    @Getter
    private long current = Constants.DEFAULT_PAGE;

    @Getter
    @ApiModelProperty("每页查询量")
    private long size = Constants.DEFAULT_PAGE_SIZE;

    //    @Getter
    @ApiModelProperty("最大页数")
    private long pages = MathUtils.ZERO;

    @Getter
    @ApiModelProperty("数据总条数")
    private long total = MathUtils.ZERO;

    @Getter
    @ApiModelProperty("当前页数据")
    private List<T> records = new ArrayList<>();

    private PageVo() {
    }

    private PageVo(long current, long size) {
        if (current > 0) {
            this.current = current;
        }
        if (size > 0) {
            this.size = size;
        }
    }

    public static <T> PageVo<T> initialized(long current, long size) {
        return new PageVo<T>(current, size);
    }

    public static <T> PageVo<T> initialized(BaseQo qo) {
        return initialized(qo.getCurrent(), qo.getSize());
    }

    public static <T> PageVo<T> initialized(BaseQo qo, long total) {
        PageVo<T> pageVo = initialized(qo);
        pageVo.total = total;
        return pageVo;
    }

    public static <T> PageVo<T> initialized(IPage page) {
        return new PageVo<T>(page);
    }

    public PageVo(IPage<T> page) {
        if (Objects.nonNull(page)) {
            this.current = page.getCurrent();
            this.size = page.getSize();
            this.pages = page.getPages();
            this.total = page.getTotal();
            this.records = page.getRecords();
        }
    }

    public PageVo(long current, long size, long total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.records = records;
    }

    @JsonIgnore
    @ApiModelProperty(value = "忽略接口hitCount字段", hidden = true)
    public boolean isHitCount() {
        return false;
    }

    @JsonIgnore
    @ApiModelProperty(value = "忽略接口searchCount字段", hidden = true)
    public boolean isSearchCount() {
        return true;
    }

    @Override
    public List<OrderItem> orders() {
        return null;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }
}
