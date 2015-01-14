package com.topsem.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/12/10.
 */
public class IPAddressUtil {

    private static final String PATH = "http://ip.taobao.com/service/getIpInfo.php";

    private static final String DEFAULT_ENCODING = "UTF-8";

    private IPAddressUtil() {

    }

    private static IPAddressUtil instance;

    private static LoadingCache<String, String> ipInfoCache = CacheBuilder
        .newBuilder().expireAfterWrite(7, TimeUnit.DAYS)
        .build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return "";
            }
        });

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            System.out.println(IPAddressUtil.getIPLocationInfo("210.21.36.100"));
        }
    }

    public static String getIPLocationInfo(String ip) throws Exception {
        String ipInfo = ipInfoCache.getUnchecked(ip);
        String params = "?ip=" + ip;
        String locationInfo = StringUtils.isEmpty(ipInfo) ? IPAddressUtil.getInstance().getAddress(params, DEFAULT_ENCODING) : ipInfo;
        ipInfoCache.put(ip, locationInfo);
        return locationInfo;
    }

    public static synchronized IPAddressUtil getInstance() {
        if (instance == null) {
            instance = new IPAddressUtil();
        }
        return instance;
    }

    /**
     * 获取地址
     *
     * @param params
     * @param encoding
     * @return
     * @throws Exception
     */
    public String getAddress(String params, String encoding) throws Exception {
        String returnStr = this.getResponseContext(PATH, params, encoding);
        JSONObject json = null;
        if (returnStr != null) {
            json = JSON.parseObject(returnStr);
            if ("0".equals(json.get("code").toString())) {
                StringBuffer buffer = new StringBuffer();
                /**
                 淘宝ip查询接口数据example
                 {"code":0,"data":{"country":"\u4e2d\u56fd","country_id":"CN","area":"\u534e\u5357","area_id":"800000","region":"\u5e7f\u4e1c\u7701","region_id":"440000","city":"\u4f5b\u5c71\u5e02","city_id":"440600","county":"","county_id":"-1","isp":"\u7535\u4fe1","isp_id":"100017","ip":"121.9.243.50"}}
                 */
                json = json.getJSONObject("data");

//                buffer.append(decodeUnicode(json.getString("country")));//国家
//                buffer.append(decodeUnicode(json.getString("area")));//地区

                buffer.append(decodeUnicode(json.getString("region")));//省份
                buffer.append(decodeUnicode(json.getString("city")));//市区
                buffer.append(decodeUnicode(json.getString("county")));//地区
                buffer.append(" " + decodeUnicode(json.getString("isp")));//ISP公司

                return buffer.toString();
            } else {
                return "获取地址失败";
            }
        }
        return null;
    }

    /**
     * 从url获取结果
     *
     * @param path
     * @param params
     * @param encoding
     * @return
     */
    public String getResponseContext(String path, String params, String encoding) throws IOException {
        String context = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(PATH + params);
            // Execute HTTP request
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // Get hold of the response entity
                HttpEntity entity = response.getEntity();
                context = EntityUtils.toString(entity, encoding);
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return context;
    }

    /**
     * 字符转码
     *
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer buffer = new StringBuffer(len);
        for (int i = 0; i < len; ) {
            aChar = theString.charAt(i++);
            if (aChar == '\\') {
                aChar = theString.charAt(i++);
                if (aChar == 'u') {
                    int val = 0;
                    for (int j = 0; j < 4; j++) {
                        aChar = theString.charAt(i++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                val = (val << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                val = (val << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                val = (val << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                    "Malformed      encoding.");
                        }
                    }
                    buffer.append((char) val);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    }
                    if (aChar == 'r') {
                        aChar = '\r';
                    }
                    if (aChar == 'n') {
                        aChar = '\n';
                    }
                    if (aChar == 'f') {
                        aChar = '\f';
                    }
                    buffer.append(aChar);
                }
            } else {
                buffer.append(aChar);
            }
        }
        return buffer.toString();

    }

    //穿透代理获取真实IP
    public static String getRealIPAddress(HttpServletRequest request) {
        String strClientIp = request.getHeader("x-forwarded-for");
        if (strClientIp == null || strClientIp.length() == 0 || "unknown".equalsIgnoreCase(strClientIp)) {
            strClientIp = request.getRemoteAddr();
        } else {
            List<String> ipList = Lists.newArrayList();
            ipList = Arrays.asList(strClientIp.split(","));
            String strIp = new String();
            for (int index = 0; index < ipList.size(); index++) {
                strIp = (String) ipList.get(index);
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    strClientIp = strIp;
                    break;
                }
            }
        }
        return strClientIp;
    }
}
