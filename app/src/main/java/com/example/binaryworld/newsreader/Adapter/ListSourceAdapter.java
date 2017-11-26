package com.example.binaryworld.newsreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binaryworld.newsreader.Common.Common;
import com.example.binaryworld.newsreader.Interface.IconBetterIdeaService;
import com.example.binaryworld.newsreader.Interface.ItemClickListener;
import com.example.binaryworld.newsreader.ListActivity;
import com.example.binaryworld.newsreader.Model.IconBetterIdea;
import com.example.binaryworld.newsreader.Model.WebSite;
import com.example.binaryworld.newsreader.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BinaryWorld on 14-Nov-17.
 */

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ItemClickListener itemClickListener;

    TextView source_title;
    CircleImageView source_img;

    public ListSourceViewHolder(View itemView) {
        super(itemView);

        source_title = (TextView) itemView.findViewById(R.id.source_name);
        source_img=(CircleImageView) itemView.findViewById(R.id.source_img);

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

public class ListSourceAdapter  extends RecyclerView.Adapter<ListSourceViewHolder>{

    private Context context;
    private WebSite webSite;

    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, WebSite webSite) {

        this.context = context;
        this.webSite = webSite;
        this.mService= Common.getIconService();
    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position) {

        StringBuilder iconBetterApi=new StringBuilder("https://icons.better-idea.org/allicons.json?url=");
        iconBetterApi.append(webSite.getSources().get(position).getUrl());




        mService.getIconUrl(iconBetterApi.toString())
                .enqueue(new Callback<IconBetterIdea>() {
                    @Override
                    public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {

                        if (response.body()!=null
                                && response.body().getIcons()!=null
                                && response.body().getIcons().size() > 0
                                && !(TextUtils.isEmpty(response.body().getIcons().get(0).getUrl())))
                        {
                            Picasso.with(context)
                                    .load(response.body().getIcons().get(0).getUrl())
                                    .into(holder.source_img);

                        }


                    }

                    @Override
                    public void onFailure(Call<IconBetterIdea> call, Throwable t) {

                    }
                });

        holder.source_title.setText(webSite.getSources().get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean islongClick) {

                try {
                    Intent intent = new Intent(context, ListActivity.class);
                    intent.putExtra("source", webSite.getSources().get(position).getId());
                    //intent.putExtra("sortBy",webSite.getSources().get(position).getSortByAvailable().get(0));
                    intent.putExtra("sortBy", "top");
//                    System.out.println("key:"+webSite.getSources().get(position).getSortByAvailable().get(0));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}
