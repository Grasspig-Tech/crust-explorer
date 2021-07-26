package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.enums.TableEnum;
import crust.explorer.mapper.TransferMapper;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.TransferPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TableVo;
import crust.explorer.pojo.vo.TransferVO;
import crust.explorer.service.TransferService;
import crust.explorer.util.CacheUtils;
import crust.explorer.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class TransferServiceImpl extends ServiceImpl<TransferMapper, TransferPO> implements TransferService {

    @Autowired
    CacheUtils cacheUtils;

    @Override
    public PageVo<TransferVO> listPage(List<Integer> tableNos, String address, BaseQo qo) {
        List<TableVo> totalCount = this.getTransferCountFromCache(tableNos, address);
        List<PageTableDto> pageTables = TableUtils.buildPageTables(totalCount, qo);
        if (CollectionUtils.isEmpty(pageTables)) {
            return PageVo.initialized(qo, totalCount.get(0).getCount());
        }
        long begin = System.currentTimeMillis();
        List<TransferVO> transfers = baseMapper.listTransfersPage(pageTables, address);
        log.info("转账列表分页接口 耗时 list ：{}", (System.currentTimeMillis() - begin));
        return new PageVo<>(
                qo.getCurrent(),
                qo.getSize(),
                totalCount.get(0).getCount(),
                transfers
        );
    }

    @Override
    public PageVo<TransferVO> listPage(Integer blockNum, BaseQo qo) {
        int tableNo = TableUtils.getTableNo(blockNum);
        qo = TableUtils.rectifyBaseQo(qo);
        PageVo<TransferVO> page = PageVo.initialized(qo);
        PageVo<TransferVO> pageVo = this.baseMapper.listTransfersAutoPage(page, tableNo, blockNum);
        return pageVo;
    }

    @Override
    public TransferVO getInfo(Integer tableNo, Integer blockNum, String extrinsicIndex) {
        return this.baseMapper.getInfo(tableNo, blockNum, extrinsicIndex);
    }

    @Override
    public List<TableVo> getTransferCountFromCache(List<Integer> tableNos, String address) {
        List<TableVo> totalCount = cacheUtils.getChainCountFromCache(TableEnum.CE_TRANSFER, address, tableNos);
        if (!CollectionUtils.isEmpty(totalCount)) {
            return totalCount;
        }
        return getAndPutTransferCountToCache(tableNos, address);
    }

    @Override
    public List<TableVo> getAndPutTransferCountToCache(List<Integer> tableNos, String address) {
        long begin = System.currentTimeMillis();
        List<TableVo> totalCount = this.baseMapper.getTotalCount(tableNos, address);
        log.info("转账列表分页接口 耗时 count ：{}", (System.currentTimeMillis() - begin));
        cacheUtils.putChainCountToCache(TableEnum.CE_TRANSFER, address, totalCount);
        return totalCount;
    }


}
