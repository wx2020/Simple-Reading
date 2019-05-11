package cn.wx2020.simplereadingdemo;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class NewsUrl {
    static String ithomeurl = "https://m.ithome.com/api/news/newslistpageget?Tag=";

    static class Ithome{
        public static String NEWEST = ithomeurl + "&ot=";
        public static String WINDOWS = ithomeurl + "windowsm&ot=";
        public static String JINGDU = ithomeurl + "jingdum&ot=";
        public static String ORIGINAL = ithomeurl + "originalm&ot=";
        public static String LAB = ithomeurl + "labsm&ot=";

        public static String getUrlWithNowUnixTime(String url){
            return url + System.currentTimeMillis();
        }

        public static String getUrlWithUnixTime(String url, Long time){
            return url + time.toString();
        }
    }

    static String qdailyUrl = "https://www.qdaily.com/";
    static class Qdaily{
        public static String NEWEST = qdailyUrl + "homes/articlemore/";
        public static String getUrlWithUnixTime(String url){
            return url + System.currentTimeMillis() + ".json";
        }

    }

    static String ifanrUrl = "https://sso.ifanr.com/api/v5/wp/web-feed/?limit=100&offset=0&published_at__lte=";
    static class Ifanr {
        public static String getUrlWithTime(String url) {
            SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
            return url + time.format(new Date());
        }
    }

    static String apiurl = "https://api.news.wx2020.fun/";
    static class Api{
        public static String Ithome = apiurl + "ithome/";
        public static String Qdaily = apiurl + "qdaily/";
        public static String ifanr = apiurl + "ifanr/";
    }

}
