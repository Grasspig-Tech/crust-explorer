package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.mapper.EventMapper;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.EventPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.EventVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TableVo;
import crust.explorer.service.EventService;
import crust.explorer.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class EventServiceImpl extends ServiceImpl<EventMapper, EventPO> implements EventService {


    @Override
    public PageVo<EventVO> listPage(List<Integer> tableNos, BaseQo qo) {
        long begin = System.currentTimeMillis();
        List<TableVo> totalCount = this.baseMapper.getTotalCount(tableNos);
        log.info("事件列表分页接口 耗时 count ：{}", (System.currentTimeMillis() - begin));
        List<PageTableDto> pageTables = TableUtils.buildPageTables(totalCount, qo);
        if (CollectionUtils.isEmpty(pageTables)) {
            return PageVo.initialized(qo, totalCount.get(0).getCount());
        }
        begin = System.currentTimeMillis();
        List<EventVO> events = baseMapper.listEventsPage(pageTables);
        log.info("事件列表分页接口 耗时 list ：{}", (System.currentTimeMillis() - begin));
        return new PageVo<>(
                qo.getCurrent(),
                qo.getSize(),
                totalCount.get(0).getCount(),
                events
        );
    }

    @Override
    public PageVo<EventVO> listPage(Integer blockNum, BaseQo qo) {
        int tableNo = TableUtils.getTableNo(blockNum);
        qo = TableUtils.rectifyBaseQo(qo);
        PageVo<EventVO> pageVo = this.baseMapper.listEventsAutoPage(PageVo.initialized(qo), tableNo, blockNum);
        return pageVo;
    }

    @Override
    public EventVO getInfo(Integer tableNo, Integer blockNum, String eventIndex) {
        return this.baseMapper.getInfo(tableNo, blockNum, eventIndex);
    }

}
