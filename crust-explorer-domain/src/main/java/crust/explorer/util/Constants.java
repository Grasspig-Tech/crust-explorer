package crust.explorer.util;

public class Constants {

    public static final String MAX_TABLE = "MAX_TABLE";
    public static final String LAST_NETWORK_OVERVIEW = "last_network_overview";
    public static final String RECTIFY_NETWORK_OVERVIEW = "rectify_network_overview";
    public static final String OTHERS = "others";
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 25;
    public static final int MAX_PAGE_SIZE = 100;
    public static final long TIMEOUT = 60 * 1000L;
    public static final String WS_KEY = "ok";
    public static final String AUTH = "auth";
    public static final String AUTH_BIRTH = "auth_birth";
    public static final long AUTH_LIFE_TIME = 30 * 60 * 1000L;
    public static final String LIMIT_1 = "limit 1";
    public static final String LIMIT_10 = "limit 10";
    public static final String MODULE = "module";

    public static final int RESERVED_TABLE = 1;
    public static final int TABLE_BLOCK_MAX = 1000000;
    public static final String SYNC_BLOCK_BEGIN_TIME = "sync_block_begin_time";
    public static final long SYNC_PASS_TIME = 15 * 1000L;
    public static final long SYNC_SLEEP_2S = 2 * 1000L;
    public static final long SYNC_SLEEP_1S = 1000L;
    public static final Integer SYNC_ERROR_LOG_LENGTH = 2000;


    public static final String GET_HISTORY_BLOCK_URL1 = "/api/block/list1";
    public static final String GET_HISTORY_BLOCK_URL2 = "/api/block/list2";
    public static final String GET_HISTORY_BLOCK_URL3 = "/api/block/list3";
    public static final String GET_LAST_BLOCK_URL = "/api/block/last_block";
    public static final String GET_CURRENT_ERA_URL = "/api/era";
    public static final String GET_NETWORK_OVERVIEW_URL = "/api/network_overview";
}
