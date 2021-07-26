package crust.explorer.pojo.vo;

import java.io.Serializable;
import java.util.List;

public class MetadataVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer nonce;
    private List<String> keys;
    private String token;
}
