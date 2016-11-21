package com.lin.weather;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lin on 2016/11/16.
 */

/*
获取Json数据步骤
1.通过URL得到 HttpURLConnection 对象   （1.HttpURLConnection对象需要Get方法的到数据 2.并传入apikey 3.提交）
2. 通过HttpURLconnection对象 的到 InputStream 输入流
3. 用BufferedReader 缓冲流读取InputStream输入流  需要将字符编码格式改为  UTF-8 模式。（BufferedReader对象读取每一行存储到JonString 字符串对象里行）
4.在主函数里面通过JsonString 得到JSONObject 对象
5.通过JSONObject 由接口 HeWeather data service 3.0 的到JsonArray对象
 */

public class GetJson {

    public static String request(String httpUrl, String httpArg) {
        Log.i("request------->","GetJson");
        BufferedReader reader=null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl2 = httpUrl + "?" + httpArg;
        try {
            //URL url = new URL(httpUrl2);
            URL url = new URL(httpUrl2);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey","你自己的 APIkey");
            connection.connect();
            Log.i("jsojo---------->",connection+"");
            InputStream is = connection.getInputStream();
            Log.i("jo------------->",is+" ");
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));// url.openConnection().getInputStream()  UTF-8
            //reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Log.i("fs------------>",reader+"");
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbf.toString();  //放回JsonString
    }

}
