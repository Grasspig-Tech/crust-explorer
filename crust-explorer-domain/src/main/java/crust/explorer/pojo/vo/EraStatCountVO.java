package crust.explorer.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("纪元统计出参")
public class EraStatCountVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;
    /**
     * 纪元：760
     */
    @ApiModelProperty("纪元：760")
    private Integer era;
    /**
     * 验证人账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW
     */
    @ApiModelProperty("验证人账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    private String validatorStash;
    /**
     * 起始块：2242439
     */
    @ApiModelProperty("起始块：2242439")
    private Integer startBlockNum;
    /**
     * 结束块：2243832
     */
    @ApiModelProperty("结束块：2243832")
    private Integer endBlockNum;
    /**
     * 起始块打包时间:1625120364
     */
    @ApiModelProperty("起始块打包时间:1625120364")
    private Integer startBlockTimestamp;
    /**
     * 结束块打包时间:1625120364
     */
    @ApiModelProperty("结束块打包时间:1625120364")
    private Integer endBlockTimestamp;
    /**
     * 所出块
     */
    @ApiModelProperty("所出块")
    private String blockProduced;
    /**
     * 出块数量:74
     */
    @ApiModelProperty("出块数量:74")
    private Integer blockProducedCount;
    /**
     * 状态：1-已结束；0-未结束
     */
    @ApiModelProperty("状态：1-已结束；0-未结束")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty("修改时间")
    private Date timeCreated;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date timeUpdated;

}
