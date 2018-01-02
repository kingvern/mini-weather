package cn.edu.pku.kingvern.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.pku.kingvern.bean.TodayWeather;
import cn.edu.pku.kingvern.util.NetUtil;




/**
 * Created by kingvern on 9/21/17.
 */


public class MainActivity extends Activity implements View.OnClickListener{

    public static String cityCode = "";

    private static final int UPDATE_TODAY_WEATHER = 1;
    private ImageView mUpdateBtn;
    private TextView cityTv, timeTv, humidityTv, weekTv, pmDataTv, pmQualityTv,
            temperatureTv, temperatureNowTv, climateTv, windTv, city_name_Tv;
    private ImageView weatherImg, pmImg,mCitySelect;

    private boolean isRunning = false;

    private HorizontalListView mlist;



//    List<FutureWeather> listFutureWeather;

    SimpleAdapter simplead;
    List<Map<String, String>> listems;

//    SimpleAdapter fAdapter;
//    List<Map<String, String>> fList;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);//更新数据
//                    updateFutureWeather(TodayWeather);
//                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);

        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);
        listems =  new ArrayList<Map<String, String>>();;


        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("myWeather", "网络OK");
            Toast.makeText(MainActivity.this,"网络OK!", Toast.LENGTH_LONG).show();
        }else
        {
            Log.d("myWeather", "网络挂了");
            Toast.makeText(MainActivity.this,"网络挂了!", Toast.LENGTH_LONG).show();
        }

        mCitySelect=(ImageView)findViewById(R.id.title_city_manager);
        mCitySelect.setOnClickListener(this);

        initView();//初始化

        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        cityCode = sharedPreferences.getString("main_city_code","101010100");
        Log.d("myWeather",cityCode);

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("myWeather", "网络OK");
            Log.d("myWeather11", "beijing101010100");
//            queryWeatherCode("101010100");
            queryWeatherCode(cityCode);//开始查询
        }else
        {
            Log.d("myWeather", "网络挂了");
            Toast.makeText(MainActivity.this,"网络挂了！",Toast.LENGTH_LONG).show();
        }

        startService(new Intent(this,WeatherService.class));
        isRunning = true;
        TimeTickLoad timetick = new TimeTickLoad();
        timetick.execute();


    }//onCreate END

    void initView(){
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);
        temperatureNowTv = (TextView) findViewById(R.id.temperature_now);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);

        mlist = (HorizontalListView)findViewById(R.id.weather_future_list);

        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureNowTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");

        for (int i = 0; i < 6; i++) {
            Map<String, String> listem = new HashMap<String, String>();
            listem.put("date", "N/A");
            listem.put("high", "N/A");
            listem.put("type", "N/A");
            listem.put("fengli", "N/A");
            listems.add(listem);
        }
        simplead = new SimpleAdapter(this, listems,
                R.layout.future_item, new String[] { "date", "high","type","fengli"},
                new int[] {R.id.week_future,R.id.temperature_future,R.id.climate_future,R.id.wind_future});
        mlist.setAdapter(simplead);
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.title_city_manager){
            Intent i = new Intent(this, SelectCity.class);
            startActivityForResult(i,1);
            // startActivity(i);
        }

        if (view.getId() == R.id.title_update_btn){

            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String cityCode = sharedPreferences.getString("main_city_code","101010100");
            Log.d("myWeather",cityCode);


            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络OK");
                queryWeatherCode(cityCode);
            }else
            {
                Log.d("myWeather", "网络挂了");
                Toast.makeText(MainActivity.this,"网络挂了！",Toast.LENGTH_LONG).show();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newCityCode= data.getStringExtra("cityCode");
            cityCode = newCityCode;
            Log.d("myWeather", "选择的城市代码为"+newCityCode);

            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络OK");
                queryWeatherCode(newCityCode);
                SharedPreferences settings = (SharedPreferences)getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("main_city_code", newCityCode);
                editor.commit();
            } else {
                Log.d("myWeather", "网络挂了");
                Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
            }

        }
    }
    void updateTodayWeather(TodayWeather todayWeather){//更新数据，过程中会反映到UI
        city_name_Tv.setText(todayWeather.getCity()+"天气");
        cityTv.setText(todayWeather.getCity());
        timeTv.setText(todayWeather.getUpdatetime()+ "发布");
        humidityTv.setText("湿度："+todayWeather.getShidu());

        pmDataTv.setText(todayWeather.getPm25());
        pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText(todayWeather.getFdate()[0]);
        temperatureTv.setText(todayWeather.getFhigh()[0]+"~"+todayWeather.getFlow()[0]);
        temperatureNowTv.setText("温度："+todayWeather.getWendu()+"℃");
        climateTv.setText(todayWeather.getFtype()[0]);

        windTv.setText("风力:"+todayWeather.getFengli());

        listems.clear();
        int i = 0;
        while ( todayWeather.getFhigh()[i] != null){
            Map<String, String> listem = new HashMap<String, String>();
            listem.put("date", todayWeather.getFdate()[i]);
            listem.put("high", todayWeather.getFhigh()[i]+"~"+todayWeather.getFlow()[i]);
            listem.put("type", todayWeather.getFtype()[i]);
            listem.put("fengli", todayWeather.getFfengxiang()[i]+todayWeather.getFfengli()[i]);
            listems.add(listem);
            i++;
        }
        simplead.notifyDataSetChanged();
        Toast.makeText(MainActivity.this,"更新成功！",Toast.LENGTH_SHORT).show();


    }


    private void queryWeatherCode(String cityCode)  {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;
        Log.d("myWeather111", address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con=null;
                TodayWeather todayWeather=null;
                try{
                    URL url = new URL(address);
                    con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while((str=reader.readLine()) != null){
                        response.append(str);
                        Log.d("myWeather", str);
                    }
                    String responseStr=response.toString();
                    Log.d("myWeather", responseStr);

                    todayWeather=(TodayWeather) parseXML(responseStr);//查询过程中，会格式化数据
                    if(todayWeather!=null){
                        Log.d("myWeather", todayWeather.toString());
                        Message msg =new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj=todayWeather;
                        mHandler.sendMessage(msg);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(con != null){
                        con.disconnect();
                    }
                }
            }
        }).start();
    }

    private TodayWeather parseXML(String xmldata){
//        Map map = new HashMap();
        TodayWeather todayWeather = null;

        String[] ffengxiang = new String[15];
        String[] ffengli = new String[15];
        String[] fdate = new String[15];
        String[] fhigh = new String[15];
        String[] flow = new String[15];
        String[] ftype = new String[15];
        int fengxiangCount=0;
        int fengliCount =0;
        int dateCount=0;
        int highCount =0;
        int lowCount=0;
        int typeCount =0;
        int weatherCount =0;
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = fac.newPullParser();
            xmlPullParser.setInput(new StringReader(xmldata));
            int eventType = xmlPullParser.getEventType();
            Log.d("myWeather", "parseXML");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    // 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    // 判断当前事件是否为标签元素开始事件
                    case XmlPullParser.START_TAG:
                        if(xmlPullParser.getName().equals("resp")){
                            todayWeather= new TodayWeather();
                        }
                        if (todayWeather != null) {
                            if (xmlPullParser.getName().equals("city")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setCity(xmlPullParser.getText());

                            } else if (xmlPullParser.getName().equals("updatetime")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setUpdatetime(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("shidu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setShidu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setWendu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("pm25")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setPm25(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("quality")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setQuality(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengxiang(xmlPullParser.getText());
                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("fengli") && fengliCount == 0) {//
                                eventType = xmlPullParser.next();
                                todayWeather.setFengli(xmlPullParser.getText());

                                fengliCount++;
                            } else if (xmlPullParser.getName().contains("date") ) {//
                                eventType = xmlPullParser.next();
//                                fdate[dateCount]=xmlPullParser.getText();
                                Log.d("date", xmlPullParser.getName()+"|||"+dateCount+": "+xmlPullParser.getText());
//                                todayWeather.setDate(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 0) {//
                                eventType = xmlPullParser.next();
                                todayWeather.setHigh(xmlPullParser.getText().substring(2).trim());
                                fhigh[highCount]=xmlPullParser.getText().substring(2).trim();
                                highCount++;
                            }
//                            else if (xmlPullParser.getName().equals("low") && lowCount == 0) {//
//                                eventType = xmlPullParser.next();
//                                todayWeather.setLow(xmlPullParser.getText().substring(2).trim());
//                                lowCount++;
//                            } else if (xmlPullParser.getName().equals("type") && typeCount == 0) {//
//                                eventType = xmlPullParser.next();
//                                todayWeather.setType(xmlPullParser.getText());
//                                typeCount++;
//                            }
                            else if (xmlPullParser.getName().equals("weather")) {
                                eventType = xmlPullParser.next();
                                weatherCount++;
                            }else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == weatherCount) {
                                eventType = xmlPullParser.next();
//                                listem.put("fengxiang",xmlPullParser.getText());
                                ffengxiang[fengxiangCount-1]=xmlPullParser.getText();
                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("fengli") && fengliCount == weatherCount) {
                                eventType = xmlPullParser.next();
                                ffengli[fengliCount-1]=xmlPullParser.getText();
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("high") ) {
                                Log.d("high", xmlPullParser.getName()+"|||"+dateCount+": "+xmlPullParser.getText());
//
                                eventType = xmlPullParser.next();
                                fhigh[highCount]=xmlPullParser.getText().substring(2).trim();
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") ) {
                                eventType = xmlPullParser.next();
                                flow[lowCount]=xmlPullParser.getText().substring(2).trim();
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") ) {
                                eventType = xmlPullParser.next();
                                ftype[typeCount]=xmlPullParser.getText();
                                typeCount++;
                            }
                        }

                        break;

                    // 判断当前事件是否为标签元素结束事件
                    case XmlPullParser.END_TAG:
                        break;
                }
                // 进入下一个元素并触发相应事件
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        todayWeather.setFdate(fdate);
        todayWeather.setFfengli(ffengli);
        todayWeather.setFfengxiang(ffengxiang);
        todayWeather.setFhigh(fhigh);
        todayWeather.setFlow(flow);
        todayWeather.setFtype(ftype);

        return todayWeather;
    }


    private class TimeTickLoad extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            while (isRunning){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);

            queryWeatherCode(cityCode);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

}