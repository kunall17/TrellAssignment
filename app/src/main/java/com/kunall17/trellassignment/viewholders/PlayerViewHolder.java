package com.kunall17.trellassignment.viewholders;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.kunall17.trellassignment.viewmodels.Post;
import com.kunall17.trellassignment.R;

public class PlayerViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;
    PlayerView player;

    public PlayerViewHolder(@NonNull ViewDataBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
        player = (binding.getRoot().findViewById(R.id.main_iv));
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    public ViewDataBinding getViewDataBinding() {
        return binding;
    }

    public void attach(Post post) {
        binding.setVariable(BR.thumbVisibilty, true);
        binding.setVariable(BR.post, post);
        binding.executePendingBindings();
    }

    public void onViewDetachedFromWindow() {
        player.setPlayer(null);
    }

    public void setPlayer(SimpleExoPlayer player) {
        this.player.setPlayer(player);
    }

    public void setThumbVisibility(boolean b) {
        binding.setVariable(BR.thumbVisibilty, b);
    }
}
