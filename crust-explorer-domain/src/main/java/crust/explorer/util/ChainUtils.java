package crust.explorer.util;


import crust.explorer.enums.SyncLogTypeEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.exception.BusinessException;
import crust.explorer.pojo.po.NetworkOverviewPO;
import crust.explorer.pojo.po.SysSyncLogPO;
import crust.explorer.pojo.vo.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ChainUtils {

    public static ConcurrentHashMap<String, Object> SYNC_DATA = new ConcurrentHashMap<>();

    public static <T> T getObj(String key) {
        if (Objects.nonNull(SYNC_DATA.get(key))) {
            return (T) SYNC_DATA.get(key);
        }
        return null;
    }

    public static void putObj(String key, Object obj) {
        SYNC_DATA.put(key, obj);
    }

    public static boolean syncPassTenSecond(String chainType) {
        long now = System.currentTimeMillis();
        long begin = now;
        Object beginTime = SYNC_DATA.get(chainType);
        if (Objects.nonNull(beginTime)) {
            begin = (long) beginTime;
        }
        return (now - begin >= Constants.SYNC_PASS_TIME);
    }

    public static SyncBlockVO buildSyncBlockVO(List<BlockVO> blockVOS) {
        SyncBlockVO syncBlockVO = new SyncBlockVO();
        List<BlockVO> blocks = new ArrayList<>();
        List<EventVO> events = new ArrayList<>();
        List<ExtrinsicVO> extrinsics = new ArrayList<>();
        List<TransferVO> transfers = new ArrayList<>();
        List<RewardSlashVO> rewardSlashes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blockVOS)) {
            blockVOS.forEach(blockVO -> {
                blocks.add(blockVO);
                if (!CollectionUtils.isEmpty(blockVO.getEvents())) {
                    events.addAll(blockVO.getEvents());
                }
                if (!CollectionUtils.isEmpty(blockVO.getExtrinsics())) {
                    extrinsics.addAll(blockVO.getExtrinsics());
                }
                if (!CollectionUtils.isEmpty(blockVO.getTransfers())) {
                    transfers.addAll(blockVO.getTransfers());
                }
                if (!CollectionUtils.isEmpty(blockVO.getRewardSlashes())) {
                    rewardSlashes.addAll(blockVO.getRewardSlashes());
                }
            });
        }
        syncBlockVO.setBlocks(blocks);
        syncBlockVO.setEvents(events);
        syncBlockVO.setExtrinsics(extrinsics);
        syncBlockVO.setTransfers(transfers);
        syncBlockVO.setRewardSlashes(rewardSlashes);
        return syncBlockVO;
    }

    public static SysSyncLogPO buildSysSyncLogPO(Exception e, SyncBlockEvent event, SyncLogTypeEnum syncLogTypeEnum) {
        SysSyncLogPO logPO = SysSyncLogPO.builder()
                .syncMode(event.getSyncMode()).tableName(event.getTableName()).tableNo(event.getTableNo())
                .type(syncLogTypeEnum.getType()).typeDesc(syncLogTypeEnum.getTypeDesc()).build();
        if (Objects.nonNull(e)) {
            logPO.setErrorInfo(getRealMessage(e, syncLogTypeEnum));
        }
        return logPO;
    }

    public static String buildSyncToTypeDesc(List<BlockVO> blocks) {
        StringBuilder sb = new StringBuilder(SyncLogTypeEnum.SYNC_TO.getTypeDesc());
        sb.append("区块高度（");
        sb.append(blocks.get(blocks.size() - 1).getBlockNum());
        sb.append(")");
        return sb.toString();
    }

    public static String getSyncHistoryBlockUrl(String domain, Integer start, Integer row) {
        String api = Constants.GET_HISTORY_BLOCK_URL3;
        Integer tableNo = TableUtils.getTableNo(start);
        if (tableNo == 2) {
            api = Constants.GET_HISTORY_BLOCK_URL2;
        } else if (tableNo == 1) {
            api = Constants.GET_HISTORY_BLOCK_URL1;
        }
        StringBuilder sb = new StringBuilder(domain);
        sb.append(api);
        sb.append("?start=");
        sb.append(start);
        sb.append("&row=");
        sb.append(row);
        return sb.toString();
    }

    public static String getSyncLastBlockUrl(String domain, Integer row) {
        StringBuilder sb = new StringBuilder(domain);
        sb.append(Constants.GET_LAST_BLOCK_URL);
        if (Objects.nonNull(row) && row > 1) {
            sb.append("?row=");
            sb.append(row);
        }
        return sb.toString();
    }

    public static String getSyncCurrentEraUrl(String domain) {
        StringBuilder sb = new StringBuilder(domain);
        sb.append(Constants.GET_CURRENT_ERA_URL);
        return sb.toString();
    }

    public static String getQueryNetworkOverviewUrl(String domain) {
        StringBuilder sb = new StringBuilder(domain);
        sb.append(Constants.GET_NETWORK_OVERVIEW_URL);
        return sb.toString();
    }

    public static Integer getBlockNum(String index) {
        if (StringUtils.isBlank(index)) {
            return null;
        }
        String[] split = index.split("-");
        if (split.length == 2) {
            try {
                return Integer.valueOf(split[0]);
            } catch (Exception e) {
                throw new BusinessException(ValidateEnum.INDEX_IS_ERROR);
            }
        }
        throw new BusinessException(ValidateEnum.INDEX_IS_ERROR);
    }

    public static boolean isHash(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        if (s.contains("-")) {
            String[] split = s.split("-");
            if (split.length == 2) {
                for (int i = 0; i < split.length; i++) {
                    try {
                        Integer.parseInt(split[i]);
                        return false;
                    } catch (Exception e) {
                        return true;
                    }
                }
                return true;
            }
            return false;
        } else {
            try {
                Integer.parseInt(s);
                return false;
            } catch (Exception e) {
                return true;
            }
        }
    }

    public static boolean isBlock(String s) {
        if (isHash(s)) {
            return false;
        }
        String[] split = s.split("-");
        return (split.length == 1);
    }

    public static String getRealMessage(Throwable e, SyncLogTypeEnum syncLogTypeEnum) {
        while (e != null) {
            Throwable cause = e.getCause();
            if (cause == null || StringUtils.isBlank(cause.getMessage())) {
                if (Objects.nonNull(e) && StringUtils.isNotBlank(e.getMessage())) {
                    if (e.getMessage().length() >= Constants.SYNC_ERROR_LOG_LENGTH) {
                        return e.getMessage().substring(MathUtils.ZERO, Constants.SYNC_ERROR_LOG_LENGTH);
                    }
                    return e.getMessage();
                }
                return syncLogTypeEnum.getTypeDesc();
            }
            e = cause;
        }
        return syncLogTypeEnum.getTypeDesc();
    }

    public static void fillLastOverview(NetworkOverviewPO source, NetworkOverviewPO target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        if (StringUtils.isNotBlank(source.getTotalStorage())) {
            if (source.getTotalStorage().contains(".")) {
                target.setTotalStorage(source.getTotalStorage().substring(0, source.getTotalStorage().indexOf(".")));
            } else {
                target.setTotalStorage(source.getTotalStorage());
            }
        }
        if (StringUtils.isNotBlank(source.getPledgePer())) {
            target.setPledgePer(source.getPledgePer());
        }
        if (StringUtils.isNotBlank(source.getTotalCirculation())) {
            if (source.getTotalCirculation().contains(".")) {
                target.setTotalCirculation(source.getTotalCirculation().substring(0, source.getTotalCirculation().indexOf(".")));
            } else {
                target.setTotalCirculation(source.getTotalCirculation());
            }
        }
        if (StringUtils.isNotBlank(source.getBlockHeight())) {
            target.setBlockHeight(source.getBlockHeight());
        }
        if (StringUtils.isNotBlank(source.getBlockHeightConfirmed())) {
            target.setBlockHeightConfirmed(source.getBlockHeightConfirmed());
        }
        if (StringUtils.isNotBlank(source.getBlockLastTime())) {
            target.setBlockLastTime(source.getBlockLastTime());
        }
        if (Objects.nonNull(source.getEra())) {
            target.setEra(source.getEra());
        }
        if (Objects.nonNull(source.getEraStartTimestamp())) {
            target.setEraStartTimestamp(source.getEraStartTimestamp());
        }
    }

    public static void rectifyCountdownEra(NetworkOverviewPO target) {
        if (Objects.nonNull(target) && Objects.nonNull(target.getEraStartTimestamp())) {
            int eraStartTimestamp = target.getEraStartTimestamp();
            int eraEndTimestamp = (3600 * 6) + eraStartTimestamp;
            long time = new Date().getTime() / 1000L;
            long eraCountdown = eraEndTimestamp - time;
            if (eraCountdown <= 0) {
                eraCountdown = 0;
            }
            target.setCountdownEra(String.valueOf(eraCountdown));
            target.setCountdownSession(String.valueOf(eraCountdown % (100 * 6)));
        }
    }

    public static void main(String[] args) {

        int eraStartTime = 1626740196;
        int eraStartTimestamp = eraStartTime;
        int eraEndTimestamp = (3600 * 6) + eraStartTimestamp;
        System.out.println(eraEndTimestamp);
        long time1 = new Date().getTime();
        System.out.println(time1);
        long time = time1 / 1000L;
        System.out.println(time);
        long eraCountdown = eraEndTimestamp - time;
        System.out.println(eraCountdown);
    }

}
