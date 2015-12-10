package com.thd.wx.template;

import com.google.gson.Gson;
import com.thd.wx.util.DateUtil;
import net.sf.json.JSONObject;
import org.apache.tools.ant.taskdefs.condition.Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class Token {
    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public static void main(String[] args) throws IOException {
        String token = getToken();
        String getUserUrl = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=" + token;
        StringBuilder postData = new StringBuilder("{");
        postData.append(appendValue("touser"));
        postData.append(":");
        postData.append(appendValue("oXA_CjsYobQ31Acsc01MfLklTqDE"));//王菲
        //postData.append("oXA_CjjDhdYDbtuOX1RFi3CWd4iE");//李德成
        //postData.append(appendValue("oXA_CjpQtQZvbFindMY8pZXX9Mhg"));//李德成2
        //postData.append("oXA_CjieBQKMUFn4zTkj5jygtiqs");//陶静
        postData.append(",");
        postData.append(appendValue("template_id"));
        postData.append(":");
        postData.append(appendValue("-JpAPbYISCwUnFkD6RP5g7FYGKoOhiYmcBuxOyS-31M"));
        postData.append(",");
        postData.append(appendValue("url"));
        postData.append(":");
        postData.append(appendValue("http://weixin.qq.com/download"));
        postData.append(",");

        postData.append(appendValue("data"));
        postData.append(":{");
        postData.append(appendValue("first"));
        postData.append(":{");
        postData.append(appendValue("value"));
        postData.append(":");
        postData.append(appendValue("测试模板消息-陶发登"));
        postData.append(",");
        postData.append(appendValue("color"));
        postData.append(":");
        postData.append(appendValue("#173177"));
        postData.append("},");

        postData.append(appendValue("time"));
        postData.append(":{");
        postData.append(appendValue("value"));
        postData.append(":");
        postData.append(appendValue(DateUtil.getNow()));
        postData.append(",");
        postData.append(appendValue("color"));
        postData.append(":");
        postData.append(appendValue("#173177"));
        postData.append("},");

        postData.append(appendValue("reason"));
        postData.append(":{");
        postData.append(appendValue("value"));
        postData.append(":");
        postData.append(appendValue("李德成累了"));
        postData.append(",");
        postData.append(appendValue("color"));
        postData.append(":");
        postData.append(appendValue("#173177"));
        postData.append("},");

        postData.append(appendValue("remark"));
        postData.append(":{");
        postData.append(appendValue("value"));
        postData.append(":");
        postData.append(appendValue("我是备注信息"));
        postData.append(",");
        postData.append(appendValue("color"));
        postData.append(":");
        postData.append(appendValue("#173177"));
        postData.append("}");

        postData.append("}}");

        String data = postData.toString();
        System.out.println(data);
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        System.out.println("jsonData  " + jsonData);

        String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        sendPost(sendUrl, data);
    }

    private static String getToken() throws IOException {
        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
        String getURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxa06b4ae965be8fe8&secret=4185be473df8830782770b55990cf6bf";

        URL getUrl = new URL(getURL);
        // 根据拼凑的URL，打开连接，URL.openConnection()函数会根据 URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        // 建立与服务器的连接，并未发送数据
        connection.connect();

        // 发送数据到服务器并使用Reader读取返回的数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        System.out.println(" ============================= ");
        System.out.println(" Contents of get request ");
        System.out.println(" ============================= ");

        String lines;
        StringBuilder json = new StringBuilder();
        while ((lines = reader.readLine()) != null) {
            json.append(lines);
            System.out.println(lines);
        }

        System.out.println(json.toString());
        JSONObject jsonObject = JSONObject.fromObject(json.toString());
        String token = (String) jsonObject.get("access_token");
        System.out.println(token);
        reader.close();

        // 断开连接
        connection.disconnect();

        System.out.println(" ============================= ");
        System.out.println(" Contents of get request ends ");
        System.out.println(" ============================= ");
        return token;
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "/n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        } finally {// 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String appendValue(String value) {
        return "\"" + value + "\"";
    }
}
