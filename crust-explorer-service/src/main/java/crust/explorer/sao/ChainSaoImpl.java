package crust.explorer.sao;

import com.google.gson.reflect.TypeToken;
import crust.explorer.enums.hint.UnforeseenEnum;
import crust.explorer.exception.BusinessException;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.po.NetworkOverviewPO;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.EraVO;
import crust.explorer.service.SysConfigService;
import crust.explorer.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
@Slf4j
public class ChainSaoImpl {

    @Autowired
    SysConfigService sysConfigService;

//    http://192.168.3.108:9527/api/block/list?start=2328959&row=3

    public List<BlockVO> queryBlockLimit(Integer start, Integer row, String dKey) {
        try {
            String domain = StringUtils.EMPTY;
            if (StringUtils.isNotBlank(dKey)) {
                domain = sysConfigService.getChainDomain(dKey);
            }
            if (StringUtils.isBlank(domain)) {
                domain = sysConfigService.getChainDomain(RedisKeys.CHAIN_SYNC_BLOCK_DOMAIN);
            }
            String url = ChainUtils.getSyncHistoryBlockUrl(domain, start, row);
            log.info("ChainSaoImpl.queryBlockLimit,url:{},start-row:{}-{}", url, start, row);
            long begin = System.currentTimeMillis();
            String result = HttpClientHandler.get(url, null);
            long end = System.currentTimeMillis();

            log.info("ChainSaoImpl.queryBlockLimit,result:{},start-row:{}-{},耗时:{},", "", start, row, (end - begin));
            if (ResultUtils.hasBody(result)) {
                Result<List<BlockVO>> listResult = GsonUtils.getInstance().fromJson(result, new TypeToken<Result<List<BlockVO>>>() {
                }.getType());
                if (ResultUtils.bodyIsEmpty(listResult)) {
                    throw new BusinessException(UnforeseenEnum.SYNC_DATA_BODY_PARSE_IS_NULL);//todo 放开
                }
                if (ResultUtils.isOk(listResult)) {
                    return listResult.getData();
                } else {
                    throw new BusinessException(listResult.getCode(), listResult.getMsg());//todo 放开
                }
            } else {
                throw new BusinessException(UnforeseenEnum.SYNC_DATA_BODY_IS_NULL);//todo 放开
            }
        } catch (Exception e) {
            log.error("ChainSaoImpl.queryBlockLimit 失败", e);
            throw new BusinessException(UnforeseenEnum.SYNC_DATA_ERROR);// todo 放开
        }
//        return null;// todo 注释掉
    }

    public List<BlockVO> queryLastBlockPage(Integer row) {
        try {
            String domain = sysConfigService.getChainDomain(RedisKeys.CHAIN_REFRESH_BLOCK_DOMAIN);
            String url = ChainUtils.getSyncLastBlockUrl(domain, row);
            long begin = System.currentTimeMillis();
            String result = HttpClientHandler.get(url, null);
            long end = System.currentTimeMillis();
            log.info("ChainSaoImpl.queryLastBlockPage ,耗时:{},result:{}", (end - begin), "");
            if (ResultUtils.hasBody(result)) {
                Result<List<BlockVO>> blockVO = GsonUtils.getInstance().fromJson(result, new TypeToken<Result<List<BlockVO>>>() {
                }.getType());
                if (ResultUtils.isOk(blockVO)) {
                    return blockVO.getData();
                } else if (ResultUtils.isFail(blockVO)) {
                    throw new BusinessException(blockVO.getCode(), blockVO.getMsg());//todo 放开
                }
                throw new BusinessException(UnforeseenEnum.SYNC_DATA_BODY_PARSE_IS_NULL);//todo 放开
            } else {
                throw new BusinessException(UnforeseenEnum.SYNC_DATA_BODY_IS_NULL);//todo 放开
            }
        } catch (Exception e) {
            log.error("ChainSaoImpl.queryLastBlockPage 失败：", e);
            throw new BusinessException(UnforeseenEnum.SYNC_DATA_ERROR);// todo 放开
        }
    }

    public BlockVO queryLastBlock() {
        List<BlockVO> blockVOS = queryLastBlockPage(null);
        if (!CollectionUtils.isEmpty(blockVOS)) {
            return blockVOS.get(0);
        }
        return null;
    }


    public static void main(String[] args) throws IOException, URISyntaxException {
//        String url = "http://192.168.3.108:9527/api/block/list1?start=2397014&row=1";
        String url = "http://192.168.3.108:9527/api/network_overview";
        String result = HttpClientHandler.get(url, null);
        if (StringUtils.isNotBlank(result)) {
//            Result<List<BlockVO>> listResult = GsonUtils.getInstance().fromJson(result, new TypeToken<Result<List<BlockVO>>>() {
//            }.getType());
            Result<NetworkOverviewPO> resultObj = GsonUtils.getInstance().fromJson(result, new TypeToken<Result<NetworkOverviewPO>>() {
            }.getType());
            System.out.println(resultObj);
        }
    }

    public EraVO queryCurrentEra() {
        try {
            String domain = sysConfigService.getChainDomain(RedisKeys.CHAIN_ERA_DOMAIN);
            String url = ChainUtils.getSyncCurrentEraUrl(domain);
            long begin = System.currentTimeMillis();
            String result = HttpClientHandler.get(url, null);
            long end = System.currentTimeMillis();
            log.info("ChainSaoImpl.queryCurrentEra ,耗时:{},result:{}", (end - begin), "");
            if (ResultUtils.hasBody(result)) {
                Result<EraVO> eraVOResult = GsonUtils.getInstance().fromJson(result, new TypeToken<Result<EraVO>>() {
                }.getType());
                if (ResultUtils.isOk(eraVOResult)) {
                    return eraVOResult.getData();
                } else if (ResultUtils.isFail(eraVOResult)) {
                    throw new BusinessException(eraVOResult.getCode(), eraVOResult.getMsg());//todo 放开
                }
                throw new BusinessException(UnforeseenEnum.SYNC_DATA_BODY_PARSE_IS_NULL);//todo 放开
            } else {
                throw new BusinessException(UnforeseenEnum.SYNC_DATA_BODY_IS_NULL);//todo 放开
            }
        } catch (Exception e) {
            log.error("ChainSaoImpl.queryCurrentEra 失败：", e);
            throw new BusinessException(UnforeseenEnum.SYNC_DATA_ERROR);// todo 放开
        }
    }

    public NetworkOverviewPO queryNetworkOverview() {
        try {
            String domain = sysConfigService.getChainDomain(RedisKeys.CHAIN_ERA_DOMAIN);
            String url = ChainUtils.getQueryNetworkOverviewUrl(domain);
            long begin = System.currentTimeMillis();
            String result = HttpClientHandler.get(url, null);
            long end = System.currentTimeMillis();
            log.info("ChainSaoImpl.queryNetworkOverview ,耗时:{},result:{}", (end - begin), "");
            if (ResultUtils.hasBody(result)) {
                Result<NetworkOverviewPO> networkOverviewResult = GsonUtils.getInstance().fromJson(result, new TypeToken<Result<NetworkOverviewPO>>() {
                }.getType());
                if (ResultUtils.isOk(networkOverviewResult)) {
                    return networkOverviewResult.getData();
                } else if (ResultUtils.isFail(networkOverviewResult)) {
                    throw new BusinessException(networkOverviewResult.getCode(), networkOverviewResult.getMsg());//todo 放开
                }
                throw new BusinessException(UnforeseenEnum.SYNC_DATA_BODY_PARSE_IS_NULL);//todo 放开
            } else {
                throw new BusinessException(UnforeseenEnum.SYNC_DATA_BODY_IS_NULL);//todo 放开
            }
        } catch (Exception e) {
            log.error("ChainSaoImpl.queryNetworkOverview 失败：", e);
            throw new BusinessException(UnforeseenEnum.SYNC_DATA_ERROR);// todo 放开
        }
    }

}
