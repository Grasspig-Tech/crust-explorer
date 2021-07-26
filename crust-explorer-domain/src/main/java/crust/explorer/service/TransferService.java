package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.TransferPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TableVo;
import crust.explorer.pojo.vo.TransferVO;

import java.util.List;

public interface TransferService extends IService<TransferPO> {
    PageVo<TransferVO> listPage(List<Integer> tableNos, String address, BaseQo qo);

    PageVo<TransferVO> listPage(Integer blockNum, BaseQo qo);

    TransferVO getInfo(Integer tableNo, Integer blockNum, String extrinsicIndex);

    List<TableVo> getTransferCountFromCache(List<Integer> tableNos, String address);

    List<TableVo> getAndPutTransferCountToCache(List<Integer> tableNos, String address);
}
