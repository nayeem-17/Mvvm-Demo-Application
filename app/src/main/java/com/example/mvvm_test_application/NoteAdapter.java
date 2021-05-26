package com.example.mvvm_test_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.Noteholder> {
    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public Noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);
        return new Noteholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.Noteholder holder, int position) {
        Note currentNote = notes.get(position);
        holder.titleTextView.setText(currentNote.getTitle());
        holder.descriptionTextView.setText(currentNote.getDescription());
        holder.priorityTextView.setText(String.valueOf(currentNote.getPriority()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class Noteholder extends RecyclerView.ViewHolder {

        private TextView priorityTextView;
        private TextView descriptionTextView;
        private TextView titleTextView;

        public Noteholder(@NonNull View itemView) {
            super(itemView);
            priorityTextView = itemView.findViewById(R.id.text_view_priority);
            descriptionTextView = itemView.findViewById(R.id.text_view_description);
            titleTextView = itemView.findViewById(R.id.text_view_title);
        }
    }
}
