package com.example.order;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//整个界面设置为一个监听器
public  class weather extends AppCompatActivity implements View.OnClickListener {
    private TextView tvCity;
    private TextView tvWeather;
    private TextView tvTemp;
    private TextView tvWind;
    private TextView tvPm;
    private ImageView ivIcon;
    private ListView my_lv;
    private EditText et_v;
    private Map<String,String> map;
    private List<Map<String,String>> list;
    private String temp,weather,name,pm,wind;
    MyDB m_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        m_db = new MyDB(this,"db.db");
        initView();//对控件初始化以及对控件设置监听
        display();//用于展示注册的城市信息以及进行城市天气的切换
        List <String> ls = getInformation(m_db,et_v.getText().toString());
        disData(ls);
    }
    private void disData(List<String> ls){
        List<String> new_ls = new ArrayList<String>();
        for(String lt:ls){
            new_ls.add(lt.substring(lt.indexOf(" ")+1));
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new_ls);
        my_lv.setAdapter(adp);
        AdapterView.OnItemClickListener lvClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(com.example.order.weather.this,food.class);
                String cts = ls.get(i);
                it.putExtra("infs",ls.get(i));
                startActivity(it);
            }
        };
        my_lv.setOnItemClickListener(lvClickListener);
    }
    private List<String> getInformation(MyDB db,String qr){
        SQLiteDatabase m_db;
        m_db = db.getReadableDatabase();
        List<String> allInformation=new ArrayList<String>();
        if(qr!=null){
            Cursor cs1 = m_db.rawQuery("select _id,name,type,price,quantity from information where name like ? or type like ? or price like ? or quantity like ?",new String[]{"%"+qr+"%","%"+qr+"%","%"+qr+"%","%"+qr+"%"});

            if(cs1.getCount()<1)
                Toast.makeText(com.example.order.weather.this,"暂无数据",Toast.LENGTH_SHORT).show();
            else {
                cs1.moveToFirst();
                allInformation.add(cs1.getString(0) + " " + cs1.getString(1) + " " + cs1.getString(2) + " " + cs1.getString(3) + " " + cs1.getString(4));
                while (cs1.moveToNext()) {
                    allInformation.add(cs1.getString(0) + " " + cs1.getString(1) + " " + cs1.getString(2) + " " + cs1.getString(3) + " " + cs1.getString(4));
                }
                cs1.close();
            }
        }else{
            Cursor cs = m_db.query("information", null, null, null, null, null, null);
                if(cs.getCount()<1)
                    Toast.makeText(com.example.order.weather.this,"暂无数据",Toast.LENGTH_SHORT).show();
                else{
                    cs.moveToFirst();
                    allInformation.add(cs.getString(0)+" "+cs.getString(1)+" "+cs.getString(2)+" "+cs.getString(3)+" "+cs.getString(4));
                    while (cs.moveToNext()){
                        allInformation.add(cs.getString(0)+" "+cs.getString(1)+" "+cs.getString(2)+" "+cs.getString(3)+" "+cs.getString(4));
                    }
                    cs.close();
        }

        }
        return allInformation;
    }
    private void display(){
        //用sharedPreferences取出city数据,用于登录显示
        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        String city = sp.getString("city","");
        try {
//            //读取weather1.xml文件
//            InputStream is = this.getResources().openRawResource(R.raw.weather);
//            //把每个城市的天气信息集合存到weatherInfos中
//            List<WeatherInfo> weatherInfos = WeatherService.getInfosFromXML(is);
//            //循环读取weatherInfos中的每一条数据
            InputStream is = this.getResources().openRawResource(R.raw.weather1);
            List<WeatherInfo> weatherInfos = WeatherService.getInfosFromJson(is);
            list = new ArrayList<Map<String,String>>();
            for (WeatherInfo info : weatherInfos){
                map = new HashMap<String,String>();
                map.put("temp",info.getTemp());
                map.put("weather",info.getWeather());
                map.put("name",info.getName());
                map.put("pm",info.getPm());
                map.put("wind",info.getWind());
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();//在命令行打印错误信息
            Toast.makeText(this,"解析信息失败",Toast.LENGTH_LONG).show();
        }
        switch (city){
            case "上海":getMap(0,R.drawable.cloud_sun);break;//用于展示城市信息
            case "北京":getMap(1,R.drawable.sun);break;
            case "广州":getMap(2,R.drawable.clouds);break;
            case "成都":getMap(3,R.drawable.sun);break;
            default :getMap(0,R.drawable.cloud_sun);
        }

    }
    private void initView(){
        tvCity = findViewById(R.id.tv_city);
        tvWeather = findViewById(R.id.tv_weather);
        tvTemp = findViewById(R.id.tv_temp);
        tvWind = findViewById(R.id.tv_wind);
        tvPm = findViewById(R.id.tv_pm);
        ivIcon = findViewById(R.id.imageView);
        my_lv = (ListView) findViewById(R.id.lv);
        et_v = findViewById(R.id.et_v);
        findViewById(R.id.bt_bj).setOnClickListener(this);
        findViewById(R.id.bt_sh).setOnClickListener(this);
        findViewById(R.id.bt_gz).setOnClickListener(this);
        findViewById(R.id.bt_cd).setOnClickListener(this);
        findViewById(R.id.insert).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_sh:
                getMap(0,R.drawable.cloud_sun);
                break;
            case R.id.bt_bj:
                getMap(1,R.drawable.sun);
                break;
            case R.id.bt_gz:
                getMap(2,R.drawable.clouds);
                break;
            case R.id.bt_cd:
                getMap(3,R.drawable.sun);
                break;
            case R.id.search:
                List <String> ls = getInformation(m_db,et_v.getText().toString());
                disData(ls);
                break;
            case R.id.insert:
                Intent it = new Intent(com.example.order.weather.this,food.class);//注意要对button按钮设置监听
                it.putExtra("infs","");//该条语句解决了添加信息与点击listView修改信息的矛盾
                startActivity(it);
                break;
        }
    }
    //将城市天气信息分条展示到界面上
    private void getMap(int number,int iconNumber){
        Map<String,String> cityMap = list.get(number);
        temp = cityMap.get("temp");
        weather = cityMap.get("weather");
        name = cityMap.get("name");
        pm = cityMap.get("pm");
        wind = cityMap.get("wind");
        tvCity.setText(name);
        tvWeather.setText(weather);
        tvTemp.setText(""+temp);
        tvWind.setText("风力 :"+wind);
        tvPm.setText("pm: "+pm);
        ivIcon.setImageResource(iconNumber);
    }
}