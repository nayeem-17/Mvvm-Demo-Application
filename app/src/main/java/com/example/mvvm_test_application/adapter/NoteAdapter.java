package com.example.mvvm_test_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

//import org.jetbrains.annotations.NotNull;

import com.example.mvvm_test_application.R;
import com.example.mvvm_test_application.database.model.Note;

/**
 * The type Note adapter.
 */
public class NoteAdapter extends ListAdapter<Note, NoteAdapter.Noteholder> {
    private OnItemClickListener onItemClickListener;
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority() &&
                    oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    /**
     * Instantiates a new Note adapter.
     */
    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public Noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);
        return new Noteholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.Noteholder holder, int position) {
        Note currentNote = getItem(position);
        holder.titleTextView.setText(currentNote.getTitle());
        holder.descriptionTextView.setText(currentNote.getDescription());
        holder.priorityTextView.setText(String.valueOf(currentNote.getPriority()));
    }

    /**
     * Gets note.
     *
     * @param index the index
     * @return the note
     */
    public Note getNote(int index) {
        return getItem(index);
    }

    /**
     * The type Noteholder.
     */
    class Noteholder extends RecyclerView.ViewHolder {

        private TextView priorityTextView;
        private TextView descriptionTextView;
        private TextView titleTextView;

        /**
         * Instantiates a new Noteholder.
         *
         * @param itemView the item view
         */
        public Noteholder(@NonNull View itemView) {
            super(itemView);
            priorityTextView = itemView.findViewById(R.id.text_view_priority);
            descriptionTextView = itemView.findViewById(R.id.text_view_description);
            titleTextView = itemView.findViewById(R.id.text_view_title);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(getItem(position));
            });
        }
    }

    /**
     * The interface On item click listener.
     */
    public interface OnItemClickListener {
        /**
         * On item click.
         *
         * @param note the note
         */
        void onItemClick(Note note);
    }

    /**
     * Sets on item click listener.
     *
     * @param onItemClickListener the on item click listener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
