package com.kunall17.trellassignment.network;

import com.kunall17.trellassignment.folder.Example;
import com.kunall17.trellassignment.viewmodels.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apis {

    @GET("/posts")
    Call<Example> getNewsList(@Query("query") String newsSource);

}
