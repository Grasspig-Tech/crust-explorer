package crust.explorer.config;

import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.runner.Publisher;
import crust.explorer.sao.ChainSaoImpl;
import crust.explorer.service.BlockService;
import crust.explorer.service.SysSyncLogService;
import crust.explorer.service.impl.ChainServiceImpl;
import crust.explorer.util.ChainUtils;
import crust.explorer.util.Constants;
import crust.explorer.ws.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitAppConfig implements ApplicationRunner {

    @Autowired
    BlockService blockService;

    @Autowired
    ChainServiceImpl chainService;

    @Autowired
    ChainSaoImpl chainSao;

    @Autowired
    Publisher publisher;

    @Autowired
    SysSyncLogService sysSyncLogService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Collections.init();
//        ChainUtils.SYNC_DATA.put(Constants.MAX_TABLE, 4);
        BlockVO block = chainSao.queryLastBlock();
        chainService.initTable(Boolean.FALSE, block);
        sysSyncLogService.initSyncToLog(Boolean.FALSE, block);
        blockService.publishSyncHistoryBlock(block);
    }
}
