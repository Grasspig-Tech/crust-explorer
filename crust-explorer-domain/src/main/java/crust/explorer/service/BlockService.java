package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.pojo.po.BlockPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TableVo;

import java.util.List;

public interface BlockService extends IService<BlockPO> {

    void publishSyncHistoryBlock(BlockVO block);

    List<TableVo> getBlockCountFromCache(List<Integer> tableNos);

    List<TableVo> getAndPutBlockCountToCache(List<Integer> tableNos);

    void syncHistoryBlock(SyncBlockEvent event) throws InterruptedException;

    void syncLastPage(List<BlockVO> blockVOS);

    void syncLastTenDay();

    void refreshBlock(List<BlockVO> blockVOS);

    PageVo<BlockVO> listPage(List<Integer> tableNos, BaseQo qo);

    BlockVO getByBlockNum(Integer tableNo, Integer blockNum);

    BlockVO getBlockByHash(String hash);

    Integer getCountBlock(List<Integer> tableNos, long begin, long end);
}
