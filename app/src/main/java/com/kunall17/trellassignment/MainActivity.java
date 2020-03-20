package com.kunall17.trellassignment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kunall17.trellassignment.databinding.ActivityMainBinding;

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
        ADapter adapter = new ADapter(this, dataViewModel);
        adapter.setHasStableIds(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        new PagerSnapHelper() {
        }.attachToRecyclerView(feedRv);

        feedRv.setHasFixedSize(true);
        feedRv.setLayoutManager(layoutManager);
        feedRv.setAdapter(adapter);
        feedRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    Viddd viewHolderForLayoutPosition = (Viddd) feedRv.findViewHolderForAdapterPosition(firstVisibleItemPosition);
                    adapter.setPlayer(firstVisibleItemPosition, viewHolderForLayoutPosition);
                }
            }
        });
    }
}
