package com.example.berita_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.berita_app.Favorite.FavoriteDatabase;
import com.example.berita_app.Favorite.FavoriteList;
import com.example.berita_app.models.Article;
import com.example.berita_app.models.Source;

import java.util.List;

public class Adapter extends RecyclerView.Adapter <Adapter.MyViewHolder> {

    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private FavoriteDatabase database;
    private int title;


    public Adapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Article model = articles.get(position);
        FavoriteList favoriteList = new FavoriteList();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageview);

        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
        holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(model.getPublishedAt()));
        holder.publishedAt.setText(Utils.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());



//        database = Room.databaseBuilder(context, FavoriteDatabase.class, "Favoritedb").build();
//        if(MainActivity.database.favoriteDao().isFavorite(model.getId()) == 1){
//            holder.fav_btn.setImageResource(R.drawable.ic_fav);
//        }else {
//            holder.fav_btn.setImageResource(R.drawable.ic_fav_border);
//            holder.fav_btn.setOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View view) {
//
//                    int id = model.getId();
//                    String author = model.getAuthor();
//                    String title = model.getTitle();
//                    String description = model.getDescription();
//                    String url = model.getUrl();
//                    String urlToImage = model.getUrlToImage();
//                    String publishedAt = model.getPublishedAt();
//
//                    favoriteList.setId(id);
//                    favoriteList.setAuthor(author);
//                    favoriteList.setDescription(description);
//                    favoriteList.setUrl(url);
//                    favoriteList.setTitle(title);
//                    favoriteList.setUrlToImage(urlToImage);
//                    favoriteList.setPublishedAt(publishedAt);
//
//                    if (MainActivity.database.favoriteDao().isFavorite(id)!=1){
//                        holder.fav_btn.setImageResource((R.drawable.ic_fav));
//                        MainActivity.database.favoriteDao().addData(favoriteList);
//                    }else {
//                        holder.fav_btn.setImageResource(R.drawable.ic_fav_border);
//                        MainActivity.database.favoriteDao().delete(favoriteList);
//                    }
//
//                }
//            });
//        }
    }



    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView title, desc, author, publishedAt, source, time;
        ImageView imageview,  fav_btn;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;
        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            progressBar = itemView.findViewById(R.id.progress_bar);
            imageview = itemView.findViewById(R.id.newsImage);
            fav_btn = itemView.findViewById(R.id.fav_btn);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}