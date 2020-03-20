package com.kunall17.trellassignment.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.video.VideoListener;
import com.kunall17.trellassignment.R;
import com.kunall17.trellassignment.viewholders.PlayerViewHolder;
import com.kunall17.trellassignment.viewmodels.DataViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

    public static final long DEFAULT_MAX_CACHE_FILE_SIZE = 5 * 1024 * 1024;
    private final CacheDataSourceFactory dataSourceFactory;
    private final SimpleExoPlayer player;
    private final DefaultHttpDataSourceFactory factory;
    private final Context context;
    private final DataViewModel dataViewModel;
    private int lastINdex = -1;
    private SimpleCache cache;

    public PlayerAdapter(Context context, DataViewModel dataViewModel) {
        this.context = context;
        this.dataViewModel = dataViewModel;
        File file = new File(context.getCacheDir(), "media");
        cache = new SimpleCache(file, new LeastRecentlyUsedCacheEvictor(1048576000));
        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(context).setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER);
        factory = new DefaultHttpDataSourceFactory("android-marsplay-video-player");
        dataSourceFactory = new CacheDataSourceFactory(getSimpleCache(), factory);
        player = new SimpleExoPlayer.Builder(context, defaultRenderersFactory).build();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void preCache(int position, Context context) {
        String url = dataViewModel.fetchPost(position).getPostUrl();
        DataSpec dataSpec = new DataSpec(Uri.parse(url), 0, 1024 * 1024, null);
        Pair<Long, Long> cached = CacheUtil.getCached(dataSpec, cache, null);

        if (cached.second != 0) {
            return;
        }

        Thread thread = new Thread(() -> {
            try {
                CacheUtil.cache(dataSpec, cache, null, dataSourceFactory.createDataSource(), null, new AtomicBoolean(false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        FutureTarget<File> submit = Glide.with(context)
                .downloadOnly()
                .load(url)
                .submit();
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {

        if (getItemCount() > position + 1) {
            preCache(position + 1, context);
        }
    }

    private Cache getSimpleCache() {
        return this.cache;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull PlayerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow(); //   player.setPlayer(null);
    }

    @Override
    public int getItemCount() {
        return dataViewModel.getPostListSize();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull PlayerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.attach(dataViewModel.fetchPost(holder.getAdapterPosition()));
        if (lastINdex == -1) setPlayer(0, holder);
    }

    public void setPlayer(int s, @NotNull PlayerViewHolder holder) {
        if (lastINdex != s) {
            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(dataViewModel.fetchPost(s).getPostUrl()));
            player.prepare(mediaSource);
            player.seekTo(0);
            lastINdex = s;
            holder.setPlayer(player);
            player.setPlayWhenReady(true);
            player.removeVideoListener(null);
            player.addVideoListener(new VideoListener() {
                @Override
                public void onRenderedFirstFrame() {
                    holder.setThumbVisibility(false);
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        player.release();
    }
}
