package com.example.mvvm_test_application;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static final int THREADS = 10;
    public static NoteDatabase instance;
    public ExecutorService service;

    public abstract NoteDao noteDao();

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
