package com.example.mvvm_test_application;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.mvvm_test_application_EXTRA_ID";
    private NoteViewModel noteViewModel;
    public static final int ADD_NOTE_REQUEST = 1;
    private ActivityResultLauncher<Intent> addNoteActivityResultLauncher;
    private ActivityResultLauncher<Intent> editNoteActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        addNoteActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
                            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
                            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);
                            noteViewModel.insert(new Note(title, description, priority));
                            Toast.makeText(MainActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Note Not Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        editNoteActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int id = data.getIntExtra(EXTRA_ID, -1);
                        if (id == -1) {
                            Toast.makeText(this, "Note can't be updated!!!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (result.getResultCode() == Activity.RESULT_OK) {
                            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
                            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
                            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

                            Note note = new Note(title, description, priority);
                            note.setId(id);
                            noteViewModel.update(note);

                            Toast.makeText(MainActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Note Not Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            addNoteActivityResultLauncher.launch(intent);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted!!!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(note -> {
            Intent data = new Intent(MainActivity.this, AddNoteActivity.class);
            data.putExtra(AddNoteActivity.EXTRA_TITLE, note.getTitle());
            data.putExtra(AddNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
            data.putExtra(AddNoteActivity.EXTRA_PRIORITY, note.getPriority());
            data.putExtra(EXTRA_ID, note.getId());
            editNoteActivityResultLauncher.launch(data);
        });

        noteViewModel = new ViewModelProvider(this,
                ViewModelProvider
                        .AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                .get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
//                should update recycler view
                adapter.submitList(notes);
                Toast.makeText(MainActivity.this, "AAAAAAAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}