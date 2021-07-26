package crust.explorer.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("区块出参")
public class BlockVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;

    /**
     * 块状态：1-已确认，0-确认中
     */
    @ApiModelProperty("块状态：1-已确认，0-确认中")
    private Integer finalized;
    /**
     * 区块号
     */
    @ApiModelProperty("区块号")
    private Integer blockNum;
    /**
     * 区块打包时间
     */
    @ApiModelProperty("区块打包时间")
    private Integer blockTimestamp;
    /**
     * 区块hash
     */
    @ApiModelProperty("区块hash")
    private String hash;
    /**
     * 父hash
     */
    @ApiModelProperty("父hash")
    private String parentHash;
    /**
     * 事件数
     */
    @ApiModelProperty("事件数")
    private Integer eventCount;
    /**
     * 交易数
     */
    @ApiModelProperty("交易数")
    private Integer extrinsicsCount;
    /**
     * 状态根
     */
    @ApiModelProperty("状态根")
    private String stateRoot;
    /**
     * 交易根
     */
    @ApiModelProperty("交易根")
    private String extrinsicsRoot;
    /**
     * 运行时版本
     */
    @ApiModelProperty("运行时版本")
    private Integer specVersion;
    /**
     * 验证人json
     */
    @ApiModelProperty("验证人json")
    private String accountDisplay;
    /**
     * 验证人地址
     */
    @ApiModelProperty("验证人地址")
    private String validator;
    /**
     * 事件list
     */
    @ApiModelProperty("事件list")
    private List<EventVO> events;
    /**
     * 交易list
     */
    @ApiModelProperty("交易list")
    private List<ExtrinsicVO> extrinsics;
    /**
     * 转账list
     */
    @ApiModelProperty("转账list")
    private List<TransferVO> transfers;
    /**
     * 奖惩list
     */
    @ApiModelProperty("奖惩list")
    private List<RewardSlashVO> rewardSlashes;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date timeCreated;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date timeUpdated;

}
