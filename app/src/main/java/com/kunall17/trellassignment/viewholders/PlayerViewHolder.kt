package com.kunall17.trellassignment.viewholders

import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.kunall17.trellassignment.R
import com.kunall17.trellassignment.viewmodels.Post

class PlayerViewHolder(val viewDataBinding: ViewDataBinding) : ViewHolder(
    viewDataBinding.root
) {
    var player: PlayerView

    fun attach(post: Post?) {
        viewDataBinding.setVariable(BR.post, post)
        viewDataBinding.executePendingBindings()
    }

    fun onViewDetachedFromWindow() {
        player.player = null
    }

    fun setPlayer(player: SimpleExoPlayer?) {
        this.player.player = player
    }


    init {
        player = viewDataBinding.root.findViewById(R.id.main_iv)
    }
}