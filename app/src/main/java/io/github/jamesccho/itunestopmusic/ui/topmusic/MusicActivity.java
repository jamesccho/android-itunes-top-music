package io.github.jamesccho.itunestopmusic.ui.topmusic;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.jamesccho.itunestopmusic.R;
import io.github.jamesccho.itunestopmusic.data.model.Music;

public class MusicActivity extends AppCompatActivity {

    private static final String TAG = "MusicActivity";

    private MusicViewModel mViewModel;

    private Button mRefreshButton;
    private TextView mMessageTextView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MusicAdapter mMusicAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_music);

        mViewModel = ViewModelProviders.of(this).get(MusicViewModel.class);

        mRefreshButton = findViewById(R.id.button_refresh);
        mMessageTextView = findViewById(R.id.text_view_message);
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler_view_music);

        mLayoutManager = new LinearLayoutManager(this);
        mMusicAdapter = new MusicAdapter();
        mRecyclerView.setAdapter(mMusicAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Refresh Clicked");
                mViewModel.refresh();
            }
        });

        observeViewModel();

    }

    private void observeViewModel() {
        mViewModel.getAllMusics().observe(this, new Observer<List<Music>>() {
            @Override
            public void onChanged(@Nullable List<Music> musics) {

                int size = 0;

                if (musics != null) {
                    size = musics.size();
                    mMusicAdapter.setDataSet(musics);
                }

                mMessageTextView.setVisibility(size == 0 ? View.VISIBLE : View.GONE);

            }
        });

        mViewModel.isNetworkConnected().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isConnected) {
//                mRefreshButton.setEnabled(isConnected != null && isConnected);
            }
        });

        mViewModel.showLoadingIndicator().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean showIndicator) {
                mProgressBar.setVisibility(showIndicator != null && showIndicator ? View.VISIBLE : View.GONE);
            }
        });

        mViewModel.getToastMessageResId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer resId) {
                if (resId != null) {
                    Toast.makeText(getApplicationContext(),
                            resId, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
