package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditMainMoviesActivity extends AppCompatActivity {
    //------Declaration of MovieDatabaseHelper Class-----//
    MovieDatabaseHelper movieDatabaseHelper;
    //-----Declaration of listView-----------------//
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_main_movies);

        movieDatabaseHelper = new MovieDatabaseHelper(this);
        //---------EditText is assign using id----------//
        listView = (ListView) findViewById(R.id.List_View_Display);
        viewAllData();
    }

    public void viewAllData() {
        Cursor res = movieDatabaseHelper.displayMovie();
        ArrayList<String> items = new ArrayList<>();

        if (res.getCount() == 0) {
            Toast.makeText(EditMainMoviesActivity.this, "No data is Available", Toast.LENGTH_LONG).show();
        } else {
            while (res.moveToNext()) {
                String name = res.getString(0);
                items.add(name);

                ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
                listView.setAdapter(aa);

                //---------setting on action for each list item------------//
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = ((TextView) view).getText().toString();
                        Intent intent = new Intent();
                        intent.setClass(EditMainMoviesActivity.this, EditMovieActivity.class);
                        intent.putExtra("title", selectedItem);
                        finish();
                        startActivity(intent);

                    }
                });



            }
        }
    }

}