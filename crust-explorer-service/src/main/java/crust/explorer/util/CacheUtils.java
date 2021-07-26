package crust.explorer.util;

import com.google.gson.reflect.TypeToken;
import crust.explorer.enums.TableEnum;
import crust.explorer.event.PutChainCountCacheEvent;
import crust.explorer.pojo.vo.TableVo;
import crust.explorer.runner.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Component
public class CacheUtils {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    Publisher publisher;

    public List<TableVo> getChainCountFromCache(TableEnum tableEnum, String hash, List<Integer> tableNos) {
        String key = tableEnum.getTotalCountKey();
        if (StringUtils.isNotBlank(hash)) {
            key += (":" + hash);
        }
        String s = redisUtils.get(key);
        if (StringUtils.isNotBlank(s)) {
            List<TableVo> list = GsonUtils.getInstance().fromJson(s, new TypeToken<List<TableVo>>() {
            }.getType());
            long life = redisUtils.getLife(key);
            if (life <= RedisKeys.CACHE_2S) {
                PutChainCountCacheEvent event = PutChainCountCacheEvent.builder().
                        tableEnum(tableEnum).secondKey(hash).tableNos(tableNos).build();
                publisher.putChainCountCache(event);
            }
            if (!CollectionUtils.isEmpty(list)) {
                return list;
            }
        }
        return null;
    }

    public void putChainCountToCache(TableEnum tableEnum, String hash, List<TableVo> totalCount) {
        if (Objects.nonNull(tableEnum) && !CollectionUtils.isEmpty(totalCount)) {
            String key = tableEnum.getTotalCountKey();
            if (StringUtils.isNotBlank(hash)) {
                key += (":" + hash);
            }
            redisUtils.set(key, totalCount, RedisKeys.CACHE_5S);
        }
    }
}
