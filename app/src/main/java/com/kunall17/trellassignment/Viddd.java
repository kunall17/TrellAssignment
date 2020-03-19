package com.kunall17.trellassignment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.PlayerView;

class Viddd extends RecyclerView.ViewHolder {

    AppCompatImageView thumbIv;
    PlayerView player;

    public Viddd(@NonNull View itemView) {
        super(itemView);
        player = itemView.findViewById(R.id.main_iv);
        thumbIv = itemView.findViewById(R.id.thumb_iv);
    }

}
