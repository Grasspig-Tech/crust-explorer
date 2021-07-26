package crust.explorer;

import com.google.gson.reflect.TypeToken;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.service.BlockService;
import crust.explorer.service.NetworkOverviewService;
import crust.explorer.service.SysSyncLogService;
import crust.explorer.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class CommonTests {
    @Autowired
    SysSyncLogService sysSyncLogService;
    @Autowired
    NetworkOverviewService networkOverviewService;
    @Autowired
    BlockService blockService;
    @Autowired
    RedisUtils redisUtils;

    @Test
    public void test() {
        sysSyncLogService.removeLongAgoData();
        networkOverviewService.removeLongAgoData();

    }

    @Test
    public void test_6e_12() throws IOException, URISyntaxException {
        String url = "http://192.168.3.108:9527/api/block/list1?start=2397014&row=1";
        String result = HttpClientHandler.get(url, null);
        if (StringUtils.isNotBlank(result)) {
            Result<List<BlockVO>> listResult = GsonUtils.getInstance().fromJson(result, new TypeToken<Result<List<BlockVO>>>() {
            }.getType());
            System.out.println(listResult);
            BigDecimal amount = listResult.getData().get(0).getRewardSlashes().get(0).getAmount();
            System.out.println(amount);
            blockService.refreshBlock(listResult.getData());
        }
    }

    @Test
    public void test_era() throws InterruptedException {

        redisUtils.set("xxxxxxxxxxxxxx", "yyyyyyyyyyyyyyyyy", RedisKeys.CACHE_5S);
        Thread.sleep(1 * 1000);
        long life = redisUtils.getLife("xxxxxxxxxxxxxx");
        System.out.println(life);
        Thread.sleep(1 * 1000);
        life = redisUtils.getLife("xxxxxxxxxxxxxx");
        System.out.println(life);
        life = redisUtils.getLife("xxxxxxxxxxxxxx11111");
        System.out.println(life);
    }
    @Test
    public void test_24Block() {

        Date now = new Date();
        long end = now.getTime() / 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 24);
        long begin = calendar.getTime().getTime() / 1000;
        List<Integer> blockTables = TableUtils.getFirstThreeTableNos();
        int numBlock = blockService.getCountBlock(blockTables, begin, end);
        System.out.println(numBlock);
    }
}
