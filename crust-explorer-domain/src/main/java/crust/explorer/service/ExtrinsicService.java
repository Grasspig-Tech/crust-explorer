package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.ExtrinsicPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.ExtrinsicVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TableVo;

import java.util.List;

public interface ExtrinsicService extends IService<ExtrinsicPO> {
    PageVo<ExtrinsicVO> listPage(List<Integer> tableNos, String address, BaseQo qo);

    PageVo<ExtrinsicVO> listPage(Integer blockNum, BaseQo qo);

    ExtrinsicVO getInfo(Integer tableNo, Integer blockNum, String extrinsicIndex);

    List<TableVo> getExtrinsicCountFromCache(List<Integer> tableNos, String address);

    List<TableVo> getAndPutExtrinsicCountToCache(List<Integer> tableNos, String address);


    ExtrinsicVO getExtrinsicByHash(String extrinsicHash);
}
