package crust.explorer.runner;

import crust.explorer.event.PutChainCountCacheEvent;
import crust.explorer.event.RefreshBlockEvent;
import crust.explorer.event.RemoveLongAgoDataEvent;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.pojo.po.NetworkOverviewPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    @Autowired
    private ApplicationContext context;

    public void publishSyncHistoryBlock(SyncBlockEvent event) {
        context.publishEvent(event);
    }

    public void publishRefreshBlock(RefreshBlockEvent event) {
        context.publishEvent(event);
    }

    public void publishRemoveLongAgoData() {
        context.publishEvent(new RemoveLongAgoDataEvent());
    }

    public void putChainCountCache(PutChainCountCacheEvent event) {
        context.publishEvent(event);
    }

    public void publishRefreshNetworkOverview(NetworkOverviewPO networkOverview) {
        context.publishEvent(networkOverview);
    }
}
