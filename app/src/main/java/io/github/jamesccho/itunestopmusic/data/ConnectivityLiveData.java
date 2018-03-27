package io.github.jamesccho.itunestopmusic.data;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by james on 26/3/2018.
 */

public class ConnectivityLiveData extends LiveData<Boolean> {

    private Context mContext;

    private BroadcastReceiver mConnectivityBR;

    public ConnectivityLiveData(Context context) {
        mContext = context;
        mConnectivityBR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                postValue(isConnected);
            }
        };
    }

    @Override
    protected void onActive() {
        super.onActive();
        mContext.registerReceiver(mConnectivityBR,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mContext.unregisterReceiver(mConnectivityBR);
    }
}
