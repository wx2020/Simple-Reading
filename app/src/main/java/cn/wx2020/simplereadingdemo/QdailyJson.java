package cn.wx2020.simplereadingdemo;

public class QdailyJson {

    public Data data;

    public class Data{

        public Feeds[] feeds;

        /*public Long last_key;
        public boolean has_more;*/

        public class Feeds{
            public Post post;
            public String image;

            public class Post{
                public String id;
                public String title;
                public String description;
                public String publish_time;
            }

        }

    }

}
