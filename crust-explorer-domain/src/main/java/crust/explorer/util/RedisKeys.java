package crust.explorer.util;

public class RedisKeys {
    public static final String AUTH_KEY = "Authentication_";
    public static final String SYNC_TO_BLOCK = "sync_to_block:";
    public static final String CHAIN_ERA_DOMAIN = "chain_era_domain";
    public static final String CHAIN_REFRESH_BLOCK_DOMAIN = "chain_refresh_block_domain";
    public static final String CHAIN_SYNC_BLOCK_DOMAIN = "chain_sync_block_domain";
    public static final String BLOCK_HASH = "block_hash:";
    public static final String BLOCK_NUM = "block_num:";
    public static final String BLOCK_NUM_VO = "block_num_vo:";
    public static final String BLOCK_HASH_VO = "block_hash_vo:";
    public static final String EXTRINSIC_HASH = "extrinsic_hash:";
    public static final String EXTRINSIC_BLOCK_NUM = "extrinsic_block_num:";
    public static final String EXTRINSIC_HASH_VO = "extrinsic_hash_vo:";
    public static final String EXTRINSIC_BLOCK_NUM_VO = "extrinsic_block_num_vo:";
    public static final String COMPOSITE_QUERY = "composite_query:";
    public static final String COMPOSITE_QUERY_VO = "composite_query_vo:";

    public static final String BLOCK_TOTAL_COUNT = "block_total_count";
    public static final String EVENT_TOTAL_COUNT = "event_total_count";
    public static final String EXTRINSIC_TOTAL_COUNT = "extrinsic_total_count";
    public static final String REWARD_SLASH_TOTAL_COUNT = "reward_slash_total_count";
    public static final String TRANSFER_TOTAL_COUNT = "transfer_total_count";
    public static final String ACTIVE_PLEDGE_MAP_KEY = "active_pledge_map_key";
    public static final String WORK_CAPABILITY_MAP_KEY = "work_capability_map_key";

    /* redis 缓存时间 */
    public static final long CACHE_5S = 5;
    public final static long CACHE_2S = 2;
    public final static long CACHE_20S = 20;
    public final static long CACHE_5M = 5 * 60;


}
