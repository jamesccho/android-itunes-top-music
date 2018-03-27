package io.github.jamesccho.itunestopmusic;

import android.app.Application;

import io.github.jamesccho.itunestopmusic.data.db.AppDatabase;
import io.github.jamesccho.itunestopmusic.data.DataRepository;
import okhttp3.OkHttpClient;

/**
 * Created by james on 25/3/2018.
 */

public class BaseApp extends Application {

    private final AppExecutors mAppExecutors = new AppExecutors();
    private final OkHttpClient mHttpClient = new OkHttpClient();
    private DataRepository mDataRepository;


    @Override
    public void onCreate() {
        super.onCreate();
        mDataRepository = DataRepository.getInstance(AppDatabase.getInstance(getApplicationContext()), mHttpClient, mAppExecutors);
    }

    public DataRepository getDataRepository() {
        return mDataRepository;
    }

}
