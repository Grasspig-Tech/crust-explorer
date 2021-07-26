package crust.explorer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * http客户端处理类
 */
@Slf4j
public class HttpClientHandler {

    /**
     * 最大的连接数
     */
    private static final int DEFAULT_MAX_TOTAL = 200;

    private static final int DEFAULT_MAX_PER_ROUTE = 20;


    /**
     * 等待数据的时间或者两个包之间的间隔时间，默认20秒
     */
    private final static int SOCKET_TIMEOUT = 50 * 1000;

    /**
     * 链接建立的时间，默认15秒
     */
    private final static int CONNECT_TIMEOUT = 50 * 1000;

    /**
     * 配置连接池获取超时时间 3 秒
     */
    private static final int CONNECTION_REQUEST_TIMEOUT = 3 * 1000;

    /**
     * 连接池
     */
    private static PoolingHttpClientConnectionManager connManager = null;

    /**
     * http初始化连接配置
     */
    private static RequestConfig requestConfig = null;

    /**
     * 是否使用连接池
     */
    private static boolean usePool = false;

    static {

        try {

            // 创建Http请求配置参数
            requestConfig = RequestConfig.custom()
                    // 获取连接超时时间
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                    // 请求超时时间
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    // 响应超时时间
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .build();

            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();

            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            connManager.setMaxTotal(DEFAULT_MAX_TOTAL);
            connManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 关闭无效的链接、异常链接和空闲的链接
     *
     * @param idleTimeOut
     * @param timeUnit
     */
    public static void clearUnusedConnection(long idleTimeOut, TimeUnit timeUnit) {

        connManager.closeExpiredConnections();
        connManager.closeIdleConnections(idleTimeOut, timeUnit);

    }

    /**
     * 设置是否使用连接池
     *
     * @param usePool
     */
    public static void setUsePool(boolean usePool) {
        HttpClientHandler.usePool = usePool;
    }

    /**
     * 获取Http客户端连接对象
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {

        if (HttpClientHandler.usePool) {
            /*
             * 测出超时重试机制为了防止超时不生效而设置
             *  如果直接放回false,不重试
             *  这里会根据情况进行判断是否重试
             */
            HttpRequestRetryHandler retry = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                    if (executionCount >= 1) {// 如果已经重试了3次，就放弃
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {// 超时
                        return true;
                    }
                    if (exception instanceof UnknownHostException) {// 目标服务器不可达
                        return false;
                    }
                    if (exception instanceof SSLException) {// ssl握手异常
                        return false;
                    }
                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    // 如果请求是幂等的，就再次尝试
                    return !(request instanceof HttpEntityEnclosingRequest);
                }
            };


            // 创建httpClient
            return HttpClients.custom()
                    // 把请求相关的超时信息设置到连接客户端
                    .setDefaultRequestConfig(requestConfig)
                    // 把请求重试设置到连接客户端
                    .setRetryHandler(retry)
                    // 配置连接池管理对象
                    .setConnectionManager(connManager)
                    .build();

        } else {
            return HttpClients.custom()
                    // 把请求相关的超时信息设置到连接客户端
                    .setDefaultRequestConfig(requestConfig)
                    .build();
        }
    }

    /**
     * GET接口网络请求
     *
     * @param url    调用的地址
     * @param params 参数
     * @return String
     * @throws IOException
     */
    public static String get(String url, Map<String, String> params) throws IOException, URISyntaxException {
        return get(url, null, params);
    }

    /**
     * GET接口网络请求
     *
     * @param url     调用的地址
     * @param headers 请求头参数
     * @param params  参数
     * @return String
     * @throws IOException
     */
    public static String get(String url, Map<String, String> headers, Map<String, String> params) throws IOException, URISyntaxException {

        String result = "";
        URIBuilder uri = new URIBuilder(url);

        if (null != params) {
            for (Entry<String, String> entry : params.entrySet()) {
                uri.setParameter(entry.getKey(), entry.getValue());
            }
        }

        HttpGet httpGet = new HttpGet(uri.build());

        if (null != headers) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            /* 读返回数据 */
            result = EntityUtils.toString(entity, "UTF-8");
            log.debug("get url: {}, result: {}", url, result);

        } catch (ConnectionPoolTimeoutException e) {
            log.error("http get throw ConnectionPoolTimeoutException(wait time out)", e);
            throw e;
        } catch (ConnectTimeoutException e) {
            log.error("http get throw ConnectTimeoutException", e);
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("http get throw SocketTimeoutException", e);
            throw e;
        } catch (Exception e) {
            log.error("http get throw Exception", e);
        } finally {
            if (usePool) {
                if (null != response) {
                    try {
                        EntityUtils.consume(response.getEntity());
                        response.close();
                    } catch (IOException e) {
                        log.error("http链接回收异常", e);
                    }
                }
            } else {
                httpGet.abort();
                httpClient.close();
            }

        }
        return result;
    }


    /**
     * POST接口网络请求（JSON传输格式）
     *
     * @param url       调用的地址
     * @param textPlain JSON参数
     * @return String
     * @throws IOException
     */
    public static String postTextPlain(String url, String textPlain) throws IOException {
        return postTextPlain(url, textPlain, null);
    }


    /**
     * POST接口网络请求（JSON传输格式）
     *
     * @param url  调用的地址
     * @param text 发送的内容
     * @return String
     * @throws IOException
     */
    private static String postTextPlain(String url, String text, Map<String, String> headers) throws IOException {
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        if (null != headers) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        StringEntity strEntity = new StringEntity(text);
        strEntity.setContentEncoding("UTF-8");
        strEntity.setContentType("text/plain");
        httpPost.setEntity(strEntity);

        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            /* 读返回数据 */
            result = EntityUtils.toString(entity, "UTF-8");
            log.debug("post url: {}, result: {}", url, result);

        } catch (ConnectionPoolTimeoutException e) {
            log.error("http post throw ConnectionPoolTimeoutException(wait time out)", e);
            throw e;
        } catch (ConnectTimeoutException e) {
            log.error("http post throw ConnectTimeoutException", e);
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("http post throw SocketTimeoutException", e);
            throw e;
        } catch (Exception e) {
            log.error("http post throw Exception", e);
        } finally {
            if (usePool) {
                if (null != response) {
                    try {
                        EntityUtils.consume(response.getEntity());
                        response.close();
                    } catch (IOException e) {
                        log.error("http链接回收异常", e);
                    }
                }
            } else {
                httpPost.abort();
                httpClient.close();
            }

        }
        return result;
    }


    /**
     * POST接口网络请求（JSON传输格式）
     *
     * @param url  调用的地址
     * @param json JSON参数
     * @return String
     * @throws IOException
     */
    public static String post(String url, String json) throws IOException {
        return postJson(url, json, null);
    }

    /**
     * POST接口网络请求（JSON传输格式）
     *
     * @param url  调用的地址
     * @param json JSON参数
     * @return String
     * @throws IOException
     */
    public static String postJson(String url, String json, Map<String, String> headers) throws IOException {
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        if (null != headers) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        StringEntity strEntity = new StringEntity(json, "utf-8");
        strEntity.setContentEncoding("UTF-8");
        strEntity.setContentType("application/json");
        httpPost.setEntity(strEntity);

        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;

        try {

            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            /* 读返回数据 */
            result = EntityUtils.toString(entity, "UTF-8");
            log.debug("post url: {}, result: {}", url, result);

        } catch (ConnectionPoolTimeoutException e) {
            log.error("http post throw ConnectionPoolTimeoutException(wait time out)", e);
            throw e;
        } catch (ConnectTimeoutException e) {
            log.error("http post throw ConnectTimeoutException", e);
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("http post throw SocketTimeoutException", e);
            throw e;
        } catch (Exception e) {
            log.error("http post throw Exception", e);
        } finally {
            if (usePool) {
                if (null != response) {
                    try {
                        EntityUtils.consume(response.getEntity());
                        response.close();
                    } catch (IOException e) {
                        log.error("http链接回收异常", e);
                    }
                }
            } else {
                httpPost.abort();
                httpClient.close();
            }

        }
        return result;
    }

    /**
     * POST接口网络请求（JSON传输格式）
     *
     * @param url  调用的地址
     * @param json JSON参数
     * @return String
     * @throws IOException
     */
    public static String putJson(String url, String json, Map<String, String> headers) throws IOException {
        String result = "";
        HttpPut httpPost = new HttpPut(url);
        if (null != headers) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        StringEntity strEntity = new StringEntity(json, "utf-8");
        strEntity.setContentEncoding("UTF-8");
        strEntity.setContentType("application/json");
        httpPost.setEntity(strEntity);

        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;

        try {

            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            /* 读返回数据 */
            result = EntityUtils.toString(entity, "UTF-8");
            log.debug("post url: {}, result: {}", url, result);

        } catch (ConnectionPoolTimeoutException e) {
            log.error("http post throw ConnectionPoolTimeoutException(wait time out)", e);
            throw e;
        } catch (ConnectTimeoutException e) {
            log.error("http post throw ConnectTimeoutException", e);
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("http post throw SocketTimeoutException", e);
            throw e;
        } catch (Exception e) {
            log.error("http post throw Exception", e);
        } finally {
            if (usePool) {
                if (null != response) {
                    try {
                        EntityUtils.consume(response.getEntity());
                        response.close();
                    } catch (IOException e) {
                        log.error("http链接回收异常", e);
                    }
                }
            } else {
                httpPost.abort();
                httpClient.close();
            }

        }
        return result;
    }

    /**
     * POST接口网络请求
     *
     * @param url    调用的地址
     * @param params 参数
     * @return String
     * @throws IOException
     */
    public static String post(String url, Map<String, String> params) throws IOException {
        return post(url, null, params);
    }

    /**
     * POST接口网络请求
     *
     * @param url     调用的地址
     * @param headers 请求头参数
     * @param params  参数
     * @return String
     * @throws IOException
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> params) throws IOException {

        String result = "";
        HttpPost httpPost = new HttpPost(url);

        if (null != headers) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if (null != params) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for (Entry<String, String> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
        }

        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            /* 读返回数据 */
            result = EntityUtils.toString(entity, "UTF-8");
            log.debug("post url: {}, result: {}", url, result);

        } catch (ConnectionPoolTimeoutException e) {
            log.error("http post throw ConnectionPoolTimeoutException(wait time out)", e);
            throw e;
        } catch (ConnectTimeoutException e) {
            log.error("http post throw ConnectTimeoutException", e);
            throw e;
        } catch (SocketTimeoutException e) {
            log.error("http post throw SocketTimeoutException", e);
            throw e;
        } catch (Exception e) {
            log.error("http post throw Exception", e);
        } finally {
            if (usePool) {
                if (null != response) {
                    try {
                        EntityUtils.consume(response.getEntity());
                        response.close();
                    } catch (IOException e) {
                        log.error("http链接回收异常", e);
                    }
                }
            } else {
                httpPost.abort();
                httpClient.close();
            }
        }
        return result;
    }

}
