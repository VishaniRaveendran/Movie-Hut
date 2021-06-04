package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchMovieActivity extends AppCompatActivity {

    MovieDatabaseHelper movieDatabaseHelper;
    private EditText editSearch;
    private Button editButton;
    private TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        editSearch = findViewById(R.id.etSearch);
        editButton = findViewById(R.id.editButton);
        tvSearch = findViewById(R.id.tvSearch);

        movieDatabaseHelper = new MovieDatabaseHelper(this);
    }

    public void viewAll(View view) {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (!editSearch.getText().toString().equals("")) {
            tvSearch.setText("");
            String let = editSearch.getText().toString();
            Cursor cursor = movieDatabaseHelper.search(let);

            if (cursor.getCount() == 0) {
                Toast.makeText(SearchMovieActivity.this, "Search not found", Toast.LENGTH_LONG).show();
            } else {

                while (cursor.moveToNext()) {
                    if (!(tvSearch.getText().toString().equals(cursor.getString(0).toLowerCase())))
                        tvSearch.append(cursor.getString(0) + "\n");
                }
            }
        }
        editSearch.setText("");
    }
}
