package com.kunall17.trellassignment.viewmodels

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class Post(val postUrl: String) {

    val thumbnailUrl: String
        get() = postUrl.replace("user-videos/videos/orig/", "user-images/images/orig/thumb-")
            .replace(".mp4", ".jpg")

    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, imagepath: String?) {
        Glide.with(imageView.context).load(imagepath).into(imageView)
    }

}