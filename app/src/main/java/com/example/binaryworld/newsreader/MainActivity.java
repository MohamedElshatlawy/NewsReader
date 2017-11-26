package com.example.binaryworld.newsreader;


import android.app.AlertDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.binaryworld.newsreader.Adapter.ListSourceAdapter;
import com.example.binaryworld.newsreader.Common.Common;
import com.example.binaryworld.newsreader.Interface.NewsService;
import com.example.binaryworld.newsreader.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*App tech:

-Retrofit for fetch data
-Picasso for read imgs from internet
-Recycler view
-Paper for cache
-Diagonal layout % Kenburns effect

 */
public class MainActivity extends AppCompatActivity {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mNewsService;
    ListSourceAdapter adapter;
    AlertDialog alertDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init cache
        Paper.init(this);

        //init service
        mNewsService= Common.getNewsService();
        swipeRefreshLayout=findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });

        //init view
        listWebsite=findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        alertDialog = new SpotsDialog(this);

        loadWebsiteSource(false);
    }

    private void loadWebsiteSource(boolean isRefreshed) {

        if(!isRefreshed){
            String cache=Paper.book().read("cache");
            if(cache!=null && !cache.isEmpty()){ //if have a cache
                WebSite webSite=new Gson().fromJson(cache,WebSite.class); //convert data from json to object

                adapter=new ListSourceAdapter(this,webSite);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }
            else{ // not have cache

                alertDialog.show();

                //fetch new data
                mNewsService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter=new ListSourceAdapter(getBaseContext(),response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);


                        //save to cache
                        Paper.book().write("cache",new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });


            }
        }else { //if from swipe to refresh

            alertDialog.show();

            //fetch new data
            mNewsService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter=new ListSourceAdapter(getBaseContext(),response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);


                    //save to cache
                    Paper.book().write("cache",new Gson().toJson(response.body()));

                    //dimiss  refresh progress
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });

        }
    }
}
