package com.kunall17.trellassignment;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class Post {

    private String post;

    public Post(String post) {
        this.post = post;
    }

    public String getPostUrl() {
        return post;
    }

    public String getThumbnailUrl() {
        return post.replace("user-videos/videos/orig/", "user-images/images/orig/thumb-").replace(".mp4", ".jpg");
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imagepath) {
        Glide.with(imageView.getContext()).load(imagepath).into(imageView);
    }

}
