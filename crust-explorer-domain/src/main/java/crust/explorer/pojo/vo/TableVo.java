package crust.explorer.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TableVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer tableNo;
    private Integer count;
}
