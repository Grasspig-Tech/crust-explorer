package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.BlockPO;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.TableVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlockMapper extends BaseMapper<BlockPO> {

    int createTable(@Param("tableNo") Integer tableNo);

    int syncBlock(@Param("tableNo") Integer tableNo, @Param("blocks") List<BlockVO> blocks);

    int syncUpdateBlock(@Param("tableNo") Integer tableNo, @Param("blocks") List<BlockVO> blocks);

    List<TableVo> getTotalCount(@Param("tableNos") List<Integer> tableNos);

    BlockVO getByBlockNum(@Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum);

    List<BlockVO> getBlocksPage(@Param("tableNo") Integer tableNo, @Param("beginIndex") Integer beginIndex, @Param("pageSize") Integer pageSize);

    List<BlockVO> listBlocksPage(@Param("pageTables") List<PageTableDto> pageTables);

    Integer getCountBlock(@Param("tableNos") List<Integer> tableNos, @Param("beginTime") Long beginTime, @Param("endTime") Long endTime);

    BlockVO getBlockByHash(@Param("tableNos") List<Integer> tableNos, @Param("hash") String hash);
}

