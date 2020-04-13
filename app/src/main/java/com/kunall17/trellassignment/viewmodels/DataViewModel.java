package com.kunall17.trellassignment.viewmodels;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.kunall17.trellassignment.folder.Restaurant;
import com.kunall17.trellassignment.folder.Restaurant_;
import com.kunall17.trellassignment.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class DataViewModel extends ViewModel {

    private PostRepository postRepository;
    private ArrayList<Post> postList;
    private Handler handler;
    private Runnable workRunnable;

    public DataViewModel() {
        postRepository = new PostRepository();
        handler = new Handler(Looper.getMainLooper() /*UI thread*/);
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public int getPostListSize() {
        return postList.size();
    }

    public Post fetchPost(int position) {
        return postList.get(position);
    }

    public void searchForQuery(String query) {
        if (query.length() < 2) {
            return;
        }

        handler.removeCallbacks(workRunnable);
        workRunnable = () -> postRepository.searchForQuery(query);
        handler.postDelayed(workRunnable, 350 /*delay*/);
    }

    public MutableLiveData<PagedList<Restaurant>> getMutableLiveData() {
        return postRepository.getNewsData();
    }
}
