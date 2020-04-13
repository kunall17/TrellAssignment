package com.kunall17.trellassignment.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
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
import com.kunall17.trellassignment.Diff;
import com.kunall17.trellassignment.R;
import com.kunall17.trellassignment.folder.Restaurant;
import com.kunall17.trellassignment.viewholders.PlayerViewHolder;
import com.kunall17.trellassignment.viewmodels.DataViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {


    private final DataViewModel dataViewModel;

    private List<Restaurant> list = new ArrayList<>();

    public PlayerAdapter(DataViewModel dataViewModel) {
        this.dataViewModel = dataViewModel;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Restaurant> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new Diff(this.list, newList));
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {

    }
}
