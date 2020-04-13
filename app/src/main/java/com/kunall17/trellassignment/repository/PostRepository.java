package com.kunall17.trellassignment.repository;

import androidx.lifecycle.MutableLiveData;

import com.kunall17.trellassignment.folder.Example;
import com.kunall17.trellassignment.folder.Restaurant;
import com.kunall17.trellassignment.network.RetrofitService;
import com.kunall17.trellassignment.network.apis;
import com.kunall17.trellassignment.viewmodels.Post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {
    private apis newsApi;
    private MutableLiveData<List<Restaurant>> newsData = new MutableLiveData<>();

    public PostRepository() {
        newsApi = RetrofitService.cteateService(apis.class);
    }

    public MutableLiveData<List<Restaurant>> getNewsData() {
        return newsData;
    }

    public void searchForQuery(String source) {
        newsApi.getNewsList(source).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {
                    Example body = response.body();
                    newsData.setValue(body.getRestaurants());
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                newsData.setValue(null);
            }
        });
    }
}
