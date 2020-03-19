package com.kunall17.trellassignment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView feedRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedRv = findViewById(R.id.recyclerView);
        ADapter adapter = new ADapter(this);
        adapter.setHasStableIds(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        new PagerSnapHelper() {
        }.attachToRecyclerView(feedRv);

        feedRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("SEEHERE", "onScrollStateChanged: " + newState);
                if (newState == 0) {
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    Viddd viewHolderForLayoutPosition = (Viddd) feedRv.findViewHolderForLayoutPosition(firstVisibleItemPosition);
                    adapter.setPlayer(firstVisibleItemPosition, viewHolderForLayoutPosition);
                }
            }
        });
        feedRv.setHasFixedSize(true);
        feedRv.setLayoutManager(layoutManager);
        feedRv.setAdapter(adapter);
    }
}
