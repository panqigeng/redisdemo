package com.example.redis.redisdemo.controller;


import com.example.redis.redisdemo.dto.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

/**
 * @author jay.zhou
 * @date 2019/1/24
 * @time 16:16
 */
@RestController
@RequestMapping("/Indexlogin")
public class IndexController {

    //测试1111111111

    @Value("${server.port}")
    private String port;

    @RequestMapping("/hi")
    public String hi(String name) {
        return "hi " + name + " , l am " + port + " port";
    }

    @RequestMapping("/login")
    public String hi(@RequestBody UserInfo userInfo) {
        return "admin";
    }

    @RequestMapping("/info")
    public UserInfo info(@RequestParam(value = "token") String token) {
        UserInfo userInfo = new UserInfo();
        userInfo.setRoles("['admin']");
        userInfo.setIntroduction("I am a super administrator");
        userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        userInfo.setName("Super Admin");
        return userInfo;
    }

    @RequestMapping("/logout")
    public String info() {
        return "";
    }


//    @RequestMapping("/test")
//    public void test(String name) {
//        UserDemoExample  userDemoExample  = new UserDemoExample();
//        userDemoExample.createCriteria().andAgeEqualTo(name);
//        int m =userDemoMapper.countByExample(userDemoExample);
////        return "admin";
//    }
    @RequestMapping("/geturlAll")
    @ResponseBody
    public String geturlAll(@RequestParam(value = "latest") String latest) throws Exception {
        //接口地址
        String url = "https://lab.ahusmart.com/nCoV/api/overall?"+latest;

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
//        System.out.printf(result.toString());
        return aa;
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