package crust.explorer.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleMessage implements Serializable {
    private static final long serialVersionUID = 4271239294675508685L;
    private String id;
    private String module;
    private String message;
}
