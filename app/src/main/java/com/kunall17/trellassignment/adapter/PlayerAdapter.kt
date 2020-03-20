package com.kunall17.trellassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.video.VideoListener
import com.kunall17.trellassignment.R
import com.kunall17.trellassignment.viewholders.PlayerViewHolder
import com.kunall17.trellassignment.viewmodels.DataViewModel

class PlayerAdapter(
    private val dataViewModel: DataViewModel,
    private var player: SimpleExoPlayer
) :
    RecyclerView.Adapter<PlayerViewHolder>() {
    private var lastIndex = -1
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun initPlayerMediaSource(holder: PlayerViewHolder, position: Int) {
        val mediaSource =
            dataViewModel.generateMediaSource(position)
        player.prepare(mediaSource)
        player.seekTo(0)
        lastIndex = position
        holder.setPlayer(player)
        player.playWhenReady = true
        player.addVideoListener(object : VideoListener {
            override fun onRenderedFirstFrame() {
                holder.setThumbVisibility(false)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
    }

    override fun onViewDetachedFromWindow(holder: PlayerViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow() //   player.setPlayer(null);
    }

    override fun getItemCount(): Int {
        return dataViewModel.postListSize
    }

    override fun onViewAttachedToWindow(holder: PlayerViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attach(dataViewModel.fetchPost(holder.adapterPosition))
        if (lastIndex == -1) initPlayerMediaSource(holder, 0)
    }

}