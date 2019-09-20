package com.example.mychannel.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mychannel.R;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_FAVORITES = "extra_favorites";
    public static final String EXTRA_POSITION = "extra_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
