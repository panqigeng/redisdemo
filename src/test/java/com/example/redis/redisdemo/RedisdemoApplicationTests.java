package com.example.redis.redisdemo;

import com.example.redis.redisdemo.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@SpringBootTest
class RedisdemoApplicationTests {
    @Autowired
    private RedisUtil redisUtils;

    @Test
    void contextLoads() {
    }
    /**
     * 插入缓存数据
     */
    @Test
    public void set() {
        redisUtils.set("aa", "dwwwww");
    }

    /**
     * 读取缓存数据
     */
    @Test
    public void get() {
        String value = redisUtils.get("aa");
        System.out.println(value);
    }

    @Test
    public void getUrl() throws Exception {
        //接口地址
//        String url = "https://lab.ahusmart.com/nCoV/api/overall?latest=1";
//        String url = "https://lab.ahusmart.com/nCoV/api/area?province=上海市&latest=1";
        String url = "https://lab.ahusmart.com/nCoV/api/provinceName";

        SSLContext sc = createSslContext();
        HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier((s, sslSession) -> true);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while (null != (line = br.readLine())) {
                result.append(line).append("\n");
            }
        }
        conn.disconnect();

        String aa = result.toString();
        System.out.printf(result.toString());
    }


    private static SSLContext createSslContext() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSL");

        sc.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new java.security.SecureRandom());

        return sc;
    }
}
