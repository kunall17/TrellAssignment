package com.kunall17.trellassignment;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/P7sls2VpRAJW8Vbz6rnUlU6WvLTZwEhp.mp4");
        a.add("https://cdn.trell.co/h_640,w_640/user-videos/videos/orig/8ijoZpmyyWR6Kt1pOESjSZSJ4vES0s73.mp4");
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

    private void preCache(int position, Context context) {

        String url = a.get(position);
        DataSpec dataSpec = new DataSpec(Uri.parse(url), 0, 1024 * 1024, null);
        Pair<Long, Long> cached = CacheUtil.getCached(dataSpec, cache, null);
        Log.d("SEEHERE", "preCache: " + cached);

        if (cached.second != 0) {
            Log.d("SEEHERE", "!!!!preCache: " + cached);
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CacheUtil.cache(dataSpec, cache, null, dataSourceFactory.createDataSource(), new CacheUtil.ProgressListener() {
                        @Override
                        public void onProgress(long requestLength, long bytesCached, long newBytesCached) {
                            Log.d("ADapterseehere", position + "onProgress() called with: requestLength = [" + requestLength + "], bytesCached = [" + bytesCached + "], newBytesCached = [" + newBytesCached + "]");
                        }
                    }, new AtomicBoolean(false));
                    System.out.println("seehere");
                } catch (Exception e) {
                    System.out.println("Exception");
                    e.printStackTrace();
                }
            }
        });
        thread.start();

//
//        AsyncTask.execute(() -> {
//            Log.d("SEEHERE", "preCache: executing");
//            PriorityTaskManager mPriorityTaskManager = new PriorityTaskManager();
//            mPriorityTaskManager.add(C.PRIORITY_DOWNLOAD);
//
//            DataSource cacheReadDataSource = new FileDataSource();
//            DefaultDataSourceFactory upstreamFactory =
//                    new DefaultDataSourceFactory(context, factory);
//            DataSink cacheWriteDataSink = new CacheDataSink(cache, DEFAULT_MAX_CACHE_FILE_SIZE);
//            DataSource upstream = upstreamFactory.createDataSource();
//            upstream = new PriorityDataSource(upstream, mPriorityTaskManager, C.PRIORITY_DOWNLOAD);
//
//            int progressive_download = Log.e("progressive_download", "Create priority upstream data source!");
//            CacheDataSource cacheDataSource = new CacheDataSource(cache, upstream, cacheReadDataSource,
//                    cacheWriteDataSink, 0, new CacheDataSource.EventListener() {
//                @Override
//                public void onCachedBytesRead(long cacheSizeBytes, long cachedBytesRead) {
//                    Log.d("SEEHERE", "onCachedBytesRead() called with: cacheSizeBytes = [" + cacheSizeBytes + "], cachedBytesRead = [" + cachedBytesRead + "]");
//                }
//
//                @Override
//                public void onCacheIgnored(int reason) {
//                    Log.d("SEEHERE", "onCacheIgnored() called with: reason = [" + reason + "]");
//                }
//            });
//
//            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
//            try {
//                Log.d("SEEHERE", "preCache: ");
//                CacheUtil.cache(dataSpec, cache, null, cacheDataSource, new CacheUtil.ProgressListener() {
//                    @Override
//                    public void onProgress(long requestLength, long bytesCached, long newBytesCached) {
//                        Log.d("SEEHERE", "onProgress() called with: requestLength = [" + requestLength + "], bytesCached = [" + bytesCached + "], newBytesCached = [" + newBytesCached + "]");
//                    }
//                }, atomicBoolean);
//                Log.d("SEEHERE", "preCache: done ");
//                cacheDataSource.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
    }

    @NonNull
    @Override
    public Viddd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new Viddd(inflater.inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viddd holder, int position) {
        Log.d("ADapterseehere", "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
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
        Log.d("ADapterseehere", "onViewDetachedFromWindow() called with: holder = [" + holder.getAdapterPosition() + "]");
        holder.player.setPlayer(null);

    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull Viddd holder) {
        super.onViewAttachedToWindow(holder);
        Log.d("ADapterseehere", "onViewAttachedToWindow() called with: holder = [" + holder.getAdapterPosition() + "]");
    }

    public void setPlayer(int s, @NotNull Viddd holder) {
        Log.d("ADapterseehere", "setPlayer() called with: s = [" + s + "], holder = [" + holder + "]");
        if (lastINdex != s) {
            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(a.get(s)));
            player.prepare(mediaSource);
            player.seekTo(0);
            lastINdex = s;
            holder.player.setPlayer(player);
            player.setPlayWhenReady(true);
        }
    }
}
