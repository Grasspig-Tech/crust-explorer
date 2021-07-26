package crust.explorer.event;

import crust.explorer.enums.TableEnum;
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
public class PutChainCountCacheEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private TableEnum tableEnum;
    private String secondKey;
    private List<Integer> tableNos;
}
