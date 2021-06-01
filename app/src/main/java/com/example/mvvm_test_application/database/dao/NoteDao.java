package com.example.mvvm_test_application.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvm_test_application.database.model.Note;

import java.util.List;

/**
 * The interface Note dao.
 */
@Dao
public interface NoteDao {
    /**
     * Insert.
     *
     * @param note the note
     */
    @Insert
    void insert(Note note);

    /**
     * Update.
     *
     * @param note the note
     */
    @Update
    void update(Note note);

    /**
     * Delete.
     *
     * @param note the note
     */
    @Delete
    void delete(Note note);

    /**
     * Delete all notes.
     */
    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    /**
     * Gets all notes.
     *
     * @return the all notes
     */
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}
