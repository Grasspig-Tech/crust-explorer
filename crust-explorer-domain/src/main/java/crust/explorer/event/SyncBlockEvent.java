package crust.explorer.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncBlockEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private String syncMode;
    private String tableName;
    private Integer tableNo;
    private Integer maxTableNo;
    private Integer networkBlockNum;
    private String dKey;
}
