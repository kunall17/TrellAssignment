package com.kunall17.trellassignment.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheUtil
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.kunall17.trellassignment.repository.PostRepository
import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val postRepository: PostRepository = PostRepository()
    private var postList: ArrayList<Post> = ArrayList()

    private var dataSourceFactory: CacheDataSourceFactory
    var defaultRenderersFactory: DefaultRenderersFactory
    private var player: SimpleExoPlayer
    private var factory: DefaultHttpDataSourceFactory
    private var cache: SimpleCache

    private fun fetchPostsList() {
        postList = postRepository.fetchPostsList()
    }

    val postListSize: Int
        get() = postList.size

    fun fetchPost(position: Int): Post {
        return postList[position]
    }

    fun getDataSourceFactory(): CacheDataSourceFactory {
        return dataSourceFactory
    }

    init {
        fetchPostsList()

        val file = File(application.cacheDir, "media")
        cache = SimpleCache(file, LeastRecentlyUsedCacheEvictor(1048576000))
        defaultRenderersFactory =
            DefaultRenderersFactory(application).setExtensionRendererMode(
                DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
            )
        factory = DefaultHttpDataSourceFactory("android-marsplay-video-player")
        dataSourceFactory = CacheDataSourceFactory(cache, factory)
        player = SimpleExoPlayer.Builder(application, defaultRenderersFactory).build()
    }

    fun preCache(position: Int) {
        val url = fetchPost(position).postUrl
        val dataSpec = DataSpec(Uri.parse(url), 0, 1024 * 1024, null)
        val cached = CacheUtil.getCached(dataSpec, cache, null)
        if (cached.second > 0) {
            return
        }
        val thread = Thread(Runnable {
            try {
                CacheUtil.cache(
                    dataSpec,
                    cache,
                    null,
                    dataSourceFactory.createDataSource(),
                    null,
                    AtomicBoolean(false)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        thread.start()

        Glide.with(getApplication<Application>().baseContext)
            .downloadOnly()
            .load(url)
            .submit()
    }

    fun generateMediaSource(firstVisibleItemPosition: Int): ProgressiveMediaSource =
        ProgressiveMediaSource.Factory(getDataSourceFactory()).createMediaSource(
            Uri.parse(
                fetchPost(
                    firstVisibleItemPosition
                ).postUrl
            )
        )
}