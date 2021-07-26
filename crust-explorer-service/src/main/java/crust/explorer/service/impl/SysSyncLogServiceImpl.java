package crust.explorer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.enums.SyncLogTypeEnum;
import crust.explorer.enums.SyncModeEnum;
import crust.explorer.enums.TableEnum;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.mapper.SysSyncLogMapper;
import crust.explorer.pojo.po.SysSyncLogPO;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.service.SysSyncLogService;
import crust.explorer.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service
@Slf4j
public class SysSyncLogServiceImpl extends ServiceImpl<SysSyncLogMapper, SysSyncLogPO> implements SysSyncLogService {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void initSyncToLog(boolean isJob, BlockVO block) {
        if (Objects.nonNull(block)) {
            int startTable = 1;
            int maxTableNo = TableUtils.getCurrentMaxTableNo(block.getBlockNum());
            if (isJob) {
                startTable = maxTableNo;
            }
            for (int i = startTable; i <= maxTableNo; i++) {
                TableEnum[] values = TableEnum.values();
                List<SysSyncLogPO> initLogs = new ArrayList<>();
                for (TableEnum e : values) {
                    SysSyncLogPO logPO = SysSyncLogPO.builder()
                            .syncMode(SyncModeEnum.AUTO.getType()).tableName(e.getTableName()).tableNo(i)
                            .blockNum(MathUtils.NEGATIVE)
                            .type(SyncLogTypeEnum.SYNC_TO.getType()).build();
                    LambdaQueryWrapper<SysSyncLogPO> query = Wrappers.lambdaQuery();
                    query.eq(SysSyncLogPO::getTableName, e.getTableName())
                            .eq(SysSyncLogPO::getTableNo, i)
                            .eq(SysSyncLogPO::getType, SyncLogTypeEnum.SYNC_TO.getType());
                    List<SysSyncLogPO> list = this.list(query);
                    if (CollectionUtils.isEmpty(list)) {
                        initLogs.add(logPO);
                    }
                }
                if (!CollectionUtils.isEmpty(initLogs)) {
                    this.saveBatch(initLogs);
                }
            }
        }
    }

    @Override
    public void recordSyncToLog(SyncBlockEvent event, List<BlockVO> blocks) {
        SysSyncLogPO logPO = SysSyncLogPO.builder()
                .syncMode(event.getSyncMode()).tableName(event.getTableName()).tableNo(event.getTableNo())
                .typeDesc(ChainUtils.buildSyncToTypeDesc(blocks)).blockNum(blocks.get(blocks.size() - 1).getBlockNum())
                .type(SyncLogTypeEnum.SYNC_TO.getType()).build();
        try {
            redisUtils.set(RedisKeys.SYNC_TO_BLOCK + event.getTableNo(), GsonUtils.BeanToJson(logPO), RedisUtils.NOT_EXPIRE);
        } catch (Exception e) {
            log.error("recordSyncToLog redis出错：event：{}", event, e);
        }
        this.baseMapper.insert(logPO);
    }

    @Override
    public SysSyncLogPO getSyncToLog(SyncBlockEvent event) {
        SysSyncLogPO logPO = null;
        try {
            logPO = redisUtils.get(RedisKeys.SYNC_TO_BLOCK + event.getTableNo(), SysSyncLogPO.class);
        } catch (Exception e) {
            log.error("getSyncToLog redis出错：event：{}", event, e);
        }
        if (Objects.isNull(logPO)) {
            LambdaQueryWrapper<SysSyncLogPO> query = Wrappers.lambdaQuery();
            query.eq(SysSyncLogPO::getTableName, TableEnum.CE_BLOCK.getTableName())
                    .eq(SysSyncLogPO::getTableNo, event.getTableNo())
                    .eq(SysSyncLogPO::getType, SyncLogTypeEnum.SYNC_TO.getType())
                    .orderByDesc(SysSyncLogPO::getTimeCreated);
            List<SysSyncLogPO> sysSyncLogPOS = this.baseMapper.selectList(query);
            if (!CollectionUtils.isEmpty(sysSyncLogPOS)) {
                return sysSyncLogPOS.get(0);
            }
        }
        return logPO;
    }

    @Override
    public void removeLongAgoData() {
        List<Integer> ids = this.baseMapper.getNotRemoveId();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 3);
        Date time = calendar.getTime();
        LambdaQueryWrapper<SysSyncLogPO> remove = Wrappers.lambdaQuery();
        remove.eq(SysSyncLogPO::getType, SyncLogTypeEnum.SYNC_TO.getType())
                .le(SysSyncLogPO::getTimeCreated, time);
        if (!CollectionUtils.isEmpty(ids)) {
            remove.notIn(SysSyncLogPO::getId, ids);
        }
        this.remove(remove);
    }

}
