package com.example.mvvm_test_application.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_test_application.R;
import com.example.mvvm_test_application.adapter.NoteAdapter;
import com.example.mvvm_test_application.ui.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * The type Home fragment.
 */
public class HomeFragment extends Fragment {

    private NoteViewModel noteViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(requireActivity(), adapter::submitList);

        NavController navController = Navigation.findNavController(view);

        FloatingActionButton addButton = view.findViewById(R.id.add_button);
        adapter.setOnItemClickListener(note -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", note.getTitle());
            bundle.putString("description", note.getDescription());
            bundle.putInt("priority", note.getPriority());
            bundle.putInt("id", note.getId());
            navController.navigate(R.id.action_homeFragment_to_editNoteFragment, bundle, null, null);
        });
        addButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Yo Man!!!", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.action_homeFragment_to_addNoteFragment);
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
                Toast.makeText(getContext(), "Note Deleted!!!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_item_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_notes) {
            noteViewModel.deleteAllNotes();
            Toast.makeText(getContext(), "All Notes Deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}