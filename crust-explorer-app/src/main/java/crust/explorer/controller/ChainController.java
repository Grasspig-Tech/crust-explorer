package crust.explorer.controller;

import crust.explorer.enums.SyncModeEnum;
import crust.explorer.enums.TableEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.runner.Publisher;
import crust.explorer.sao.ChainSaoImpl;
import crust.explorer.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/chains")
//@Api(tags = "数据同步（前端不用）")
public class ChainController {

    @Autowired
    ChainSaoImpl chainSao;

    @Autowired
    Publisher publisher;

    //    @ApiOperation("重试同步区块")
    @GetMapping("/retry/block/{tableNo}")
    public Result<String> retrySyncBlock(@PathVariable Integer tableNo, String dKey) {
        log.info("ChainController.retrySyncBlock tableNo:{}", tableNo);
        BlockVO block = chainSao.queryLastBlock();
        if (Objects.nonNull(block)) {
            int maxTableNo = TableUtils.getMaxTableNo(block.getBlockNum());
            if (1 <= tableNo && tableNo <= maxTableNo) {
                SyncBlockEvent event = SyncBlockEvent.builder().syncMode(SyncModeEnum.MANUAL.getType())
                        .tableName(TableEnum.CE_BLOCK.getTableName())
                        .dKey(dKey)
                        .tableNo(tableNo).maxTableNo(maxTableNo).networkBlockNum(block.getBlockNum()).build();
                publisher.publishSyncHistoryBlock(event);
                return Result.success();
            } else {
                return Result.failure(ValidateEnum.TABLE_ERROR);
            }
        }
        return Result.failure(ValidateEnum.QUERY_LAST_BLOCK_FAIL);
    }

}
