package com.example.binaryworld.newsreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.binaryworld.newsreader.Common.ISO8601DateParser;
import com.example.binaryworld.newsreader.DetailArticles;
import com.example.binaryworld.newsreader.Interface.ItemClickListener;
import com.example.binaryworld.newsreader.Model.Article;
import com.example.binaryworld.newsreader.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shatla on 11/23/17.
 */

class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView articleTitle;
    CircleImageView articleImage;
    RelativeTimeTextView articleTime;
    ItemClickListener itemClickListener;

    public ListNewsViewHolder(View itemView) {
        super(itemView);
        articleTitle=itemView.findViewById(R.id.article_title);
        articleImage=itemView.findViewById(R.id.article_img);
        articleTime=itemView.findViewById(R.id.article_time);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}



public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder> {

    private Context context;
    private List<Article> articleList;

    public ListNewsAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public ListNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.news_lst_item,parent,false);
        return new ListNewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListNewsViewHolder holder, int position) {

        Picasso.with(context)
                .load(articleList.get(position).getUrlToImage())
                .into(holder.articleImage);
        if(articleList.get(position).getTitle().length()>65){
            holder.articleTitle.setText(articleList.get(position).getTitle().substring(0,65)+".....");

        }else
            holder.articleTitle.setText(articleList.get(position).getTitle());


        Date date=null;
        try{
            date=ISO8601DateParser.parse(articleList.get(position).getPublishedAt());
            holder.articleTime.setReferenceTime(date.getTime());

        }catch (Exception e){
            holder.articleTime.setReferenceTime(0);
        }


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean islongClick) {


                Intent intent=new Intent(context,DetailArticles.class);
                intent.putExtra("webURL",articleList.get(position).getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
