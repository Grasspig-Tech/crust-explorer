package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.EventPO;
import crust.explorer.pojo.vo.EventVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TableVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventMapper extends BaseMapper<EventPO> {

    int createTable(@Param("tableNo") Integer tableNo);

    List<TableVo> getTotalCount(@Param("tableNos") List<Integer> tableNos);

    List<EventVO> listEventsPage(@Param("pageTables") List<PageTableDto> pageTables);

    PageVo<EventVO> listEventsAutoPage(IPage<EventVO> page, @Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum);

    EventVO getInfo(@Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum, @Param("eventIndex") String eventIndex);

    List<EventVO> getEvents(@Param("tableNo") Integer tableNo, @Param("blockNum") Integer blockNum, @Param("extrinsicIndex") String extrinsicIndex);

    int syncEvent(@Param("tableNo") Integer tableNo, @Param("events") List<EventVO> events);

    int syncUpdateEvent(@Param("tableNo") Integer tableNo, @Param("events") List<EventVO> events);
}
