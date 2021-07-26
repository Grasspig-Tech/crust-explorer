package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.ExtrinsicPO;
import crust.explorer.pojo.vo.ExtrinsicVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TableVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExtrinsicMapper extends BaseMapper<ExtrinsicPO> {
    int createTable(@Param("tableNo") Integer tableNo);

    Integer getCountExtrinsic(@Param("tableNos") List<Integer> tableNos, @Param("beginTime") Long beginTime, @Param("endTime") Long endTime);

    List<TableVo> getTotalCount(@Param("tableNos") List<Integer> tableNos, @Param("address") String address);

    List<ExtrinsicVO> listExtrinsicsPage(@Param("pageTables") List<PageTableDto> pageTables, @Param("address") String address);

    PageVo<ExtrinsicVO> listExtrinsicsAutoPage(IPage<ExtrinsicVO> page, @Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum);

    ExtrinsicVO getInfo(@Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum, @Param("extrinsicIndex") String extrinsicIndex);

    ExtrinsicVO getInfoByHash(@Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum, @Param("extrinsicHash") String extrinsicHash);

    int syncExtrinsic(@Param("tableNo") Integer tableNo, @Param("extrinsics") List<ExtrinsicVO> extrinsics);

    int syncUpdateExtrinsic(@Param("tableNo") Integer tableNo, @Param("extrinsics") List<ExtrinsicVO> extrinsics);

    ExtrinsicVO getExtrinsicByHash(@Param("tableNos") List<Integer> tableNos, @Param("extrinsicHash") String extrinsicHash);
}
