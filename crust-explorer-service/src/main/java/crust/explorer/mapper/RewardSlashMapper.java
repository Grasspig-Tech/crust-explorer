package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.RewardSlashPO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.RewardSlashVO;
import crust.explorer.pojo.vo.TableVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RewardSlashMapper extends BaseMapper<RewardSlashPO> {

    int createTable(@Param("tableNo") Integer tableNo);

    List<TableVo> getTotalCount(@Param("tableNos") List<Integer> tableNos, @Param("address") String address);

    List<RewardSlashVO> listPageByValidateAddress(@Param("pageTables") List<PageTableDto> pageTables, @Param("address") String address);

    PageVo<RewardSlashVO> listEventsAutoPage(IPage<RewardSlashVO> page, @Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum);

    RewardSlashVO getInfo(@Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum, @Param("eventIndex") String eventIndex);

    int syncRewardSlash(@Param("tableNo") Integer tableNo, @Param("rewardSlashes") List<RewardSlashVO> rewardSlashes);

    int syncUpdateRewardSlash(@Param("tableNo") Integer tableNo, @Param("rewardSlashes") List<RewardSlashVO> rewardSlashes);

}
