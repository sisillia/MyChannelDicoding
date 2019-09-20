package com.example.mychannel.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychannel.R;
import com.example.mychannel.model.TvData;
import com.example.mychannel.ui.adapter.TvShowAdapter;
import com.example.mychannel.viewmodel.MainViewModel;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {

    private TvShowAdapter tvShowAdapter;
    private RecyclerView recyclerData;
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTVData().observe(this, getTVData);

        tvShowAdapter = new TvShowAdapter(getContext());
        tvShowAdapter.notifyDataSetChanged();

        recyclerData = (RecyclerView) view.findViewById(R.id.recycler_data);
        recyclerData.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerData.setAdapter(tvShowAdapter);


        mainViewModel.setListMovieData();
        showLoading(true);

        return view;
    }

    private Observer<ArrayList<TvData>> getTVData = new Observer<ArrayList<TvData>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvData> tvData) {
            if (tvData != null){
                tvShowAdapter.setTvData(tvData);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
