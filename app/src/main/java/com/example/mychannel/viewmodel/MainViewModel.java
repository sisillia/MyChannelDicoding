package com.example.mychannel.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychannel.db.FavoriteHelper;
import com.example.mychannel.model.MovieData;
import com.example.mychannel.model.TvData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainViewModel extends ViewModel {

    private static final String API_KEY = "ca17afdd8c6d6638a7ee520a9b84ea8f";
    private MutableLiveData<ArrayList<MovieData>> listMovieData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvData>> lisTvData = new MutableLiveData<>();

    private MutableLiveData<ArrayList<MovieData>> lisMovieFavorite = new MutableLiveData<>();

    private MutableLiveData<MovieData> listMovieIsFavorite = new MutableLiveData<>();

    public void setListMovieData() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieData> listItemsMovie = new ArrayList<>();
        final ArrayList<TvData> listItemTv = new ArrayList<>();
        String urlMovie = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language=en-US";
        String urlTv = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=en-US";

        client.get(urlTv, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TvData tvData = new TvData(tv);

                        listItemTv.add(tvData);
                    }
                    lisTvData.postValue(listItemTv);

                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        client.get(urlMovie, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);

                        MovieData movieData = new MovieData(movie);
                        listItemsMovie.add(movieData);
                    }
                    listMovieData.postValue(listItemsMovie);

                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

    }

    public LiveData<ArrayList<MovieData>> getMovieData() {
        return listMovieData;
    }

    public LiveData<ArrayList<TvData>> getTVData(){
        return lisTvData;
    }

    public LiveData<ArrayList<MovieData>> getDataFavorite(Context context) {
        lisMovieFavorite.postValue(FavoriteHelper.getInstance(context).getMovieFavorite());
        return lisMovieFavorite;
    }

    public LiveData<MovieData> getCheckFavorite(Context context,int id){
        listMovieIsFavorite.postValue(FavoriteHelper.getInstance(context).getMovieData(id));
        return listMovieIsFavorite;
    }
}
