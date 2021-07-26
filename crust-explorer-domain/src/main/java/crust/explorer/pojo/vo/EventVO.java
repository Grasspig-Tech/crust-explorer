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
@ApiModel("事件出参")
public class EventVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;
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
     * 操作（事件）:WorksReportSuccess
     */
    @ApiModelProperty("操作（事件）:WorksReportSuccess")
    private String eventId;
    /**
     * 事件idx:1
     */
    @ApiModelProperty("事件idx:1")
    private Integer eventIdx;
    /**
     * 事件index:2242969-1
     */
    @ApiModelProperty("事件index:2242969-1")
    private String eventIndex;
    /**
     * 事件sort:22429690001
     */
    @ApiModelProperty("事件sort:22429690001")
    private String eventSort;
    /**
     * 交易hash
     */
    @ApiModelProperty("交易hash")
    private String extrinsicHash;
    /**
     * 交易index:2242969-2
     */
    @ApiModelProperty("交易index:2242969-2")
    private String extrinsicIndex;
    /**
     * 交易idx:1
     */
    @ApiModelProperty("交易idx:1")
    private String extrinsicIdx;
    /**
     * 块状态：1-已确认，0-确认中
     */
    @ApiModelProperty("块状态：1-已确认，0-确认中")
    private Integer finalized;
    /**
     * 结果：1-成功，0-失败
     */
    @ApiModelProperty("结果：1-成功，0-失败")
    private Integer success;
    /**
     * 事件类型
     */
    @ApiModelProperty("事件类型")
    private String type;
    /**
     * 操作（组件）:swork
     */
    @ApiModelProperty("操作（组件）:swork")
    private String moduleId;
    /**
     * 参数
     */
    @ApiModelProperty("参数")
    private String params;
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
