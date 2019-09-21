package com.example.mychannel.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mychannel.model.MovieData;
import com.example.mychannel.model.TvData;

import java.util.ArrayList;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.mychannel.db.DatabaseContract.FavoriteColumn.DESCRIPTION;
import static com.example.mychannel.db.DatabaseContract.FavoriteColumn.ID;
import static com.example.mychannel.db.DatabaseContract.FavoriteColumn.IMAGE;
import static com.example.mychannel.db.DatabaseContract.FavoriteColumn.TITLE;
import static com.example.mychannel.db.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {

    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    private FavoriteHelper(Context context){
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<MovieData> getMovieFavorite() {
        open();
        ArrayList<MovieData> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            do {
                MovieData movieData = new MovieData();
                movieData.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                movieData.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieData.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movieData.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                arrayList.add(movieData);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertNote(MovieData movieData) {
        open();
        ContentValues args = new ContentValues();
        args.put(TITLE, movieData.getTitle());
        args.put(ID,movieData.getId());
        args.put(DESCRIPTION, movieData.getOverview());
        args.put(IMAGE, movieData.getPoster_path());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public boolean isFavorite(int id) {
        open();
        Cursor cursor = database.query(DATABASE_TABLE,
                new String[]{ID, TITLE, DESCRIPTION, IMAGE},
                "id" + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        } else {
            MovieData movieData = new MovieData();
            movieData.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
            movieData.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
            movieData.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
            movieData.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
            cursor.close();

            return movieData != null;
        }
    }

    public MovieData getMovieData(int id){
        open();

        ArrayList<MovieData> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                new String[]{ID, TITLE, DESCRIPTION, IMAGE},
                "id" + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(!cursor.moveToFirst()){
            cursor.close();
        }

        MovieData movieData = new MovieData();

        movieData.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
        movieData.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
        movieData.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
        movieData.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));

        return movieData;
    }

    public int deleteNote(int id) {
        open();
        Log.e("hellow", ""+id);
        return database.delete(DATABASE_TABLE, ID + " =? ", new String[]{ Integer.toString(id) });
    }
}
