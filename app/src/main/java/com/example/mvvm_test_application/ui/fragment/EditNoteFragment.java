package com.example.mvvm_test_application.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mvvm_test_application.R;
import com.example.mvvm_test_application.database.model.Note;
import com.example.mvvm_test_application.ui.viewmodel.NoteViewModel;

/**
 * The type Edit note fragment.
 */
public class EditNoteFragment extends Fragment {

    private NoteViewModel noteViewModel;
    private View view;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private NumberPicker numberPicker;
    private int note_id = -1;
    private String note_title = "";
    private String note_description = "";
    private int note_priority = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        if (getArguments() != null) {
            note_id = this.getArguments().getInt("id", -1);
            note_title = this.getArguments().getString("title");
            note_description = this.getArguments().getString("description");
            note_priority = this.getArguments().getInt("priority");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleEditText = view.findViewById(R.id.edit_title);
        descriptionEditText = view.findViewById(R.id.edit_description);
        numberPicker = view.findViewById(R.id.number_picker);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);

        if (note_id != -1 && !note_title.equals("") && !note_description.equals("")) {
            titleEditText.setText(note_title);
            descriptionEditText.setText(note_description);
            numberPicker.setValue(note_priority);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_note_menu, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        System.out.println(item.getItemId());
        if (item.getItemId() == R.id.save_note) {
            String description = descriptionEditText.getText().toString();
            String title = titleEditText.getText().toString();
            int priority = numberPicker.getValue();
            if (!title.trim().isEmpty() && !description.trim().isEmpty()) {
                Note note = new Note(title, description, priority);
                note.setId(note_id);
                System.out.println(note);
                updateNote(note);
                Toast.makeText(getContext(), "Note Saved", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).popBackStack();
                return true;
            } else {
                System.out.println("Insert all items");
                Toast.makeText(getContext(), "Insert all items", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else
            return super.onOptionsItemSelected(item);
    }

    private void updateNote(Note note) {
        System.out.println("Updating notes");
        noteViewModel.update(note);
    }

}