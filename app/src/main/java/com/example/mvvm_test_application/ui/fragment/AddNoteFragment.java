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
 * The type Add note fragment.
 */
public class AddNoteFragment extends Fragment {
    private NoteViewModel noteViewModel;
    private View view;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private NumberPicker numberPicker;


    /**
     * Instantiates a new Add note fragment.
     */
    public AddNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleEditText = view.findViewById(R.id.edit_title);
        descriptionEditText = view.findViewById(R.id.edit_description);
        numberPicker = view.findViewById(R.id.number_picker);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_note, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_note_menu, menu);
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
                System.out.println(note);
                saveNote(note);
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

    private void saveNote(Note note) {
        System.out.println("Saving notes");
        noteViewModel.insert(note);
    }

}