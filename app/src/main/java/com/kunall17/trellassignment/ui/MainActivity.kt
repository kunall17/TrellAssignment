package com.kunall17.trellassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.kunall17.trellassignment.R
import com.kunall17.trellassignment.adapter.PlayerAdapter
import com.kunall17.trellassignment.databinding.ActivityMainBinding
import com.kunall17.trellassignment.viewholders.PlayerViewHolder
import com.kunall17.trellassignment.viewmodels.DataViewModel

class MainActivity : AppCompatActivity() {

    lateinit var feedRv: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: PlayerAdapter
    private var dataViewModel: DataViewModel? = null
    lateinit private var binding: ActivityMainBinding
    private lateinit var player: SimpleExoPlayer
    private var lastIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        dataViewModel = DataViewModel(application)

        binding.viewModel = dataViewModel
        feedRv = binding.recyclerView

        initPlayer()
        adapter = PlayerAdapter(dataViewModel!!, player)
        adapter.setHasStableIds(true)
        binding.adapter = adapter
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        PagerSnapHelper().attachToRecyclerView(feedRv)
        feedRv.setHasFixedSize(true)
        feedRv.layoutManager = layoutManager

        setupScrollListener()
    }

    override fun onResume() {
        super.onResume()
        player.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        player.playWhenReady = false
    }

    private fun setupScrollListener() {
        feedRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == 0) {
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    val viewHolderForLayoutPosition =
                        feedRv.findViewHolderForAdapterPosition(firstVisibleItemPosition) as PlayerViewHolder

                    if (lastIndex != firstVisibleItemPosition) {
                        adapter.initPlayerMediaSource(
                            viewHolderForLayoutPosition,
                            firstVisibleItemPosition
                        )
                    }
                }
            }
        })
        feedRv.scrollToPosition(0)
    }

    private fun initPlayer() {
        player = SimpleExoPlayer.Builder(this, dataViewModel!!.defaultRenderersFactory).build()
    }
}