package com.example.mvvm_test_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.example.mvvm_test_application.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.mvvm_test_application.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.mvvm_test_application.EXTRA_PRIORITY";

    private EditText titleEditText;
    private EditText descriptionEditText;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleEditText = findViewById(R.id.edit_title);
        descriptionEditText = findViewById(R.id.edit_description);

        numberPicker = findViewById(R.id.number_picker);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.EXTRA_ID)) {
            setTitle("Edit Note");
            titleEditText.setText(intent.getStringExtra(AddNoteActivity.EXTRA_TITLE));
            descriptionEditText.setText(intent.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1));
        } else {
            setTitle("Edit Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int priority = numberPicker.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = getIntent().getIntExtra(MainActivity.EXTRA_ID, -1);

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);
        if (id != -1) {
            data.putExtra(MainActivity.EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}