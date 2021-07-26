package crust.explorer.service.impl;

import crust.explorer.enums.SyncModeEnum;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.mapper.*;
import crust.explorer.pojo.po.NetworkOverviewPO;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.EraVO;
import crust.explorer.pojo.vo.SyncBlockVO;
import crust.explorer.sao.ChainSaoImpl;
import crust.explorer.service.NetworkOverviewService;
import crust.explorer.service.SysSyncLogService;
import crust.explorer.util.ChainUtils;
import crust.explorer.util.Constants;
import crust.explorer.util.RedisUtils;
import crust.explorer.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@Slf4j
public class ChainServiceImpl {

    @Autowired
    BlockMapper blockMapper;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    ExtrinsicMapper extrinsicMapper;
    @Autowired
    RewardSlashMapper rewardSlashMapper;
    @Autowired
    TransferMapper transferMapper;
    @Autowired
    EraStatMapper eraStatMapper;
    @Autowired
    BondedPledgeMapper bondedPledgeMapper;
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    NominatorMapper nominatorMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    NetworkOverviewMapper networkOverviewMapper;
    @Autowired
    NetworkOverviewService networkOverviewService;
    @Autowired
    SysSyncLogService sysSyncLogService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ChainSaoImpl chainSao;

    public void initTable(boolean isJob, BlockVO block) {
        log.info("ChainServiceImpl.initTable start isJob:{},blockNum:{}", isJob, block.getBlockNum());
        int maxTableNo = 0;
        if (Objects.nonNull(block)) {
            maxTableNo = TableUtils.getCurrentMaxTableNo(block.getBlockNum());
            ChainUtils.SYNC_DATA.put(Constants.MAX_TABLE, maxTableNo);
            int startTable = 1;
            if (isJob) {
                startTable = maxTableNo;
            }
            for (int i = startTable; i <= maxTableNo; i++) {
                blockMapper.createTable(i);
                eventMapper.createTable(i);
                extrinsicMapper.createTable(i);
                rewardSlashMapper.createTable(i);
                transferMapper.createTable(i);
            }
        }
        log.info("ChainServiceImpl.initTable end isJob:{},maxTableNo:{},blockNum:{}", isJob, maxTableNo, block.getBlockNum());
    }

    @Transactional(rollbackFor = Exception.class)
    public void writeChainData(SyncBlockEvent event, SyncBlockVO syncBlockVO) {
        if (Objects.isNull(syncBlockVO)) {
            return;
        }
        long begin = System.currentTimeMillis();
        if (SyncModeEnum.JOB.getType().equals(event.getSyncMode())) {
            blockMapper.syncUpdateBlock(event.getTableNo(), syncBlockVO.getBlocks());
        } else {
            if (!CollectionUtils.isEmpty(syncBlockVO.getBlocks())) {
                blockMapper.syncBlock(event.getTableNo(), syncBlockVO.getBlocks());
            }
            if (!CollectionUtils.isEmpty(syncBlockVO.getEvents())) {
                eventMapper.syncEvent(event.getTableNo(), syncBlockVO.getEvents());
            }
            if (!CollectionUtils.isEmpty(syncBlockVO.getExtrinsics())) {
                extrinsicMapper.syncExtrinsic(event.getTableNo(), syncBlockVO.getExtrinsics());
            }
            if (!CollectionUtils.isEmpty(syncBlockVO.getTransfers())) {
                transferMapper.syncTransfer(event.getTableNo(), syncBlockVO.getTransfers());
            }
            if (!CollectionUtils.isEmpty(syncBlockVO.getRewardSlashes())) {
                rewardSlashMapper.syncRewardSlash(event.getTableNo(), syncBlockVO.getRewardSlashes());
            }
        }
        if (!CollectionUtils.isEmpty(syncBlockVO.getBlocks())) {
            sysSyncLogService.recordSyncToLog(event, syncBlockVO.getBlocks());
        }
        long end = System.currentTimeMillis();
        log.info("ChainServiceImpl.writeChainData 耗时:{}", (end - begin));
    }

    @Transactional(rollbackFor = Exception.class)
    public void refreshChainData(int tableNo, SyncBlockVO syncBlockVO) {
        long begin = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(syncBlockVO.getBlocks())) {
            blockMapper.syncUpdateBlock(tableNo, syncBlockVO.getBlocks());
        }
        if (!CollectionUtils.isEmpty(syncBlockVO.getEvents())) {
            eventMapper.syncUpdateEvent(tableNo, syncBlockVO.getEvents());
        }
        if (!CollectionUtils.isEmpty(syncBlockVO.getExtrinsics())) {
            extrinsicMapper.syncUpdateExtrinsic(tableNo, syncBlockVO.getExtrinsics());
        }
        if (!CollectionUtils.isEmpty(syncBlockVO.getTransfers())) {
            transferMapper.syncUpdateTransfer(tableNo, syncBlockVO.getTransfers());
        }
        if (!CollectionUtils.isEmpty(syncBlockVO.getRewardSlashes())) {
            rewardSlashMapper.syncUpdateRewardSlash(tableNo, syncBlockVO.getRewardSlashes());
        }
        long end = System.currentTimeMillis();
        log.info("ChainServiceImpl.refreshChainData 耗时:{}", (end - begin));
    }


    public void writeEraData(EraVO eraVO) {
        if (Objects.nonNull(eraVO)) {
            if (Objects.nonNull(eraVO.getEraStat())) {
                eraVO.getEraStat().setStatus(0);
                this.eraStatMapper.syncUpdateEra(eraVO.getEraStat());
            }
            if (!CollectionUtils.isEmpty(eraVO.getMembers())) {
                memberMapper.syncUpdateMembers(eraVO.getMembers());
            }
            if (!CollectionUtils.isEmpty(eraVO.getNominators())) {
                nominatorMapper.syncUpdateNominators(eraVO.getNominators());
            }
            if (!CollectionUtils.isEmpty(eraVO.getBondedPledges())) {
                bondedPledgeMapper.syncUpdateBondedPledges(eraVO.getBondedPledges());
            }
            if (!CollectionUtils.isEmpty(eraVO.getAccounts())) {
                accountMapper.syncUpdateAccounts(eraVO.getAccounts());
            }
        }
    }

    public void countNetworkOverview() {
        NetworkOverviewPO only = networkOverviewService.getOnly();
        if (Objects.isNull(only)) {
            return;
        }
        BigDecimal pledgeMinimum = bondedPledgeMapper.getPledgeMinimum();
        BigDecimal pledgeAvg = bondedPledgeMapper.getPledgeAvg();
        BigDecimal pledgeTotalActive = bondedPledgeMapper.getPledgeTotalActive();
        BigDecimal pledgeAbleNum = bondedPledgeMapper.getPledgeAbleNum();
        int numberGuarantee = nominatorMapper.getNumberGuarantee();
        int numberValidator = memberMapper.getNumberValidator();
        only.setPledgeMinimum(pledgeMinimum.stripTrailingZeros().toPlainString());
        only.setPledgeAvg(pledgeAvg.stripTrailingZeros().toPlainString());
        only.setPledgeTotalActive(pledgeTotalActive.stripTrailingZeros().toPlainString());
        only.setPledgeAbleNum(pledgeAbleNum.stripTrailingZeros().toPlainString());
        only.setNumberGuarantee(numberGuarantee);
        only.setNumberValidator(numberValidator);
        try {
            NetworkOverviewPO overview = chainSao.queryNetworkOverview();
            ChainUtils.fillLastOverview(overview, only);
        } catch (Exception e) {
        }
        networkOverviewService.putOnly(only);
    }

}
