package io.github.jamesccho.itunestopmusic.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import io.github.jamesccho.itunestopmusic.AppExecutors;
import io.github.jamesccho.itunestopmusic.data.db.AppDatabase;
import io.github.jamesccho.itunestopmusic.data.model.Music;
import io.github.jamesccho.itunestopmusic.data.util.FeedParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by james on 25/3/2018.
 */

public class DataRepository {

    private static final String TAG = "DataRepository";

    private static final String FEED_TOP_SONGS_URL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml";
    private static final String FEED_TOP_ALBUMS_URL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topalbums/limit=25/xml";
    private final AppDatabase mAppDatabase;
    private final OkHttpClient mHttpClient;
    private final AppExecutors mAppExecutors;
    private static DataRepository INSTANCE;

    private DataRepository(final AppDatabase database, final OkHttpClient httpClient, final AppExecutors appExecutors) {
        mAppDatabase = database;
        mHttpClient = httpClient;
        mAppExecutors = appExecutors;
    }

    public static DataRepository getInstance(final AppDatabase database, final OkHttpClient httpClient, final AppExecutors appExecutors) {
        synchronized (DataRepository.class) {
            if (INSTANCE == null) {
                INSTANCE = new DataRepository(database, httpClient, appExecutors);
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Music>> loadAllMusicsFromLocal() {
        return mAppDatabase.musicDao().loadAllMusics();
    }

    public void saveAllMusics(List<Music> musics) {
        mAppDatabase.musicDao().deleteAll();
        mAppDatabase.musicDao().insertAll(musics);
    }

    public void loadAllMusicsFromRemote(final LoadMusicsCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = mHttpClient
                            .newCall(new Request.Builder().url(FEED_TOP_SONGS_URL).build())
                            .execute();

                    FeedParser parser = new FeedParser();
                    List<Music> musics = parser.parse(response.body().byteStream());
                    callBack.onMusicsLoaded(musics);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    callBack.onError();
                }
            }
        };
        mAppExecutors.networkIO().execute(runnable);
    }
}
