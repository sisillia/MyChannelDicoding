package com.example.mychannel.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mychannel.R;
import com.example.mychannel.db.FavoriteHelper;
import com.example.mychannel.model.MovieData;
import com.example.mychannel.model.TvData;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

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
        imgFavorite = (ImageView) findViewById(R.id.img_favorite);

        movieData = getIntent().getParcelableExtra(EXTRA_DATA);

        titleFilm.setText(movieData.getTitle());
        descOfFilm.setText(movieData.getOverview());
        imgUrl = movieData.getPoster_path();
        id = movieData.getId();

        Glide.with(this)
                .load(movieData.getPoster_path())
                .into(imgFilm);


        imgFavorite.setOnClickListener(this);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        if (FavoriteHelper.getInstance(this).isFavorite(id)){
            imgFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.img_favorite){
            if(FavoriteHelper.getInstance(this).isFavorite(id)) {
                showAlertDialog(ALERT_DIALOG_DELETE);
            } else {
                String title = titleFilm.getText().toString().trim();
                String desc = descOfFilm.getText().toString().trim();

                movieData.setTitle(title);
                movieData.setOverview(desc);
                movieData.setPoster_path(imgUrl);
                movieData.setId(movieData.getId());

                Intent intent = new Intent();
                intent.putExtra(EXTRA_FAVORITES,movieData);
                intent.putExtra(EXTRA_POSITION,0);

                long result = favoriteHelper.insertNote(movieData);

                if (result > 0 ){
                    movieData.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();
                } else {
                    Toast.makeText(DetailActivity.this, "Gagal menambahkan ke favorite", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Observer<? super MovieData> getCheckMovie = new Observer<MovieData>() {
        @Override
        public void onChanged(MovieData movieData) {

        }
    };


    private void showAlertDialog(int type) {

        String dialogTitle, dialogMessage;
        dialogMessage = getResources().getString(R.string.question);
        dialogTitle = getResources().getString(R.string.title_question);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes_question), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int result = favoriteHelper.deleteNote(movieData.getId());
                        if (result > 0) {
                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_POSITION, position);
                            setResult(RESULT_DELETE, intent);
                            finish();
                        } else {
                            Toast.makeText(DetailActivity.this, getResources().getString(R.string.failed_delete), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no_question), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
