package com.kunall17.trellassignment;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.Player;
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

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ADapter extends RecyclerView.Adapter<Viddd> {

    public static final long DEFAULT_MAX_CACHE_FILE_SIZE = 5 * 1024 * 1024;
    private final CacheDataSourceFactory dataSourceFactory;
    private final SimpleExoPlayer player;
    private final DefaultHttpDataSourceFactory factory;
    private final Context context;
    int lastINdex = -1;
    private ArrayList<String> a = new ArrayList<>();
    private SimpleCache cache;

    public ADapter(Context context) {
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/8ijoZpmyyWR6Kt1pOESjSZSJ4vES0s73.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/P7sls2VpRAJW8Vbz6rnUlU6WvLTZwEhp.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/XRa8qdlzCvuMIhcRLcqrNYJlKNKa5OKB.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/j65taHwHTw4mCpbA5moVjVO6frzwkD3u.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/8V7AyVafhbyMH2aWOL4xZdp8POAjskxn.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/W1snOQYcmY2Wv06pF0gZZivFnyWUgnuj.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/8COPuaSXvzyqzM4MG3FCRZNwVGxFmEEd.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/UdTgjehMxzugb7TN4O4Ycg5QVgqlojx8.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/r7EsSAGF6a1q2vXdZXFDUCTJ7wMLBGEO.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/fDkn4hLtkApjqyVq6vEItYKUcr8Kgxlf.mp4");

        this.context = context;
        File file = new File(context.getCacheDir(), "media");
        cache = new SimpleCache(file, new LeastRecentlyUsedCacheEvictor(1048576000));
        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(context).setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER);
        factory = new DefaultHttpDataSourceFactory("android-marsplay-video-player");
        dataSourceFactory = new CacheDataSourceFactory(getSimpleCache(), factory);
        player = new SimpleExoPlayer.Builder(context, defaultRenderersFactory).build();

        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(a.get(0)));
        player.prepare(mediaSource);
        player.seekTo(0);
    }

    public String replace(String url) {
        return url.replace("user-videos/videos/orig/", "user-images/images/orig/thumb-").replace(".mp4", ".jpg");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void preCache(int position, Context context) {
        String url = a.get(position);
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
    }

    @NonNull
    @Override
    public Viddd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("SEEHERE", "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        Viddd viddd = new Viddd(inflater.inflate(R.layout.item_list, parent, false));


        return viddd;
    }

    @Override
    public void onBindViewHolder(@NonNull Viddd holder, int position) {
        if (getItemCount() > position + 1) {
            preCache(position + 1, context);
        }
    }

    private Cache getSimpleCache() {
        return this.cache;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull Viddd holder) {
        super.onViewDetachedFromWindow(holder);
        holder.player.setPlayer(null);
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull Viddd holder) {
        super.onViewAttachedToWindow(holder);
        holder.thumbIv.setVisibility(View.VISIBLE);
        Glide.with(context).addDefaultRequestListener(new RequestListener<Object>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Object> target, boolean isFirstResource) {
                Log.d("ADapterseehere", "onLoadFailed() called with: e = [" + e + "], model = [" + model + "], target = [" + target + "], isFirstResource = [" + isFirstResource + "]");
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target<Object> target, DataSource dataSource, boolean isFirstResource) {
                Log.d("ADapterseehere", "onResourceReady() called with: resource = [" + resource + "], model = [" + model + "], target = [" + target + "], dataSource = [" + dataSource + "], isFirstResource = [" + isFirstResource + "]");
                return false;
            }
        }).load(replace(a.get(holder.getAdapterPosition()))).into(holder.thumbIv);
    }

    public void setPlayer(int s, @NotNull Viddd holder) {
        if (lastINdex != s) {
            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(a.get(s)));
            player.prepare(mediaSource);
            player.seekTo(0);
            lastINdex = s;
            holder.player.setPlayer(player);
            player.setPlayWhenReady(true);
            holder.thumbIv.setVisibility(View.VISIBLE);
            player.addVideoListener(new VideoListener() {
                @Override
                public void onRenderedFirstFrame() {
                    holder.thumbIv.setVisibility(View.GONE);
                }
            });

        }
    }
}
