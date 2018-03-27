package io.github.jamesccho.itunestopmusic.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.github.jamesccho.itunestopmusic.data.model.Music;

/**
 * Created by james on 25/3/2018.
 */
@Database(entities = Music.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "top-music.db";

    private static AppDatabase INSTANCE;

    public abstract MusicDao musicDao();

    public static AppDatabase getInstance(Context context) {
        synchronized (AppDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DB_NAME)
                        .build();
            }
        }
        return INSTANCE;
    }
}
