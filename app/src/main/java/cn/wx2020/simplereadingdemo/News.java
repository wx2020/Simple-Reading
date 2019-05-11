package cn.wx2020.simplereadingdemo;

import java.io.Serializable;

public class News implements Serializable {
    private String id;
    private String title;
    private String titleImgUrl;
    private String newsUrl;
    private String description;
    private String time;

    public News() {
    }

    public News(String id, String title, String titleImgUrl, String newsUrl, String description, String time) {
        this.id = id;
        this.title = title;
        this.titleImgUrl = titleImgUrl;
        this.newsUrl = newsUrl;
        this.description = description;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImgUrl() {
        return titleImgUrl;
    }

    public void setTitleImgUrl(String titleImgUrl) {
        this.titleImgUrl = titleImgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", titleImgUrl='" + titleImgUrl + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
