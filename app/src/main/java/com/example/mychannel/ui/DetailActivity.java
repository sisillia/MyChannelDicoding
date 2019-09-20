package com.example.mychannel.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mychannel.R;
import com.example.mychannel.db.FavoriteHelper;
import com.example.mychannel.model.MovieData;
import com.example.mychannel.model.TvData;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    CircleImageView imgFilm;
    private TextView titleFilm, descOfFilm;
    ImageView imgFavorite;
    private String imgUrl;
    private int id;

    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_FAVORITES = "extra_favorites";
    public static final String EXTRA_POSITION = "extra_position";


    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;

    private final int ALERT_DIALOG_DELETE = 20;
    public static final int RESULT_DELETE = 301;

    private MovieData movieData;
    private TvData tvData;
    private FavoriteHelper favoriteHelper;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Film Detail");

        imgFilm = (CircleImageView) findViewById(R.id.img_circle);
        titleFilm = (TextView) findViewById(R.id.tv_title_film);
        descOfFilm = (TextView) findViewById(R.id.tv_desc_film);

        movieData = getIntent().getParcelableExtra(EXTRA_DATA);

        titleFilm.setText(movieData.getTitle());
        descOfFilm.setText(movieData.getOverview());
        imgUrl = movieData.getPoster_path();
        id = movieData.getId();

        Glide.with(this)
                .load(movieData.getPoster_path())
                .into(imgFilm);


        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
    }

    private Observer<? super MovieData> getCheckMovie = new Observer<MovieData>() {
        @Override
        public void onChanged(MovieData movieData) {

        }
    };

}
