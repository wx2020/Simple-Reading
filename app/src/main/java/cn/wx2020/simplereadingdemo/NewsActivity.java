package cn.wx2020.simplereadingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.xml.sax.XMLReader;

public class NewsActivity extends AppCompatActivity {
    private TextView content;
    private News news;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        news = (News) intent.getSerializableExtra("news");
        Log.d("NewsActivity", "onCreate: " + news.toString());
        title = findViewById(R.id.title);
        title.setText(news.getTitle());
        content = findViewById(R.id.content);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        String url = intent.getStringExtra("url") + news.getId();


        RequestQueue mQueue = Volley.newRequestQueue(NewsActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        content.setMovementMethod(ScrollingMovementMethod.getInstance());
                        NewsJson.Dataes newsJson = new Gson().fromJson(response.toString(), NewsJson.Dataes.class);
                        Spanned result = Html.fromHtml(newsJson.contentshtml, Html.FROM_HTML_MODE_LEGACY, new MyImageGetter(content), new Html.TagHandler() {
                            @Override
                            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

                            }
                        });
                        content.setText(result);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(jsonObjectRequest);
    }

    class MyImageGetter implements Html.ImageGetter {
        private TextView textView;
        private int actX;//实际的宽  放大缩小基于textview的宽度
        private int actY;
        public MyImageGetter(TextView textView) {
            this.textView = textView;
            DisplayMetrics metrics = NewsActivity.this.getResources().getDisplayMetrics();
            //我的textview有左右留边  margin
            actX = metrics.widthPixels - 20;
            actY = metrics.heightPixels;
        }

        @Override
        public Drawable getDrawable(String source) {
            //Drawable urlDrawable = new BitmapDrawable();
            URLDrawable urlDrawable = new URLDrawable();
            Glide.with(NewsActivity.this)
                    .asBitmap()
                    .load(source)
                    .into(new BitmapTarget(urlDrawable, actX, actY));
            return urlDrawable;
        }
    }

    public class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }

    class BitmapTarget extends SimpleTarget<Bitmap> {
        private final URLDrawable urlDrawable;
        private int actX;//实际的宽  放大缩小基于textview的宽度
        private int actY;
        public BitmapTarget(URLDrawable urlDrawable, int actX, int actY){
            this.urlDrawable = urlDrawable;
            this.actX = actX;
            this.actY = actY;
        }


        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {

        }

        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            int x = resource.getWidth();
            int y = resource.getHeight();
            if (x < actX || y < actY) {
                //进行等比例缩放程序
                Matrix matrix = new Matrix();
                matrix.postScale((float) (actX * 1.00 / x), (float) (actX * 1.00 / x));
                //长和宽放大缩小的比例
                resource = Bitmap.createBitmap(resource, 0, 0, x, y, matrix, true);
            }
            urlDrawable.bitmap = resource;
            urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
            content.invalidate();
            content.setText(content.getText());
        }
    }
}
