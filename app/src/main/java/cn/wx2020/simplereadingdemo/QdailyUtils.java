package cn.wx2020.simplereadingdemo;

import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.List;

public class QdailyUtils {
    public static List<News> getNewsList(String response){
        List<News> newsList = new ArrayList<>();
        QdailyJson qdaily = new Gson().fromJson(response, QdailyJson.class);

        for(QdailyJson.Data.Feeds result : qdaily.data.feeds){
            News news = new News();
            news.setId(result.post.id);
            news.setTitle(result.post.title);
            news.setTitleImgUrl(result.image);
            news.setDescription(result.post.description);
            news.setTime(TimeUtils.getTimeDifferenceWithPattern("uuuu-MM-dd HH:mm:ss Z", result.post.publish_time));
            newsList.add(news);
        }
        return newsList;
    }
}
