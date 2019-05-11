package cn.wx2020.simplereadingdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<News> newsList;
    private OnItemClickListener mOnItemClickListener = null;
/*
    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View
    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
*/
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView titleImage;
        TextView title;
        TextView description;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView;
            this.titleImage = itemView.findViewById(R.id.title_image);
            this.title = itemView.findViewById(R.id.title);
            this.description = itemView.findViewById(R.id.description);
            this.time = itemView.findViewById(R.id.time);
        }
    }
    /*
    static class FootHolder  extends RecyclerView.ViewHolder{
        TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = itemView.findViewById(R.id.tips);
        }
    }*/



    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (context == null){
            context = viewGroup.getContext();
        }
        view = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);

    }

    /*@Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }*/

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        News news = newsList.get(i);
        viewHolder.title.setText(news.getTitle());
        viewHolder.description.setText(news.getDescription());
        viewHolder.time.setText(news.getTime());
        Glide.with(context).load(news.getTitleImgUrl()).into(viewHolder.titleImage);
        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    public int getRealLastPosition() {
        return newsList.size();
    }

    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    /*@Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }*/


    public void setData(List<News> newsList){
        this.newsList = newsList;
    }



    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
