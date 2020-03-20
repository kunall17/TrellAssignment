package com.kunall17.trellassignment.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kunall17.trellassignment.R;
import com.kunall17.trellassignment.adapter.PlayerAdapter;
import com.kunall17.trellassignment.databinding.ActivityMainBinding;
import com.kunall17.trellassignment.viewholders.PlayerViewHolder;
import com.kunall17.trellassignment.viewmodels.DataViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView feedRv;
    private DataViewModel dataViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dataViewModel = new DataViewModel();
        binding.setViewModel(dataViewModel);

        feedRv = binding.recyclerView;
        PlayerAdapter adapter = new PlayerAdapter(this, dataViewModel);
        adapter.setHasStableIds(true);
        binding.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        new PagerSnapHelper().attachToRecyclerView(feedRv);

        feedRv.setHasFixedSize(true);
        feedRv.setLayoutManager(layoutManager);
        feedRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    PlayerViewHolder viewHolderForLayoutPosition = (PlayerViewHolder) feedRv.findViewHolderForAdapterPosition(firstVisibleItemPosition);
                    adapter.setPlayer(firstVisibleItemPosition, viewHolderForLayoutPosition);
                }
            }
        });
    }
}
