package com.yuyu.soft.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
    private static final String TAG             = "HttpUtils";
    private static final int    mReadTimeOut    = 1000 * 10;  // 10秒
    private static final int    mConnectTimeOut = 1000 * 5;   // 5秒
    private static final String CHAR_SET        = "UTF-8";
    private static final int    mRetry          = 2;          // 默认尝试访问次数

    private static HttpPost postForm(String url, Map<String, String> params) {

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }

    /**
    * 发送https请求
    * @param url
    * @param method
    * @param data
    * @return
    */
    public static String execute(String url, String method, String data) {
        CloseableHttpClient httpClient = createSSLClientDefault();
        CloseableHttpResponse response = null;
        String content = "";
        try {
            if ("post".equals(method)) {
                HttpPost post = new HttpPost(url);
                post.addHeader("Content-Type", "text/html;charset=utf-8");
                StringEntity entity = new StringEntity(data, "utf-8");
                entity.setContentEncoding("utf-8");
                post.setEntity(entity);
                response = httpClient.execute(post);
            } else {
                HttpGet get = new HttpGet(url);
                get.addHeader("Content-Type", "text/html;charset=utf-8");
                response = httpClient.execute(get);
            }
            content = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
                new TrustStrategy() {
                    //信任所有
                    public boolean isTrusted(X509Certificate[] chain, String authType)
                                                                                      throws CertificateException {
                        return true;
                    }
                }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * 发送http
     * @param url
     * @param method
     * @param map (key/value)
     * @return
     */
    public static String executeHttp(String url, String method, Map<String, String> map) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000)
            .setConnectionRequestTimeout(20000).setSocketTimeout(20000).build();
        CloseableHttpResponse response = null;
        String content = "";
        try {
            if ("post".equals(method)) {
                HttpPost post = new HttpPost(url);
                post.setConfig(requestConfig);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                for (String key : map.keySet()) {
                    nameValuePairs.add(new BasicNameValuePair(key, map.get(key)));
                }
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                response = httpClient.execute(post);
            } else {
                // 创建uri
                URIBuilder builder = new URIBuilder(url);
                if (map != null) {
                    for (String key : map.keySet()) {
                        builder.addParameter(key, map.get(key));
                    }
                }
                URI uri = builder.build();
                HttpGet get = new HttpGet(uri);
                get.setConfig(requestConfig);
                response = httpClient.execute(get);
            }
            content = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    //发送json数据
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }

    //==============================================分隔符========================================================//

    public static String get(String url) throws Exception {
        return get(url, null);
    }

    public static String get(String url, Map<String, ? extends Object> params) throws Exception {
        return get(url, params, null);
    }

    public static String get(String url, Map<String, ? extends Object> params,
                             Map<String, String> headers) throws Exception {
        if (url == null || url.trim().length() == 0) {
            throw new Exception(TAG + ": url is null or empty!");
        }

        if (params != null && params.size() > 0) {
            if (!url.contains("?")) {
                url += "?";
            }

            if (url.charAt(url.length() - 1) != '?') {
                url += "&";
            }

            url += buildParams(params);
        }

        return tryToGet(url, headers);
    }

    public static String buildParams(Map<String, ? extends Object> params)
                                                                          throws UnsupportedEncodingException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, ? extends Object> entry : params.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null)
                builder.append(entry.getKey().trim()).append("=")
                    .append(URLEncoder.encode(entry.getValue().toString(), CHAR_SET)).append("&");
        }

        if (builder.charAt(builder.length() - 1) == '&') {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

    private static String tryToGet(String url, Map<String, String> headers) throws Exception {
        int tryTime = 0;
        Exception ex = null;
        while (tryTime < mRetry) {
            try {
                return doGet(url, headers);
            } catch (Exception e) {
                if (e != null)
                    ex = e;
                tryTime++;
            }
        }
        if (ex != null)
            throw ex;
        else
            throw new Exception("未知网络错误 ");
    }

    private static String doGet(String strUrl, Map<String, String> headers) throws Exception {
        HttpURLConnection connection = null;
        InputStream stream = null;
        try {

            connection = getConnection(strUrl);
            configConnection(connection);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connection.setInstanceFollowRedirects(true);
            connection.connect();

            stream = connection.getInputStream();
            ByteArrayOutputStream obs = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int len = 0; (len = stream.read(buffer)) > 0;) {
                obs.write(buffer, 0, len);
            }
            obs.flush();
            obs.close();
            stream.close();

            return new String(obs.toByteArray());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (stream != null) {
                stream.close();
            }
        }
    }

    public static String post(String url) throws Exception {
        return post(url, null);
    }

    public static String post(String url, Map<String, ? extends Object> params) throws Exception {
        return post(url, params, null);
    }

    public static String post(String url, Map<String, ? extends Object> params,
                              Map<String, String> headers) throws Exception {
        if (url == null || url.trim().length() == 0) {
            throw new Exception(TAG + ":url is null or empty!");
        }

        if (params != null && params.size() > 0) {
            return tryToPost(url, buildParams(params), headers);
        } else {
            return tryToPost(url, null, headers);
        }
    }

    public static String post(String url, String content, Map<String, String> headers)
                                                                                      throws Exception {
        return tryToPost(url, content, headers);
    }

    private static String tryToPost(String url, String postContent, Map<String, String> headers)
                                                                                                throws Exception {
        int tryTime = 0;
        Exception ex = null;
        while (tryTime < mRetry) {
            try {
                return doPost(url, postContent, headers);
            } catch (Exception e) {
                if (e != null)
                    ex = e;
                tryTime++;
            }
        }
        if (ex != null)
            throw ex;
        else
            throw new Exception("未知网络错误 ");
    }

    private static String doPost(String strUrl, String postContent, Map<String, String> headers)
                                                                                                throws Exception {
        HttpURLConnection connection = null;
        InputStream stream = null;
        try {
            connection = getConnection(strUrl);
            configConnection(connection);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            if (null != postContent && !"".equals(postContent)) {
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                dos.write(postContent.getBytes(CHAR_SET));
                dos.flush();
                dos.close();
            }
            stream = connection.getInputStream();
            ByteArrayOutputStream obs = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            for (int len = 0; (len = stream.read(buffer)) > 0;) {
                obs.write(buffer, 0, len);
            }
            obs.flush();
            obs.close();

            return new String(obs.toByteArray());

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (stream != null) {
                stream.close();
            }
        }

    }

    private static void configConnection(HttpURLConnection connection) {
        if (connection == null)
            return;
        connection.setReadTimeout(mReadTimeOut);
        connection.setConnectTimeout(mConnectTimeOut);

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection
            .setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
    }

    private static HttpURLConnection getConnection(String strUrl) throws Exception {
        if (strUrl == null) {
            return null;
        }
        if (strUrl.toLowerCase().startsWith("https")) {
            return getHttpsConnection(strUrl);
        } else {
            return getHttpConnection(strUrl);
        }
    }

    private static HttpURLConnection getHttpConnection(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        return conn;
    }

    private static HttpsURLConnection getHttpsConnection(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier(hnv);
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        if (sslContext != null) {
            TrustManager[] tm = { xtm };
            sslContext.init(null, tm, null);
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            conn.setSSLSocketFactory(ssf);
        }

        return conn;
    }

    private static X509TrustManager xtm = new X509TrustManager() {
                                            public void checkClientTrusted(X509Certificate[] chain,
                                                                           String authType) {
                                            }

                                            public void checkServerTrusted(X509Certificate[] chain,
                                                                           String authType) {
                                            }

                                            public X509Certificate[] getAcceptedIssuers() {
                                                return null;
                                            }
                                        };

    private static HostnameVerifier hnv = new HostnameVerifier() {
                                            public boolean verify(String hostname,
                                                                  SSLSession session) {
                                                return true;
                                            }
                                        };
}
