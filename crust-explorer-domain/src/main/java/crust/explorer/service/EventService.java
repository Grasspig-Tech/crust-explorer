package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.EventPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.EventVO;
import crust.explorer.pojo.vo.PageVo;

import java.util.List;

public interface EventService extends IService<EventPO> {


    PageVo<EventVO> listPage(List<Integer> tableNos, BaseQo qo);

    PageVo<EventVO> listPage(Integer blockNum, BaseQo qo);

    EventVO getInfo(Integer tableNo, Integer blockNum, String eventIndex);
}
