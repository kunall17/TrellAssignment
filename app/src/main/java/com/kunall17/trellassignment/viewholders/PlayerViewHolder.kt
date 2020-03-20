package com.kunall17.trellassignment.viewholders

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.kunall17.trellassignment.R
import com.kunall17.trellassignment.viewmodels.Post

class PlayerViewHolder(val viewDataBinding: ViewDataBinding) : ViewHolder(
    viewDataBinding.root
) {
    var player: PlayerView

    fun attach(post: Post?) {
        viewDataBinding.setVariable(BR.thumbVisibilty, true)
        viewDataBinding.setVariable(BR.post, post)
        viewDataBinding.executePendingBindings()
    }

    fun onViewDetachedFromWindow() {
        player.player = null
    }

    fun setPlayer(player: SimpleExoPlayer?) {
        this.player.player = player
    }

    fun setThumbVisibility(b: Boolean) {
        viewDataBinding.setVariable(BR.thumbVisibilty, b)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            Glide.with(view.context).load(url).into(view)
        }
    }

    init {
        player = viewDataBinding.root.findViewById(R.id.main_iv)
    }
}