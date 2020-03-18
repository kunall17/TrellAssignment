package com.kunall17.trellassignment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.PlayerView;

class Viddd extends RecyclerView.ViewHolder {

    PlayerView player;

    public Viddd(@NonNull View itemView) {
        super(itemView);
        player = itemView.findViewById(R.id.main_iv);
    }

}
