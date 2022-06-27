package com.example.order;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class WeatherService {
    //解析json文件返回天气信息的集合
    public static List<WeatherInfo> getInfosFromJson(InputStream is) throws IOException{
        byte []buffer = new byte[is.available()];
        is.read(buffer);
        String json = new String(buffer,"utf-8");
        //使用Gson库解析JSON数据
        Gson gson = new Gson();
        Type listType = new TypeToken<List<WeatherInfo>>(){}.getType();
        List<WeatherInfo> weatherInfos = gson.fromJson(json,listType);
        return weatherInfos;
    }
        //    //解析xml文件返回的天气信息的集合
//    public static List<WeatherInfo> getInfosFromXML(InputStream is) throws Exception{
//        //得到pull解析器
//        XmlPullParser parser = Xml.newPullParser();
//        //初始化解析器,第一个参数代表包含xml的数据
//        parser.setInput(is,"utf-8");
//        List<WeatherInfo> weatherInfos = new ArrayList<>();//注意此行等号右边只能是new ArrayList<>(),不能是null;
//        WeatherInfo weatherInfo = null;
//        //得到当前事件的类型
//        int type = parser.getEventType();
//        //END_DOCUMENT文档结束标签
//        while (type != XmlPullParser.END_DOCUMENT)
//        {
//            switch (type)
//            {
//                //一个节点的开始标签
//                case XmlPullParser.START_TAG:
//                    //解析到全局开始的标签infos根节点
//                    if("info".equals(parser.getName())){
//                        weatherInfos = new ArrayList<WeatherInfo>();
//                    }else if("city".equals(parser.getName())){
//                        weatherInfo = new WeatherInfo();
//                        String idStr = parser.getAttributeValue(0);
//                        weatherInfo.setId(idStr);
//                    }else if("temp".equals(parser.getName())){
//                        //parset.nextText()得到该tag节点中的内容
//                        String temp = parser.nextText();
//                        weatherInfo.setTemp(temp);
//                    }else if("weather".equals(parser.getName())){
//                        String weather = parser.nextText();
//                        weatherInfo.setWeather(weather);
//                    }else if("name".equals(parser.getName())){
//                        String name = parser.nextText();
//                        weatherInfo.setName(name);
//                    }else if("pm".equals(parser.getName())){
//                        String pm = parser.nextText();
//                        weatherInfo.setPm(pm);
//                    }else if("wind".equals(parser.getName())){
//                        String wind = parser.nextText();
//                        weatherInfo.setWind(wind);
//                    }
//                    break;
//                    //一个节点结束的标签
//                case XmlPullParser.END_TAG:
//                    //一个城市的信息处理完毕，city的结束标签
//                    if("city".equals(parser.getName())){
//                        weatherInfos.add(weatherInfo);
//                        weatherInfo = null;
//                    }
//                    break;
//            }
//            type = parser.next();
//        }
//        return weatherInfos;
//    }
}
