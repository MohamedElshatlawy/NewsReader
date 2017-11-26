package com.example.binaryworld.newsreader;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binaryworld.newsreader.Adapter.ListNewsAdapter;
import com.example.binaryworld.newsreader.Common.Common;
import com.example.binaryworld.newsreader.Interface.NewsService;
import com.example.binaryworld.newsreader.Model.Article;
import com.example.binaryworld.newsreader.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {


    KenBurnsView kenBurnsView;
    DiagonalLayout diagonalLayout;

    AlertDialog alertDialog;
    NewsService newsService;
    TextView top_author,top_title;
    SwipeRefreshLayout swipeRefreshLayout;


    String source="",sortBy="",webHotUrl="";


    ListNewsAdapter listNewsAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


            //Services
            newsService = Common.getNewsService();
            alertDialog = new SpotsDialog(this);
            swipeRefreshLayout=findViewById(R.id.swip);

            //views

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadNews(source, true);
                }
            });

            diagonalLayout = findViewById(R.id.diagonal_layout);
            diagonalLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //click to hot latest news to read

                    Intent intent=new Intent(getBaseContext(),DetailArticles.class);
                    intent.putExtra("webURL",webHotUrl);
                    startActivity(intent);

                }
            });

            kenBurnsView = findViewById(R.id.top_image);

            top_author = findViewById(R.id.top_author);
            top_title = findViewById(R.id.top_title);

        recyclerView=findViewById(R.id.news_lst);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


            //Intent

            if (getIntent() != null) {
                source = getIntent().getStringExtra("source");
                sortBy = getIntent().getStringExtra("sortBy");
                if (!source.isEmpty() && !sortBy.isEmpty()) {

                    //Toast.makeText(this, "asd", Toast.LENGTH_LONG).show();
                    loadNews(source, false);
                }
            }




    }

    private void loadNews(String source, boolean isRefreshed) {

        if(!isRefreshed){
            alertDialog.show();
            newsService.getNewestArticles(Common.getApiUrl(source,sortBy,Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            alertDialog.dismiss();
                            Picasso.with(getBaseContext())
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kenBurnsView);

                            top_author.setText(response.body().getArticles().get(0).getAuthor());
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            webHotUrl=response.body().getArticles().get(0).getUrl();


                            List<Article> removeFirstItems=response.body().getArticles();
                            removeFirstItems.remove(0);
                            listNewsAdapter=new ListNewsAdapter(getBaseContext(),removeFirstItems);
                            listNewsAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(listNewsAdapter);
                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {
                            System.out.println("reqFAIL...");
                        }
                    });
        }else{
            alertDialog.show();
            newsService.getNewestArticles(Common.getApiUrl(source,sortBy,Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            alertDialog.dismiss();
                            Picasso.with(getBaseContext())
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kenBurnsView);

                            top_author.setText(response.body().getArticles().get(0).getAuthor());
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            webHotUrl=response.body().getArticles().get(0).getUrl();


                            List<Article> removeFirstItems=response.body().getArticles();
                            removeFirstItems.remove(0);
                            listNewsAdapter=new ListNewsAdapter(getBaseContext(),removeFirstItems);
                            listNewsAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(listNewsAdapter);
                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {
                            System.out.println("reqFAIL...");
                        }
                    });
            swipeRefreshLayout.setRefreshing(false);
        }


    }
}
