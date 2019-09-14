package com.example.mychannel.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mychannel.R;
import com.example.mychannel.ui.fragment.FavoriteFragment;
import com.example.mychannel.ui.fragment.MovieFragment;
import com.example.mychannel.ui.fragment.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottom_nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottom_nav = (BottomNavigationView) findViewById(R.id.bottom_menu);
        bottom_nav.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.movie_menu :
                fragment = new MovieFragment();
                break;
            case R.id.tv_show_menu :
                fragment = new TvShowFragment();
                break;
            case R.id.favorite_menu :
                fragment = new FavoriteFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
