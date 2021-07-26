package crust.explorer.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SyncBlockVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 区块list
     */
    @ApiModelProperty("区块list")
    private List<BlockVO> blocks;
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
}
