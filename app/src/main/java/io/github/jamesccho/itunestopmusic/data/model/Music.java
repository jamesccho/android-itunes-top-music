package io.github.jamesccho.itunestopmusic.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by james on 25/3/2018.
 */
@Entity
public class Music {
    @PrimaryKey
    @NonNull
    public String id;
    public String title;
    public String artist;
    public String imageUrl;

    public Music(String id, String title, String artist, String imageUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.imageUrl = imageUrl;
    }
}
