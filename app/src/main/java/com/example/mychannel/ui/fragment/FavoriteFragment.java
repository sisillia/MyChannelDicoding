package com.example.mychannel.ui.fragment;

import android.content.Intent;
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
import com.example.mychannel.model.MovieData;
import com.example.mychannel.ui.DetailActivity;
import com.example.mychannel.ui.adapter.FavoriteAdapter;
import com.example.mychannel.ui.adapter.MovieAdapter;
import com.example.mychannel.viewmodel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerData;
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getDataFavorite(getContext()).observe(this, getMovieData);

        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();

        recyclerData = (RecyclerView) view.findViewById(R.id.recycler_data);
        recyclerData.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerData.setAdapter(movieAdapter);

        mainViewModel.setListMovieData();

        return view;
    }

    private Observer<ArrayList<MovieData>> getMovieData = new Observer<ArrayList<MovieData>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MovieData> movieData) {
            if (movieData != null){
                movieAdapter.setMovieData(movieData);
            } else {

            }
        }
    };

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerData, message, Snackbar.LENGTH_SHORT).show();
    }

}
