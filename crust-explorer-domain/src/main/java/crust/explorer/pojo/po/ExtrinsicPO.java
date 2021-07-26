package crust.explorer.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("ce_extrinsic")
public class ExtrinsicPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * lifetime
     */
    private String lifetime;
    /**
     * 参数
     */
    private String params;
    /**
     * 交易idx:2
     */
    private Integer extrinsicIdx;
    /**
     * 交易index:2242969-2
     */
    private String extrinsicIndex;
    /**
     * 交易sort:22429690002
     */
    private String extrinsicSort;
    /**
     * 区块hash
     */
    private String blockHash;
    /**
     * 区块号
     */
    private Integer blockNum;
    /**
     * 区块打包时间
     */
    private Integer blockTimestamp;
    /**
     * 操作（组件）:swork
     */
    private String callModule;
    /**
     * 操作方法（调用）:report_works
     */
    private String callModuleFunction;
    /**
     * 发送账户账号地址
     */
    private String accountId;
    /**
     * 发送账号json
     */
    private String accountDisplay;
    /**
     * 手续费:56642301
     */
    private BigDecimal fee;
    /**
     * 块状态：1-已确认，0-确认中
     */
    private Integer finalized;
    /**
     * 结果：1-成功，0-失败
     */
    private Integer success;
    /**
     * 是否签名：1-是，0-否
     */
    private Integer signed;
    /**
     * 随机数:3840
     */
    private Integer nonce;
    /**
     * 签名
     */
    private String signature;
    /**
     * 转账json
     */
    private String transfer;

    /**
     * 创建时间
     */
    private Date timeCreated;
    /**
     * 修改时间
     */
    private Date timeUpdated;

}
