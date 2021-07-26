package crust.explorer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session implements Serializable {
    private static final long serialVersionUID = -8205883917213242571L;

    private String id;
    private String module;

}
