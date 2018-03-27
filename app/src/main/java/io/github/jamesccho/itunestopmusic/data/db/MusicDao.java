package io.github.jamesccho.itunestopmusic.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.github.jamesccho.itunestopmusic.data.model.Music;

/**
 * Created by james on 25/3/2018.
 */
@Dao
public interface MusicDao {

    @Query("SELECT * FROM music")
    LiveData<List<Music>> loadAllMusics();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Music> musics);

    @Query("DELETE FROM music")
    void deleteAll();

}
