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
public class FrontMessage implements Serializable {
    private static final long serialVersionUID = -7155826104061362592L;
    private String channel;
    private Object body;
}
