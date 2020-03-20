package com.kunall17.trellassignment.viewmodels;

import androidx.lifecycle.ViewModel;

import com.kunall17.trellassignment.repository.PostRepository;

import java.util.ArrayList;

public class DataViewModel extends ViewModel {

    private PostRepository postRepository;
    private ArrayList<Post> postList;

    public DataViewModel() {
        postRepository = new PostRepository();
        fetchPostsList();
    }

    private void fetchPostsList() {
        postList = postRepository.fetchPostsList();
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
}