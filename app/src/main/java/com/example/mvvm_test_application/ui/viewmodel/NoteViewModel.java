package com.example.mvvm_test_application.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvm_test_application.database.model.Note;
import com.example.mvvm_test_application.repository.NoteRepository;

import java.util.List;

/**
 * The type Note view model.
 */
public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;

    /**
     * Instantiates a new Note view model.
     *
     * @param application the application
     */
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    /**
     * Insert.
     *
     * @param note the note
     */
    public void insert(Note note) {
        noteRepository.insert(note);
    }

    /**
     * Update.
     *
     * @param note the note
     */
    public void update(Note note) {
        noteRepository.update(note);
    }

    /**
     * Delete.
     *
     * @param note the note
     */
    public void delete(Note note) {
        noteRepository.delete(note);
    }

    /**
     * Delete all notes.
     */
    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }

    /**
     * Gets all notes.
     *
     * @return the all notes
     */
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }


    public int getNumber(int x) {
        return x * x;
    }
}
