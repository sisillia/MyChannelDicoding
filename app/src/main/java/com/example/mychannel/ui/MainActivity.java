package com.example.mychannel.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mychannel.R;
import com.example.mychannel.model.MovieData;
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

        if (savedInstanceState == null){
            bottom_nav.setSelectedItemId(R.id.movie_menu);
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_change_settings){
            Intent setting = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(setting);
        }

        return super.onOptionsItemSelected(item);
    }

}
