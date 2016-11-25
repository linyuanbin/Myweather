package com.lin.weather;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {


    private String httpUrl = "http://apis.baidu.com/heweather/weather/free";
    private String httpArg = "city=";
    private String jsonString=null;
    private EditText mCityName;
    private TextView TV_city;
    private TextView TV_tmp;
    private TextView TV_cnty;
    private TextView TV_txt;
    private TextView TV_dir;
    private TextView TV_sc;
    private TextView TV_aqi;
    private TextView TV_pm25;
    private TextView TV_date;
    private TextView TV_txt01;
    private TextView TV_tmp01;
    private TextView TV_txt02;
    private TextView TV_tmp02;
    private TextView TV_txt03;
    private TextView TV_tmp03;
    private TextView TV_txt04;
    private TextView TV_tmp04;
    private TextView TV_txt05;
    private TextView TV_tmp05;


    private List<Map<String,Object>> list;   //注意是Object 而不是 Objects
    private static final int SET = 1;

    private Handler handler = new android.os.Handler(){  //android.os包下
        public void handleMessage(Message msg) {
            Log.i("Message------>","");
            switch (msg.what) {
                case SET:
                    Show show=new Show();
                    try {
                        Log.i("jsonstring--------->","");
                        List<Map<String, Object>> all = show.result(msg.obj.toString());
                    Iterator<Map<String, Object>> iterator = all.iterator();  //用 Iterator 遍历 List<Map>
                    while (iterator.hasNext()) {
                        Map<String, Object> map = iterator.next();
                        Log.i("tvtvt----111520--->",map.get("city").toString());
                        String s=map.get("city").toString();
                        TV_city.setText(s);
                        TV_tmp.setText(map.get("tmp").toString()+"°");
                        TV_cnty.setText(map.get("cnty").toString());
                        TV_txt.setText(map.get("txt").toString());
                        TV_dir.setText(map.get("dir").toString());
                        TV_sc.setText(map.get("sc").toString()+"级");
                        TV_aqi.setText(map.get("aqi").toString());
                        TV_pm25.setText(map.get("pm25").toString());

                        //未来七天
                        //TV_date.setText(map.get(show.date[0]).toString());
                        TV_txt01.setText(map.get(show.txt01[0]).toString());
                        TV_tmp01.setText(map.get(show.min[0].toString())+" ~ "+map.get(show.max[0].toString())+"°");
                        TV_txt02.setText(map.get(show.txt01[1]).toString());
                        TV_tmp02.setText(map.get(show.min[1].toString())+" ~ "+map.get(show.max[1].toString())+"°");
                        TV_txt03.setText(map.get(show.txt01[2]).toString());
                        TV_tmp03.setText(map.get(show.min[2].toString())+" ~ "+map.get(show.max[2].toString())+"°");
                        TV_txt04.setText(map.get(show.txt01[3]).toString());
                        TV_tmp04.setText(map.get(show.min[3].toString())+" ~ "+map.get(show.max[3].toString())+"°");
                        TV_txt05.setText(map.get(show.txt01[4]).toString());
                        TV_tmp05.setText(map.get(show.min[4].toString())+" ~ "+map.get(show.max[4].toString())+"°");

                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }//catch
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCityName= (EditText) findViewById(R.id.cityName);
        TV_city= (TextView) findViewById(R.id.city);
        TV_tmp= (TextView) findViewById(R.id.tmp);
        TV_cnty= (TextView) findViewById(R.id.cnty);
        TV_txt= (TextView) findViewById(R.id.txt);
        TV_dir= (TextView) findViewById(R.id.dir);
        TV_sc= (TextView) findViewById(R.id.sc);
        TV_aqi= (TextView) findViewById(R.id.aqi);
        TV_pm25= (TextView) findViewById(R.id.pm25);


        //TV_date= (TextView) findViewById(R.id.date);
        TV_txt01= (TextView) findViewById(R.id.txt01);
        TV_tmp01= (TextView) findViewById(R.id.tmp01);
        TV_txt02= (TextView) findViewById(R.id.txt02);
        TV_tmp02= (TextView) findViewById(R.id.tmp02);
        TV_txt03= (TextView) findViewById(R.id.txt03);
        TV_tmp03= (TextView) findViewById(R.id.tmp03);
        TV_txt04= (TextView) findViewById(R.id.txt04);
        TV_tmp04= (TextView) findViewById(R.id.tmp04);
        TV_txt05= (TextView) findViewById(R.id.txt05);
        TV_tmp05= (TextView) findViewById(R.id.tmp05);

        Log.i("jsonResult---------->","11111111");

        String st=getPinYin("吉林jilin");
        Log.i("sdsdsd--pinyin---->",st);
    }


    public void play(View view){  //点击事件
       // jsonString=GetJson.request(httpUrl,httpArg);
        if(mCityName.getText().toString().equals("")){
            Toast.makeText(this,"请输入城市名称",Toast.LENGTH_SHORT).show();
        }else{
            new Thread(new MyThread()).start();  //开启子线程
        }
    }


    class MyThread implements Runnable{ //创建一个线程
        @Override
        public void run() {
                String httpA=null;
                //httpA=httpArg+mCityName.getText().toString();
            String st=getPinYin(mCityName.getText().toString());
            httpA=httpArg+st;  //调用函数将汉字转化为拼音

                Log.i("httpA--------->",httpA);
                String jsonString=GetJson.request(httpUrl,httpA);  //得到jsonString
                Log.i("run------->",jsonString+"");
                //init();  //只在这样测试用
                //list=new Show().result(jsonString); //获取封装好的数据
                Message msg = MainActivity.this.handler
                        .obtainMessage(MainActivity.SET,jsonString);
                MainActivity.this.handler.sendMessage(msg);//传送给主线程信息
        }
    }

     /**
     * 汉字转换拼音，字母原样返回，都转换为小写
     *
     * @param input
     * @return
     */
    public static String getPinYin(String input) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (token.type == HanziToPinyin.Token.PINYIN) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

   /* public void init() throws JSONException {  //访问网络必须卸载子线程里面。否则会出现 NetworkOnMainThreadException 异常
        JSONObject jsonObject;
        jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
        jsonObject = jsonArray.getJSONObject(0);
        String str=jsonObject.getJSONObject("now").getJSONObject("cond").getString("txt");
        Log.i("scscmsl------>",str);
        Log.i("JsonString------->",jsonString);  //得到Json数据
    }//init
*/
}
