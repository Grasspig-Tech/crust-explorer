package crust.explorer.event;

import crust.explorer.pojo.vo.BlockVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshBlockEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<BlockVO> blocks;
}
