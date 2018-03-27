package io.github.jamesccho.itunestopmusic.data;

import java.util.List;

import io.github.jamesccho.itunestopmusic.data.model.Music;

/**
 * Created by james on 25/3/2018.
 */

public interface LoadMusicsCallBack {
    void onMusicsLoaded(List<Music> musics);
    void onError();
}
