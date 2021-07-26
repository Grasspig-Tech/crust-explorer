package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.pojo.po.SysSyncLogPO;
import crust.explorer.pojo.vo.BlockVO;

import java.util.List;

public interface SysSyncLogService extends IService<SysSyncLogPO> {

    void initSyncToLog(boolean isJob, BlockVO block);

    void recordSyncToLog(SyncBlockEvent event, List<BlockVO> blocks);

    SysSyncLogPO getSyncToLog(SyncBlockEvent event);

    void removeLongAgoData();
}
