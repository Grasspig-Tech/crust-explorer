package crust.explorer.pojo.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PageTableDto implements Serializable {
    private static final long serialVersionUID = -2483255181562816356L;
    private Integer  tableNo ;
    private Integer  begin ;
    private Integer  pageSize ;
}
