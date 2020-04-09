import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;
import utils.commonUtils;
import utils.writeStringToDisk;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ynuLogin {
    private static CloseableHttpClient httpClient;
    private static List<Cookie> cookies;
    private static BasicCookieStore cookieStore=new BasicCookieStore();


    @Test
    public void login() throws ScriptException, IOException {


        List<NameValuePair> nvps=new ArrayList<>();


        httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

//        CloseableHttpClient httpclient = HttpClients.createDefault();



        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        //js文件的路径
        //读取js
        String strFile =  this.getClass().getResource("/encrypt.js").getPath();;
        System.out.println(strFile);
        Reader reader = new FileReader(new File(strFile));
        CloseableHttpResponse response=commonUtils.sendGet("https://ids.ynu.edu.cn/authserver/login",httpClient);

        Document doc = Jsoup.parse(EntityUtils.toString(response.getEntity(),"utf-8"));


        //写入文件
        writeStringToDisk.writeHtml(doc.toString(),"1");

        response.close();
       cookies=cookieStore.getCookies();
        for(Cookie cookie:cookies  ){
            System.out.println("name:"+cookie.getName()+";value:"+cookie.getValue()+";domain:"+cookie.getDomain());

        }



//        System.out.println(doc);
        Element pwdDefaultEncryptSalt = doc.getElementById("pwdDefaultEncryptSalt");
        Element casLoginForm = doc.getElementById("casLoginForm");
        Elements dllt=casLoginForm.select("input[type='hidden']");
       for(Element e:dllt){
           System.out.println("属性："+e.attr("name")+",值："+e.attr("value"));
           if(e.attr("name")!=null&&e.attr("name")!="")
           nvps.add(new BasicNameValuePair(e.attr("name"),e.attr("value")));

       }
        engine.eval(reader);
        engine.eval("var ps=encryptAES('m*chongda961225','"+pwdDefaultEncryptSalt.attr("value")+"');");
        String password=(String)engine.getContext().getAttribute("ps");
        nvps.add(new BasicNameValuePair("password",password));

        System.out.println("++++++++++++++++++BasicNameValuePairs++++++++++++++++++++++");
        for(NameValuePair b:nvps){
            System.out.println("name:"+b.getName()+";value:"+b.getValue());


        }

        System.out.println("++++++++++++++++++BasicNameValuePairs++++++++++++++++++++++");

        response=commonUtils.sendPost("https://ids.ynu.edu.cn/authserver/login",nvps,httpClient);


//        while(response.getStatusLine().getStatusCode()==302){
//            Header[] headers = response.getHeaders("Location");
//
//            response=commonUtils.sendGet(headers[0].getValue(),httpClient);
//
//        }

        System.out.println("++++++++++++++++++response status++++++++++++++++++++++");
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println("++++++++++++++++++response status++++++++++++++++++++++");
        doc = Jsoup.parse(EntityUtils.toString(response.getEntity(),"utf-8"));
        //写入文件
        writeStringToDisk.writeHtml(doc.toString(),"2");

        Header[] headers = response.getHeaders("Set-Cookie");
        System.out.println("++++++++++++++++++response cookie++++++++++++++++++++++");
        for( Header h :headers ){
            System.out.println("name:"+h.getName()+";value:"+h.getValue());


        }

        response.close();

        cookies=cookieStore.getCookies();
        for(Cookie cookie:cookies  ){
            System.out.println("name:"+cookie.getName()+";value:"+cookie.getValue()+";domain:"+cookie.getDomain());

        }






        response=commonUtils.sendGet("https://ids.ynu.edu.cn/authserver/index.do",httpClient);

         doc = Jsoup.parse(EntityUtils.toString(response.getEntity(),"utf-8"));
        response.close();

        System.out.println(doc);
        // System.out.pringetContext().getAttribute(attributeName)t(commonUtils.sendGet("http://ehall.ynu.edu.cn/new/index.html"));

    }


}
