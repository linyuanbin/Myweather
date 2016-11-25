package com.lin.weather;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lin on 2016/11/20.
 */
public class Show {

    public static String[] date = {"date_d1","date_d2","date_d3","date_d4","date_d5","date_d6","date_d7"};
    public static String[] txt01 = {"txt_d1","txt_d2","txt_d3","txt_d4","txt_d5","txt_d6","txt_d7"};//这三个用于存储未来7 天天气
    public static String[] max = {"max1","max2","max3","max4","max5","max6","max7"};
    public static String[] min = {"min1","min2","min3","min4","min5","min6","min7"};


    private List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();  //一定记得初始化，否则空指针异常
    public List<Map<String,Object>> result(String jsonString) throws Exception {
        JSONObject jsonObject;
        jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
        jsonObject = jsonArray.getJSONObject(0);

        Map<String, Object> map=new HashMap<String, Object>(); //由于这里要传入两个字符串 第二个参数一定是 Object 而不是 Objects

        String city=jsonObject.getJSONObject("basic").getString("city"); //城市
        String cnty=jsonObject.getJSONObject("basic").getString("cnty"); //国家
        String update=jsonObject.getJSONObject("basic").getJSONObject("update").getString("loc");//当地时间  utc 就是utc时间

        String txt=jsonObject.getJSONObject("now").getJSONObject("cond").getString("txt");//天气状况 晴天还是雨天
        String fl=jsonObject.getJSONObject("now").getString("fl"); //体感温度
        String hum=jsonObject.getJSONObject("now").getString("hum");//相对湿度
        String pcpn=jsonObject.getJSONObject("now").getString("pcpn");//降水量
        String tmp=jsonObject.getJSONObject("now").getString("tmp"); //温度
        String vis=jsonObject.getJSONObject("now").getString("vis"); //能见度

        String deg=jsonObject.getJSONObject("now").getJSONObject("wind").getString("deg"); //风向360度
        String dir=jsonObject.getJSONObject("now").getJSONObject("wind").getString("dir"); //风向 （北方）
        String sc=jsonObject.getJSONObject("now").getJSONObject("wind").getString("sc");//风力 （3级）
        String spd=jsonObject.getJSONObject("now").getJSONObject("wind").getString("spd");//风速   kmph

        String aqi=jsonObject.getJSONObject("aqi").getJSONObject("city").getString("aqi");//空气指数
        String pm25=jsonObject.getJSONObject("aqi").getJSONObject("city").getString("pm25");
        Log.i("dsds------------>",city);
        Log.i("dsdsd----------->",cnty);
        Log.i("dsds------------>",spd);
        Log.i("dsdsd----------->",pm25);
        Log.i("dsds------------>",sc);
        Log.i("dsdsd----------->",deg);
        Log.i("dsds------------>",vis);
        Log.i("dsdsd----------->",tmp);
        Log.i("pm2.5----------->",pm25);


        //未来七天天气
       /* String date1=jsonObject.getJSONObject("daily_forecast").getString("date");
        String sr1=jsonObject.getJSONObject("daily_forecast").getJSONObject("astro").getString("sr");//日出时间
        String ss1=jsonObject.getJSONObject("daily_forecast").getJSONObject("astro").getString("ss");//日出时间

        String txt_d=jsonObject.getJSONObject("daily_forecast").getJSONObject("cond").getString("txt_d");//白天天气状况 （晴天阴天）
        String txt_n=jsonObject.getJSONObject("daily_forecast").getJSONObject("cond").getString("txt_n");//夜间天气状况

        String pcpn1=jsonObject.getJSONObject("daily_forecast").getString("pcpn");//降水量
        String hum1=jsonObject.getJSONObject("daily_forecast").getString("hum");//相对湿度
        String max=jsonObject.getJSONObject("daily_forecast").getJSONObject("tmp").getString("max");//最高温度
        String min=jsonObject.getJSONObject("daily_forecast").getJSONObject("tmp").getString("min");//最低温度
        String vis1=jsonObject.getJSONObject("daily_forecast").getString("vis");//能见度

        String dir1=jsonObject.getJSONObject("daily_forecast").getJSONObject("wind").getString("dir");//风向 东南风
        String sc1=jsonObject.getJSONObject("daily_forecast").getJSONObject("wind").getString("sc");//风力
*/

        JSONArray daily_forecast = jsonObject.getJSONArray("daily_forecast");
        for (int i = 0;i<daily_forecast.length();i++)
        {
            //map.put(date[i],jsonObject.getJSONObject("daily_forecast").getString("date"));
            JSONObject tq = daily_forecast.getJSONObject(i);
            map.put(txt01[i],tq.getJSONObject("cond").getString("txt_d"));
            map.put(max[i],tq.getJSONObject("tmp").getString("max"));
            map.put(min[i],tq.getJSONObject("tmp").getString("min"));


        }

        map.put("city",city);
        map.put("cnty",cnty);
        map.put("update",update);
        map.put("txt",txt); //
        map.put("fl",fl);
        map.put("hum",hum);//相对湿度
        map.put("pcpn",pcpn);//降水量
        map.put("tmp",tmp);//温度
        map.put("vis",vis);
        map.put("deg",deg);
        map.put("dir",dir);
        map.put("sc",sc);
        map.put("spd",spd);
        map.put("aqi",aqi);
        map.put("pm25",pm25);
        map.put("tmp",tmp);

        list.add(map);
        return list;
    }



}
