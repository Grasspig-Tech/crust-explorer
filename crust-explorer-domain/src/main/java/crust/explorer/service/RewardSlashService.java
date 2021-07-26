package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.RewardSlashPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.RewardSlashVO;
import crust.explorer.pojo.vo.TableVo;

import java.util.List;

public interface RewardSlashService extends IService<RewardSlashPO> {

    PageVo<RewardSlashVO> listPage(Integer blockNum, BaseQo qo);

    RewardSlashVO getInfo(Integer tableNo, Integer blockNum, String eventIndex);

    PageVo<RewardSlashVO> listPageByValidateAddress(String address, BaseQo qo);

    List<TableVo> getRewardSlashCountFromCache(List<Integer> tableNos, String address);

    List<TableVo> getAndPutRewardSlashCountToCache(List<Integer> tableNos, String address);
}
