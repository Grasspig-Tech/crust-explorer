package crust.explorer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ws.thread.pool")
public class ThreadConfigProperties {
    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity ;
    private int aliveTime;
    private String threadPrefix = "ws-";

}
