package crust.explorer.util;


import crust.explorer.enums.FinalizedEnum;
import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.exception.BusinessException;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.BlockPO;
import crust.explorer.pojo.po.SysSyncLogPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.TableVo;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TableUtils {

    public static int getCurrentMaxTableNo(int networkBlockNum) {
        return getMaxTableNo(networkBlockNum) + Constants.RESERVED_TABLE;
    }

    public static int getMaxTableNo(int networkBlockNum) {
//        return (networkBlockNum % Constants.TABLE_BLOCK_MAX) == 0 ?
//                (networkBlockNum / Constants.TABLE_BLOCK_MAX) :
//                (networkBlockNum / Constants.TABLE_BLOCK_MAX + MathUtils.ONE);
        return (networkBlockNum / Constants.TABLE_BLOCK_MAX + MathUtils.ONE);
    }

    public static List<Integer> getTableNos() {
        Object o = ChainUtils.SYNC_DATA.get(Constants.MAX_TABLE);
        if (Objects.isNull(o)) {
            throw new BusinessException(ValidateEnum.MAX_TABLE_IS_NOT_EXIST);
        }
        int maxTableNo = (int) o;
        List<Integer> tableNos = new ArrayList<>();
        for (int i = 1; i <= maxTableNo; i++) {
            tableNos.add(i);
        }
        return tableNos;
    }

    public static List<Integer> getFirstThreeTableNos() {
        Object o = ChainUtils.SYNC_DATA.get(Constants.MAX_TABLE);
        if (Objects.isNull(o)) {
            throw new BusinessException(ValidateEnum.MAX_TABLE_IS_NOT_EXIST);
        }
        int maxTableNo = (int) o;
        List<Integer> tableNos = new ArrayList<>();
        int j = 0;
        for (int i = maxTableNo; i > 0; i--) {
            if (j < 3) {
                tableNos.add(i);
                j++;
            }
        }
        return tableNos;
    }

    public static int getTableNo(int blockNum) {
        Object o = ChainUtils.SYNC_DATA.get(Constants.MAX_TABLE);
        int tableNo = blockNum / Constants.TABLE_BLOCK_MAX + MathUtils.ONE;
        if (Objects.nonNull(o)) {
            int maxNo = (int) o;
            if (tableNo > maxNo) {
                throw new BusinessException(ValidateEnum.TABLE_IS_NOT_EXIST);
            }
        }
        return tableNo;
    }

    public static int getThisTableMaxBlockNum(int tableNo) {
        return Constants.TABLE_BLOCK_MAX * tableNo - MathUtils.ONE;
    }

    public static int getThisTableMinBlockNum(int tableNo) {
        return getThisTableMaxBlockNum(tableNo) - Constants.TABLE_BLOCK_MAX + MathUtils.ONE;
    }

    public static int getStartSyncBlockNum(SyncBlockEvent event, SysSyncLogPO syncTo) {
        int thisTableMinBlockNum = getThisTableMinBlockNum(event.getTableNo());
        int thisTableMaxBlockNum = getThisTableMaxBlockNum(event.getTableNo());
        if (MathUtils.NEGATIVE.equals(syncTo.getBlockNum())) {
            if (thisTableMinBlockNum > event.getNetworkBlockNum()) {
                throw new BusinessException(LogicEnum.NOT_SYNC_RESERVED_TABLE);
            }
            if (event.getNetworkBlockNum() > thisTableMaxBlockNum) {
                return thisTableMaxBlockNum;
            }
            return event.getNetworkBlockNum();
        }
        if (syncTo.getBlockNum() - MathUtils.ONE < thisTableMinBlockNum) {
            throw new BusinessException(LogicEnum.THIS_TABLE_SYNC_DONE);
        }
        return syncTo.getBlockNum() - MathUtils.ONE;
    }

    public static int getThisTableFirstPage(SyncBlockEvent event, SysSyncLogPO syncTo, int pageSize) {
        int thisTableMaxBlockNum = getThisTableMaxBlockNum(event.getTableNo());
        int thisTableMinBlockNum = getThisTableMinBlockNum(event.getTableNo());
        if ((event.getNetworkBlockNum() - thisTableMinBlockNum) < MathUtils.ZERO) {
            return MathUtils.NEGATIVE;
        }
        int height = event.getNetworkBlockNum() - thisTableMaxBlockNum;
        if (!MathUtils.NEGATIVE.equals(syncTo.getBlockNum())) {
            height = event.getNetworkBlockNum() - syncTo.getBlockNum();
            if (height < MathUtils.ZERO) {
                throw new BusinessException(LogicEnum.LAST_SYNC_LOG_ERROR);
            }
        }
        int page = height / pageSize;
        return page > MathUtils.ZERO ? (page - MathUtils.ONE) : MathUtils.ZERO;
    }

//    public static int thisPageInThisTable(int tableNo, int page) {
//        return Constants.TABLE_BLOCK_MAX * tableNo - MathUtils.ONE;
//    }

//    public static int getLastBlock(int tableNo, int syncTo) {
//        int lastBlock = getThisTableMaxBlockNum(tableNo);
//        if (!MathUtils.NEGATIVE.equals(syncTo)) {
//            lastBlock = syncTo;
//        }
//        return lastBlock;
//    }

    public static Map<Integer, List<BlockPO>> groupBlockFinalized(List<BlockPO> blockVOS) {
        if (CollectionUtils.isEmpty(blockVOS)) {
            return new HashMap<>();
        }
        return blockVOS.stream().collect(Collectors.groupingBy(BlockPO::getFinalized));
    }

    public static Map<Integer, List<BlockVO>> groupBlock(List<BlockVO> blockVOS) {
        if (CollectionUtils.isEmpty(blockVOS)) {
            return new HashMap<>();
        }
        return blockVOS.stream()
                .collect(Collectors.groupingBy(block -> TableUtils.getTableNo(block.getBlockNum())));
    }

    public static List<BlockVO> filterBlock(int tableNo, List<BlockVO> blockVOS) {
        Map<Integer, List<BlockVO>> collect = groupBlock(blockVOS);
        if (CollectionUtils.isEmpty(collect)) {
            return new ArrayList<>();
        }
        return collect.get(tableNo);
    }

    public static List<BlockVO> filterRepeatBlock(List<BlockPO> blockPOS, List<BlockVO> blockVOS) {
        if (CollectionUtils.isEmpty(blockVOS)) {
            return new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(blockPOS)) {
            return blockVOS;
        }
        Map<Integer, List<BlockPO>> groupBlockFinalized = TableUtils.groupBlockFinalized(blockPOS);
        List<BlockPO> finalized = groupBlockFinalized.get(FinalizedEnum.FINALIZED.getCode());
        if (CollectionUtils.isEmpty(finalized)) {
            finalized = new ArrayList<>();
        }
        List<BlockPO> unFinalized = groupBlockFinalized.get(FinalizedEnum.UN_FINALIZED.getCode());
        if (CollectionUtils.isEmpty(unFinalized)) {
            unFinalized = new ArrayList<>();
        }
        List<Integer> finalizedBlockNums = finalized.stream().map(BlockPO::getBlockNum).collect(Collectors.toList());
        List<Integer> unFinalizedBlockNums = unFinalized.stream().map(BlockPO::getBlockNum).collect(Collectors.toList());
        return blockVOS.stream().filter(block -> {
            if (finalizedBlockNums.contains(block.getBlockNum())) {
                return false;
            }
            if (unFinalizedBlockNums.contains(block.getBlockNum()) && FinalizedEnum.UN_FINALIZED.getCode().equals(block.getFinalized())) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

//    public static boolean isRetry(SysSyncLogPO syncTo, List<BlockVO> blockVOS) {
//        return (!MathUtils.NEGATIVE.equals(syncTo.getBlockNum()));
//    }
//
//    public static boolean isNotReallyFirstPage(SysSyncLogPO syncTo, List<BlockVO> blockVOS) {
//        if (Objects.isNull(syncTo) || CollectionUtils.isEmpty(blockVOS)) {
//            return false;
//        }
//        if (MathUtils.NEGATIVE.equals(syncTo.getBlockNum())) {
//            return false;
//        }
//        int target = syncTo.getBlockNum();
//        int begin = blockVOS.get(0).getBlockNum();
//        int end = blockVOS.get(blockVOS.size() - 1).getBlockNum();
//        return (target >= begin && target <= end);
//    }

    public static void main1(String[] args) {
        List<BlockVO> blockVOS = filterRepeatBlock(null, null);
        boolean syncDone = false;
        int lastBlock = 0;
        int startBlock = 45;
        System.out.println("-----------------------------------------------------------");
        while (!syncDone) {
            int offset = TableUtils.getOffset(1, startBlock, MathUtils.TEN);
            System.out.println(offset);
            List<Integer> l = new ArrayList<>();
            for (int i = 0; i < offset; i++) {
                l.add(startBlock - i);
            }
            System.out.println(l);
            lastBlock = l.get(l.size() - 1);
            // 假设 thisTableMinBlockNum = 10，便于测试
            syncDone = TableUtils.judgeSyncDone(1, lastBlock);
            startBlock -= offset;
        }
        System.out.println("-----------------------------------------------------------");
    }

    public static int getOffset(int tableNo, int startBlock, int offset) {
        int endBlock = getThisTableMinBlockNum(tableNo);
        if (startBlock - (offset - 1) >= endBlock) {
            return offset;
        }
        int temp = startBlock - endBlock;
        return temp >= 0 ? temp + 1 : 0;
    }

    public static boolean judgeSyncDone(int tableNo, int lastBlock) {
        if (MathUtils.NEGATIVE.equals(lastBlock)) {
            return false;
        }
        int thisTableMinBlockNum = getThisTableMinBlockNum(tableNo);
        return lastBlock <= thisTableMinBlockNum;
    }

    public static boolean judgeSyncDone(int tableNo, List<BlockVO> blockVOS) {
        if (CollectionUtils.isEmpty(blockVOS)) {
            return true;
        }
        if (tableNo == 1) {
            if (blockVOS.get(blockVOS.size() - 1).getBlockNum() <= 1) {
                return true;
            }
        } else {
            Integer blockNum = blockVOS.get(0).getBlockNum();
            if (getTableNo(blockNum) == tableNo - 1) {
                return true;
            }
        }
        return false;
    }

    public static int getTotalPage(int totalCount, BaseQo qo) {
        return (totalCount % qo.getSize()) == 0 ?
                (totalCount / qo.getSize()) :
                (totalCount / qo.getSize() + 1);
    }

    public static BaseQo rectifyBaseQo(BaseQo qo) {
        if (Objects.isNull(qo)) {
            return new BaseQo();
        }
        if (qo.getCurrent() <= 0) {
            qo.setCurrent(Constants.DEFAULT_PAGE);
        }
        if (qo.getSize() <= 0) {
            qo.setSize(Constants.DEFAULT_PAGE_SIZE);
        }
        if (qo.getSize() > Constants.MAX_PAGE_SIZE) {
            qo.setSize(Constants.MAX_PAGE_SIZE);
        }
        return qo;
    }

    public static BaseQo rectifyBaseQo(BaseQo qo, int totalPage) {
        if (qo.getCurrent() >= totalPage) {
            qo.setCurrent(totalPage);
        }
        return qo;
    }

    public static void fillTotalCount(List<TableVo> totalCount) {
        if (!CollectionUtils.isEmpty(totalCount)) {
            int total = totalCount.get(0).getCount();
            for (int i = 1; i < totalCount.size(); i++) {
                total += totalCount.get(i).getCount();
            }
            totalCount.get(0).setCount(total);
        }
    }

    public static List<PageTableDto> buildPageTables(List<TableVo> totalCount, BaseQo qo) {
        qo = TableUtils.rectifyBaseQo(qo);
        fillTotalCount(totalCount);
        List<PageTableDto> pageTables = new ArrayList<>();
        if (totalCount.get(0).getCount() <= 0) {
            return pageTables;
        }
        int start = (qo.getCurrent() - 1) * qo.getSize();
        int temp = 0;
        int leftQuery = qo.getSize();
        int beginIndex;

        for (int i = totalCount.size() - 1; i > 0; i--) {
            if (leftQuery <= 0) {
                break;
            }
            int currentCount = totalCount.get(i).getCount();
            temp += currentCount;
            if (currentCount <= 0 || temp < start) {
                continue;
            }
            int reference = temp - start;
            beginIndex = currentCount - reference;
            int currentQueryCount = currentCount - beginIndex;
            currentQueryCount = (currentQueryCount >= leftQuery) ? leftQuery : currentQueryCount;
            if (currentQueryCount > 0) {
                PageTableDto dto = PageTableDto.builder()
                        .tableNo(i).begin(beginIndex).pageSize(currentQueryCount).build();
                pageTables.add(dto);
            }
            start += currentQueryCount;
            leftQuery -= currentQueryCount;
        }
        return pageTables;
    }

    public static void main(String[] args) {
//        /*
//        0000000-0999999
//        1000000-1999999
//        2000000-2999999
        //      2259797
//         */
//        System.out.println(0 / Constants.TABLE_BLOCK_MAX + 1);
//        System.out.println(999999 / Constants.TABLE_BLOCK_MAX + 1);
//        System.out.println(1000000 / Constants.TABLE_BLOCK_MAX + 1);
//        System.out.println(1999999 / Constants.TABLE_BLOCK_MAX + 1);
//        System.out.println(2000000 / Constants.TABLE_BLOCK_MAX + 1);
//        System.out.println(2999999 / Constants.TABLE_BLOCK_MAX + 1);
//        int maxTableNo = getMaxTableNo(2259797);
//        System.out.println(maxTableNo);

//        for (int i = 1; i <= 0; i++) {
//            System.out.println(i);
//        }

        System.out.println(getTableNo(0));
        System.out.println(getTableNo(1));
        System.out.println(getTableNo(999999));
        System.out.println(getMaxTableNo(0));
        System.out.println(getMaxTableNo(1));
        System.out.println(getMaxTableNo(999999));
        System.out.println("-----------------------------------");
        System.out.println(getTableNo(1000000));
        System.out.println(getTableNo(1111111));
        System.out.println(getTableNo(1999999));
        System.out.println(getMaxTableNo(1000000));
        System.out.println(getMaxTableNo(1111111));
        System.out.println(getMaxTableNo(1999999));
        System.out.println("-----------------------------------");
        System.out.println(getTableNo(2000000));
        System.out.println(getTableNo(2111111));
        System.out.println(getTableNo(2999999));
        System.out.println(getMaxTableNo(2000000));
        System.out.println(getMaxTableNo(2111111));
        System.out.println(getMaxTableNo(2999999));
    }
}
