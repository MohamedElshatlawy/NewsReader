package com.example.binaryworld.newsreader.Interface;

import com.example.binaryworld.newsreader.Model.News;
import com.example.binaryworld.newsreader.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by BinaryWorld on 14-Nov-17.
 */

public interface NewsService {

    @GET("v1/sources?language=en")
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);


}



