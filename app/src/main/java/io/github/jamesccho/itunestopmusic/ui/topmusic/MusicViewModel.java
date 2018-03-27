package io.github.jamesccho.itunestopmusic.ui.topmusic;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.github.jamesccho.itunestopmusic.BaseApp;
import io.github.jamesccho.itunestopmusic.R;
import io.github.jamesccho.itunestopmusic.data.ConnectivityLiveData;
import io.github.jamesccho.itunestopmusic.data.LoadMusicsCallBack;
import io.github.jamesccho.itunestopmusic.data.model.Music;

/**
 * Created by james on 25/3/2018.
 */

public class MusicViewModel extends AndroidViewModel {

//    private MutableLiveData<List<Music>> mMusics;
    private final BaseApp mBaseApp;
    private ConnectivityLiveData mIsNetworkConnected;
    private MutableLiveData<Boolean> mShowLoadingIndicator;
    private MutableLiveData<Integer> mToastMessageResId;
    private boolean isRefreshing = false;


    public MusicViewModel(@NonNull Application application) {
        super(application);
        mBaseApp = (BaseApp) application;
        mIsNetworkConnected = new ConnectivityLiveData(application);
        mShowLoadingIndicator = new MutableLiveData<>();
        mShowLoadingIndicator.setValue(false);
        mToastMessageResId = new MutableLiveData<>();
    }

    public LiveData<Boolean> isNetworkConnected() {
        return mIsNetworkConnected;
    }

    public LiveData<Boolean> showLoadingIndicator() {
        return mShowLoadingIndicator;
    }

    public LiveData<Integer> getToastMessageResId() {
        return mToastMessageResId;
    }

    public LiveData<List<Music>> getAllMusics() {
//        if (mMusics == null) {
//            mMusics = new MutableLiveData<>();
//        }
//        return mMusics;
        return mBaseApp.getDataRepository().loadAllMusicsFromLocal();
    }

    public void refresh() {
        if (!isRefreshing) {
            mShowLoadingIndicator.setValue(true);
            isRefreshing = true;
            mBaseApp.getDataRepository().loadAllMusicsFromRemote(new LoadMusicsCallBack() {
                @Override
                public void onMusicsLoaded(List<Music> musics) {
                    mBaseApp.getDataRepository().saveAllMusics(musics);
//                mMusics.postValue(musics);
                    isRefreshing = false;
                    mShowLoadingIndicator.postValue(false);

                }

                @Override
                public void onError() {
                    mToastMessageResId.postValue(R.string.network_error);
//                    mBaseApp.getHandler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(mBaseApp.getApplicationContext(),
//                                    R.string.network_error, Toast.LENGTH_SHORT).show();
//                        }
//                    });

                    isRefreshing = false;
                    mShowLoadingIndicator.postValue(false);
                }
            });
        }
    }
}
