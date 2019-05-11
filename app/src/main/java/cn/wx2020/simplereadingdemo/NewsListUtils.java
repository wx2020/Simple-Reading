package cn.wx2020.simplereadingdemo;

import android.util.Log;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class NewsListUtils {
    static String lastTimeStr;
    public static List<News> getIthomeList(String response){
        List<News> newsList;
        newsList = new ArrayList<>();
        List<IthomeJson.Results> ithomeList = new ArrayList<>();
        IthomeJson ithome = new Gson().fromJson(response, IthomeJson.class);
        Collections.addAll(ithomeList, ithome.Result);

        lastTimeStr = ithomeList.get(ithomeList.size() - 1).orderdate;

        for(IthomeJson.Results result : ithomeList){
            News news = new News();
            news.setId(result.newsid);
            news.setTitle(result.title);
            news.setTitleImgUrl(result.image);
            news.setNewsUrl(result.WapNewsUrl);
            news.setDescription(result.description);
            news.setTime(TimeUtils.getTimeDifferenceWithFormatter(ISO_LOCAL_DATE_TIME,result.orderdate));
            newsList.add(news);
        }

        return newsList;
    }

    public static Long getLastTime(){

        Log.d("Ithome", "getLastTime: " + lastTimeStr);
        DateTimeFormatter formatter = ISO_LOCAL_DATE_TIME;
        LocalDateTime publishTime = LocalDateTime.parse(lastTimeStr, formatter);

        return LocalDateTime.from(publishTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
