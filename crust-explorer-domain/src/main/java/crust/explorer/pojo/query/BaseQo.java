package crust.explorer.pojo.query;

import crust.explorer.util.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("分页查询入参")
public class BaseQo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer current = Constants.DEFAULT_PAGE;

    @ApiModelProperty(value = "每页查询量", example = "25")
    private Integer size = Constants.DEFAULT_PAGE_SIZE;
}
