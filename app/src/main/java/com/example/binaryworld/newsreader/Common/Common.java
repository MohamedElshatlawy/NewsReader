package com.example.binaryworld.newsreader.Common;

import com.example.binaryworld.newsreader.Interface.IconBetterIdeaService;
import com.example.binaryworld.newsreader.Interface.NewsService;
import com.example.binaryworld.newsreader.Model.IconBetterIdea;
import com.example.binaryworld.newsreader.Remote.IconBetterIdeaClient;
import com.example.binaryworld.newsreader.Remote.RetrofitClient;

/**
 * Created by BinaryWorld on 14-Nov-17.
 */

public class Common {

    private static final String Base_URL=" https://newsapi.org/";
    public static final String API_KEY="ec9d2bb8b22d40d9a0cbec51d7cc8783";

    public static NewsService getNewsService(){

        return RetrofitClient.getClient(Base_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService(){

        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }


    public static String getApiUrl(String source,String sortBY,String API_KEY){

        //http://newsapi.org/v1/articles?source=al-jazeera-english&sortBy=top&apiKey
    StringBuilder stringBuilder=new StringBuilder("http://newsapi.org/v1/articles?source=");

    return stringBuilder.append(source)
            .append("&sortBy=")
            .append(sortBY)
            .append("&apiKey=")
            .append(API_KEY)
            .toString();

    }

}
