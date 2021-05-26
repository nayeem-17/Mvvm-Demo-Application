package com.example.mvvm_test_application;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private NoteDatabase noteDatabase;

    public NoteRepository(Application application) {
        noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void delete(Note note) {
        noteDatabase.service.execute(() -> {
            noteDao.delete(note);
        });
    }

    public void insert(Note note) {
        noteDatabase.service.execute(() -> {
            noteDao.insert(note);
        });
    }

    public void update(Note note) {
        noteDatabase.service.execute(() -> {
            noteDao.update(note);
        });
    }

    public void deleteAll() {
        noteDatabase.service.execute(() -> {
            noteDao.deleteAllNotes();
        });
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
