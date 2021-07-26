package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.TransferPO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TableVo;
import crust.explorer.pojo.vo.TransferVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TransferMapper extends BaseMapper<TransferPO> {
    int createTable(@Param("tableNo") Integer tableNo);

    List<TableVo> getTotalCount(@Param("tableNos") List<Integer> tableNos, @Param("address") String address);

    List<TransferVO> listTransfersPage(@Param("pageTables") List<PageTableDto> pageTables, @Param("address") String address);

    PageVo<TransferVO> listTransfersAutoPage(IPage<TransferVO> page, @Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum);

    TransferVO getInfo(@Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum, @Param("eventIndex") String eventIndex);

    int syncTransfer(@Param("tableNo") Integer tableNo, @Param("transfers") List<TransferVO> transfers);

    int syncUpdateTransfer(@Param("tableNo") Integer tableNo, @Param("transfers") List<TransferVO> transfers);

    Integer getCountTransfer(@Param("tableNos") List<Integer> tableNos, @Param("beginTime") Long beginTime, @Param("endTime") Long endTime);

    BigDecimal getSumTransferAmount(@Param("tableNos") List<Integer> tableNos, @Param("beginTime") Long beginTime, @Param("endTime") Long endTime);
}
