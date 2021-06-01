package com.example.mvvm_test_application.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mvvm_test_application.database.model.Note;
import com.example.mvvm_test_application.database.dao.NoteDao;
import com.example.mvvm_test_application.database.NoteDatabase;

import java.util.List;

/**
 * The type Note repository.
 */
public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private NoteDatabase noteDatabase;

    /**
     * Instantiates a new Note repository.
     *
     * @param application the application
     */
    public NoteRepository(Application application) {
        noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    /**
     * Delete.
     *
     * @param note the note
     */
    public void delete(Note note) {
        noteDatabase.service.execute(() -> {
            noteDao.delete(note);
        });
    }

    /**
     * Insert.
     *
     * @param note the note
     */
    public void insert(Note note) {
        noteDatabase.service.execute(() -> {
            noteDao.insert(note);
        });
    }

    /**
     * Update.
     *
     * @param note the note
     */
    public void update(Note note) {
        noteDatabase.service.execute(() -> {
            noteDao.update(note);
        });
    }

    /**
     * Delete all.
     */
    public void deleteAll() {
        noteDatabase.service.execute(() -> {
            noteDao.deleteAllNotes();
        });
    }

    /**
     * Gets all notes.
     *
     * @return the all notes
     */
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
