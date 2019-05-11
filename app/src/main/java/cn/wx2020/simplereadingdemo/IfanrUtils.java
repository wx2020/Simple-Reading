package cn.wx2020.simplereadingdemo;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class IfanrUtils {
    public static List<News> getNewsList(String response){
        List<News> newsList = new ArrayList<>();
        IfanrJson ifanr = new Gson().fromJson(response, IfanrJson.class);

        for(IfanrJson.Objects result : ifanr.objects){
            News news = new News();
            news.setTitle(result.post_title);
            news.setTitleImgUrl(result.post_cover_image);
            news.setDescription(result.post_excerpt);
            news.setTime(TimeUtils.getTimeDifferenceFromUnixTimeWithPattern("uuuu-MM-dd HH:mm:ss", result.created_at));
            newsList.add(news);
        }
        return newsList;
    }

}
