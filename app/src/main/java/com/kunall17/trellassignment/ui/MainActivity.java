package com.kunall17.trellassignment.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
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
    private AppCompatEditText searchEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dataViewModel = new DataViewModel();
        binding.setViewModel(dataViewModel);

        feedRv = binding.recyclerView;
        searchEt = binding.searchEt;
        PlayerAdapter adapter = new PlayerAdapter(dataViewModel);
        adapter.setHasStableIds(true);
        binding.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        feedRv.setHasFixedSize(true);
        feedRv.setLayoutManager(layoutManager);


        dataViewModel.getMutableLiveData().observe(this, adapter::setData);

    }


    private void d() {

    }
}
