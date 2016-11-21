package com.lin.weather;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {


    private String httpUrl = "http://apis.baidu.com/heweather/weather/free";
    private String httpArg = "city=";
    private String jsonString=null;
    private EditText mCityName;
    private TextView TV_city;
    private List<Map<String,Object>> list;   //注意是Object 而不是 Objects
    private static final int SET = 1;

    private android.os.Handler handler = new android.os.Handler(){  //android.os包下
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET:
                    Show show=new Show();
                    List<Map<String, Object>> all = null;
                    try {
                    all = show.result(msg.obj.toString());
                    Iterator<Map<String, Object>> iterator = all.iterator();  //用 Iterator 遍历 List<Map>
                    while (iterator.hasNext()) {
                        Map<String, Object> map = iterator.next();
                        Log.i("tvtvt----111520--->",map.get("city").toString());
                        TV_city.setText(map.get("city").toString());
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

        //jsonResult=request("http://apis.baidu.com/heweather/weather/free ? city=beijing");
        Log.i("jsonResult---------->","11111111");

    }

    public void play(View view){  //点击事件
       // jsonString=GetJson.request(httpUrl,httpArg);
        if(mCityName.getText().toString().equals("")){
            Toast.makeText(this,"请输入城市名称",Toast.LENGTH_SHORT).show();
        }else{
            new Thread(new MyThread()).start();
        }
    }


    class MyThread implements Runnable{ //创建一个线程
        @Override
        public void run() {
            try {
                String httpA=null;
                httpA=httpArg+mCityName.getText().toString();
                Log.i("httpA--------->",httpA);
                jsonString=GetJson.request(httpUrl,httpA);
                Log.i("run------->",jsonString);
                init();
                //list=new Show().result(jsonString); //获取封装好的数据
                Message msg = MainActivity.this.handler
                        .obtainMessage(MainActivity.SET,jsonString);
                MainActivity.this.handler.sendMessage(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void init() throws JSONException {  //访问网络必须卸载子线程里面。否则会出现 NetworkOnMainThreadException 异常
        JSONObject jsonObject;
        jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
        jsonObject = jsonArray.getJSONObject(0);
        String str=jsonObject.getJSONObject("now").getJSONObject("cond").getString("txt");
        Log.i("scscmsl------>",str);
        Log.i("JsonString------->",jsonString);  //得到Json数据
    }//init

}
