package crust.explorer.pojo.copier;

import crust.explorer.pojo.dto.WorkPledgeMapDto;
import crust.explorer.pojo.po.AccountPO;
import crust.explorer.pojo.po.BlockPO;
import crust.explorer.pojo.po.NetworkOverviewPO;
import crust.explorer.pojo.vo.AccountVO;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.NetworkOverviewVO;
import crust.explorer.pojo.vo.WorkPledgeMapVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Mapper
public interface BlockCopier {

    BlockCopier INSTANCE = Mappers.getMapper(BlockCopier.class);


    BlockVO toBlockVO(BlockPO blockPO);

    AccountVO toAccountVO(AccountPO accountPO);

    @Mapping(source = "mapRatio", target = "mapRatio", qualifiedByName = "mapRatioFormat")
    WorkPledgeMapVO toWorkPledgeMapVO(WorkPledgeMapDto workPledgeMapDto);

    List<WorkPledgeMapVO> toWorkPledgeMapVOs(List<WorkPledgeMapDto> dtos);

    NetworkOverviewVO toNetworkOverviewVO(NetworkOverviewPO networkOverviewPO);

    List<BlockVO> toBlockVOs(List<BlockPO> blockPOs);

    @Named("mapRatioFormat")
    default String mapRatioFormat(BigDecimal source) {
        if (Objects.nonNull(source)) {
            return source.stripTrailingZeros().toPlainString();
        }
        return "0.00";
    }
}
