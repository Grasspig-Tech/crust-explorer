package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.enums.TableEnum;
import crust.explorer.mapper.RewardSlashMapper;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.RewardSlashPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.RewardSlashVO;
import crust.explorer.pojo.vo.TableVo;
import crust.explorer.service.RewardSlashService;
import crust.explorer.util.CacheUtils;
import crust.explorer.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class RewardSlashServiceImpl extends ServiceImpl<RewardSlashMapper, RewardSlashPO> implements RewardSlashService {

    @Autowired
    CacheUtils cacheUtils;

    @Override
    public PageVo<RewardSlashVO> listPage(Integer blockNum, BaseQo qo) {
        int tableNo = TableUtils.getTableNo(blockNum);
        qo = TableUtils.rectifyBaseQo(qo);
        PageVo<RewardSlashVO> page = PageVo.initialized(qo);
        PageVo<RewardSlashVO> pageVo = this.baseMapper.listEventsAutoPage(page, tableNo, blockNum);
        return pageVo;
    }

    @Override
    public RewardSlashVO getInfo(Integer tableNo, Integer blockNum, String eventIndex) {
        return this.baseMapper.getInfo(tableNo, blockNum, eventIndex);
    }

    @Override
    public PageVo<RewardSlashVO> listPageByValidateAddress(String address, BaseQo qo) {
        List<Integer> tableNos = TableUtils.getTableNos();
        List<TableVo> totalCount = this.getRewardSlashCountFromCache(tableNos, address);
        List<PageTableDto> pageTables = TableUtils.buildPageTables(totalCount, qo);
        if (CollectionUtils.isEmpty(pageTables)) {
            return PageVo.initialized(qo, totalCount.get(0).getCount());
        }
        long begin = System.currentTimeMillis();
        List<RewardSlashVO> rewardSlashes = baseMapper.listPageByValidateAddress(pageTables, address);
        log.info("查奖惩列表分页接口 耗时 list ：{}", (System.currentTimeMillis() - begin));
        return new PageVo<>(
                qo.getCurrent(),
                qo.getSize(),
                totalCount.get(0).getCount(),
                rewardSlashes
        );
    }

    @Override
    public List<TableVo> getRewardSlashCountFromCache(List<Integer> tableNos, String address) {
        List<TableVo> totalCount = cacheUtils.getChainCountFromCache(TableEnum.CE_REWARD_SLASH, address, tableNos);
        if (!CollectionUtils.isEmpty(totalCount)) {
            return totalCount;
        }
        return getAndPutRewardSlashCountToCache(tableNos, address);
    }

    @Override
    public List<TableVo> getAndPutRewardSlashCountToCache(List<Integer> tableNos, String address) {
        long begin = System.currentTimeMillis();
        List<TableVo> totalCount = this.baseMapper.getTotalCount(tableNos, address);
        log.info("查奖惩列表分页接口 耗时 count ：{}", (System.currentTimeMillis() - begin));
        cacheUtils.putChainCountToCache(TableEnum.CE_REWARD_SLASH, address, totalCount);
        return totalCount;
    }
}
