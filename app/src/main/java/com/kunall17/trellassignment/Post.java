package com.kunall17.trellassignment;

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

}
