package crust.explorer.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("纪元总出参")
@Data
public class EraVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 账户优先级：1.验证人 2.候选人 3.提名人
     */
    @ApiModelProperty("纪元")
    private EraStatVO eraStat;
    @ApiModelProperty("冻结质押列表")
    private List<BondedPledgeVO> bondedPledges;
    @ApiModelProperty("成员列表")
    private List<MemberVO> members;
    @ApiModelProperty("提名人列表")
    private List<NominatorVO> nominators;
    @ApiModelProperty("账户列表")
    private List<AccountVO> accounts;
}
