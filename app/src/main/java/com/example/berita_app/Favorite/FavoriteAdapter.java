package com.example.berita_app.Favorite;

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
import com.example.berita_app.Adapter;
import com.example.berita_app.R;
import com.example.berita_app.Utils;
import com.example.berita_app.models.Article;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter <FavoriteAdapter.MyViewHolder> {

    private Context context;
    private List<FavoriteList> favoriteList;
    private FavoriteDatabase database;
    private Adapter.OnItemClickListener onItemClickListener;


    public FavoriteAdapter(List<FavoriteList> favoriteList, Context context) {
        this.favoriteList = favoriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);

        return new FavoriteAdapter.MyViewHolder(view, onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        final FavoriteList fl = favoriteList.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(fl.getUrlToImage())
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

        holder.title.setText(fl.getTitle());
        holder.desc.setText(fl.getDescription());
//        holder.source.setText(fl.getSource().getName());
        holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(fl.getPublishedAt()));
        holder.publishedAt.setText(Utils.DateFormat(fl.getPublishedAt()));
        holder.author.setText(fl.getAuthor());



    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public void setOnItemClickListener(Adapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, desc, author, publishedAt, source, time;
        ImageView imageview;
        ProgressBar progressBar;
        Adapter.OnItemClickListener onItemClickListener;
        public MyViewHolder(View itemView, Adapter.OnItemClickListener onItemClickListener) {
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

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
