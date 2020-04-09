package utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class commonUtils {



    public static CloseableHttpResponse sendGet(String url,CloseableHttpClient httpClient) {
        if(httpClient==null)
            httpClient = HttpClients.createDefault();


        CloseableHttpResponse response = null;
        String content = null;
        try {
            HttpGet get = new HttpGet(url);

            get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            get.addHeader("Accept-Encoding", "gzip, deflate, br");
            get.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-HK;q=0.7");
            get.addHeader("Cache-Control", "max-age=0");
            get.addHeader("Connection", "keep-alive");
            get.addHeader("Content-Type", "application/x-www-form-urlencoded");
            get.addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");
           // get.addHeader("", "");




            response = httpClient.execute(get);
//            HttpEntity entity = response.getEntity();
//            content = EntityUtils.toString(entity,"utf-8");
//            EntityUtils.consume(entity);
            return response;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }

    // application/x-www-form-urlencoded
    public static CloseableHttpResponse sendPost(String url, List<NameValuePair> nvps,CloseableHttpClient httpClient) {

        HttpPost post=new HttpPost(url);

        post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        post.addHeader("Accept-Encoding", "gzip, deflate, br");
        post.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-HK;q=0.7");
        post.addHeader("Cache-Control", "max-age=0");
        post.addHeader("Connection", "keep-alive");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        post.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");
        if(httpClient==null)
          httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String content = null;
        try {
            // nvps是包装请求参数的list
            if (nvps != null) {
                post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }
            // 执行请求用execute方法，content用来帮我们附带上额外信息
            response = httpClient.execute(post);
//            // 得到相应实体、包括响应头以及相应内容
//            HttpEntity entity = response.getEntity();
//            // 得到response的内容
//            content = EntityUtils.toString(entity);
//            EntityUtils.consume(entity);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return response;
    }

    // application/json
    public static String sendPostJson (String url, JSONObject object) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            // json方式
            StringEntity entity = new StringEntity(object.toString(),"utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(entity);
            HttpResponse resp = httpClient.execute(httpPost);
            if(resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity he = resp.getEntity();
                return EntityUtils.toString(he,"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }










}
