package cn.wx2020.simplereadingdemo;

import cn.wx2020.simplereadingdemo.NewsJsonUtils.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private NewsAdapter newsAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar = null;
    String TAG = "MainActivity";
    private RequestQueue mQueue ;
    List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        //设定一个灵活的标题栏Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.news_ithome);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));


        // 初始化刷新进度条
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.colorPrimary));

        mQueue = Volley.newRequestQueue(this);

        getIthome();



        //设定一个悬浮按钮
        FloatingActionButton fab = findViewById(R.id.fab);

        //设定点击该按钮操作
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //触发一个Snackbar通知，带按钮
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Wow", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        //设定一个滑动菜单
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //设定滑动菜单的定制布局
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        //设定菜单项选中监听器
        navigationView.setCheckedItem(R.id.nav_ithome);

        navigationView.setNavigationItemSelectedListener(this);


        //菜单栏+标题栏相关
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // 初始化新闻数据

        /*recyclerView.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView2, int newState) {
                Long lastTime =  NewsListUtils.getLastTime();
                Log.d(TAG, "onSuccess: " + lastTime);
                super.onScrollStateChanged(recyclerView2, newState);
                // 在newState为滑到底部时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    NewsJsonUtils.initNewsJson(mQueue,NewsUrl.Ithome.getUrlWithUnixTime(NewsUrl.Ithome.NEWEST, lastTime), new VolleyCallback() {

                        @Override
                        public void onSuccess(JSONObject result) {
                            newsList.addAll(NewsListUtils.getIthomeList(result.toString()));
                            newsAdapter.setData(newsList);
                            newsAdapter.notifyDataSetChanged();
                        }
                    });
                }

            }
        });*/



    }


    private void getIthome(){
        NewsJsonUtils.initNewsJson(mQueue,NewsUrl.Ithome.getUrlWithNowUnixTime(NewsUrl.Ithome.NEWEST), new VolleyCallback(){
            @Override
            public void onSuccess(JSONObject result) {
                newsList = NewsListUtils.getIthomeList(result.toString());
                newsAdapter = new NewsAdapter(newsList);
                recyclerView.setAdapter(newsAdapter);

                newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view , int position){
                        Intent newActivity = new Intent(MainActivity.this, NewsActivity.class);
                        newActivity.putExtra("news", newsList.get(position));
                        newActivity.putExtra("position", position);
                        newActivity.putExtra("url", NewsUrl.Api.Ithome);
                        startActivity(newActivity);
                    }
                });
                toolbar.setTitle(R.string.news_ithome);



            }
        });

        // 下拉刷新新闻数据
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewsJsonUtils.refreshNewsJson(mQueue, NewsUrl.Ithome.getUrlWithNowUnixTime(NewsUrl.Ithome.NEWEST), //MainActivity.this,
                        new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                newsList = NewsListUtils.getIthomeList(result.toString());
                                newsAdapter.setData(newsList);
                                newsAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                                // 代码都一样，复用一下
                                newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(View view , int position){
                                        Intent newActivity = new Intent(MainActivity.this, NewsActivity.class);
                                        newActivity.putExtra("news", newsList.get(position));
                                        newActivity.putExtra("position", position);
                                        newActivity.putExtra("url", NewsUrl.Api.Ithome);
                                        startActivity(newActivity);
                                    }
                                });
                            }
                        });
            }
        });

    }

    private void getQdaily() {
        NewsJsonUtils.initNewsJson(mQueue, NewsUrl.Qdaily.getUrlWithUnixTime(NewsUrl.Qdaily.NEWEST),
                new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        newsList = QdailyUtils.getNewsList(result.toString());
                        newsAdapter.setData(newsList);
                        newsAdapter.notifyDataSetChanged();
                        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener(){
                            @Override
                            public void onItemClick(View view , int position){
                                Intent newActivity = new Intent(MainActivity.this, NewsActivity.class);
                                newActivity.putExtra("news", newsList.get(position));
                                Log.d(TAG, "onItemClick: " + newsList.get(position).toString());
                                newActivity.putExtra("position", position);
                                newActivity.putExtra("url", NewsUrl.Api.Qdaily);
                                startActivity(newActivity);
                            }
                        });
                        toolbar.setTitle(R.string.news_qdaily);
                    }
                });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewsJsonUtils.refreshNewsJson(mQueue, NewsUrl.Qdaily.getUrlWithUnixTime(NewsUrl.Qdaily.NEWEST), //MainActivity.this,
                        new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                newsAdapter.setData(QdailyUtils.getNewsList(result.toString()));
                                newsAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                                newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(View view , int position){
                                        Intent newActivity = new Intent(MainActivity.this, NewsActivity.class);
                                        newActivity.putExtra("news", newsList.get(position));
                                        newActivity.putExtra("position", position);
                                        newActivity.putExtra("url", NewsUrl.Api.Qdaily);
                                        startActivity(newActivity);
                                    }
                                });
                            }
                        });
            }
        });
    }

        private void getIfanr() {
            NewsJsonUtils.initNewsJson(mQueue, NewsUrl.Ifanr.getUrlWithTime(NewsUrl.ifanrUrl),
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            newsAdapter.setData(IfanrUtils.getNewsList(result.toString()));
                            newsAdapter.notifyDataSetChanged();
                            toolbar.setTitle(R.string.news_aifanr);
                        }
                    });
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    NewsJsonUtils.refreshNewsJson(mQueue, NewsUrl.Ifanr.getUrlWithTime(NewsUrl.ifanrUrl), //MainActivity.this,
                            new VolleyCallback() {
                                @Override
                                public void onSuccess(JSONObject result) {
                                    newsAdapter.setData(IfanrUtils.getNewsList(result.toString()));
                                    newsAdapter.notifyDataSetChanged();
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                }
            });
    }


    // 后退键触发
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 创建选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 处理菜单被选中运行后的事件处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ithome) {
            getIthome();
            // Handle the camera action
        } else if (id == R.id.nav_qdaily) {
            getQdaily();
        } else if (id == R.id.nav_aifanr) {
            getIfanr();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}


