package com.example.mvvm_test_application.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mvvm_test_application.database.dao.NoteDao;
import com.example.mvvm_test_application.database.model.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Note database.
 */
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static final int THREADS = 10;
    /**
     * The constant instance.
     */
    public static NoteDatabase instance;
    /**
     * The Service.
     */
    public ExecutorService service;

    /**
     * Note dao note dao.
     *
     * @return the note dao
     */
    public abstract NoteDao noteDao();

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

            instance.service = Executors.newFixedThreadPool(THREADS);
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newFixedThreadPool(1).execute(() -> {
                instance.noteDao().insert(new Note("Title2 ", "Description 2 ", 2));
                instance.noteDao().insert(new Note("Title1 ", "Description 1 ", 1));
                instance.noteDao().insert(new Note("Title3 ", "Description 3 ", 3));
            });
        }
    };
}
