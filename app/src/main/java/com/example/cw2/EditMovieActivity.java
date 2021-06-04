package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class EditMovieActivity extends AppCompatActivity {

    //------Declaration of MovieDatabaseHelper Class-----//
    MovieDatabaseHelper movieDatabaseHelper;
    //-----Declaration of editText------------//
    EditText et_title, et_year, et_director, et_actors,et_rating,et_review, et_favourite;
    //------Declaration of button-----//
    Button update_button;
    //-------StarBar------//
    RatingBar starBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        movieDatabaseHelper = new MovieDatabaseHelper(this);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("title");
        Toast.makeText(EditMovieActivity.this, name, Toast.LENGTH_LONG).show();

        //---------EditText is assign using id----------//
        et_title = (EditText) findViewById(R.id.titleOfTheMovie_et);
        et_year = (EditText) findViewById(R.id.year_et);
        et_director = (EditText) findViewById(R.id.director_et);
        starBar = findViewById(R.id.rating_star);
        et_review = (EditText) findViewById(R.id.review_et);
        et_actors= (EditText) findViewById(R.id.actor_actresses_et);
        et_favourite=(EditText) findViewById(R.id.favourite_et);
        update_button = (Button) findViewById(R.id.update_button);

        fillingValues(name);
    }

    //-----------------Get the values from database------------//
    public void fillingValues(String name){
        Cursor res = movieDatabaseHelper.search(name);
        if (res.getCount() == 0) {
            Toast.makeText(EditMovieActivity.this, "Nothing to show", Toast.LENGTH_LONG).show();
        } else {
            while (res.moveToNext()) {
                et_title.append(res.getString(0));
                et_year.append(res.getString(1));
                et_director.append(res.getString(2));
                et_actors.append(res.getString(3));
                String rateNum =res.getString(4);
                starBar.setRating(Float.parseFloat(rateNum));
//                et_rating.append(res.getString(4));
                et_review.append(res.getString(5));
                et_favourite.append(res.getString(6));
                if(res.getString(6).equals("true")) {
                    et_favourite.setText("Favourite");
                }else{
                    et_favourite.setText("Not Favourite");
                }
            }
        }
    }
    //-------------update data-------------------//
    public void updateData(View v){
        int ratingint = starBar.getProgress();
        boolean isUpdate = movieDatabaseHelper.updateEditData(
                et_title.getText().toString(),
                et_year.getText().toString(),
                et_director.getText().toString(),
                et_actors.getText().toString(),
                ratingint,
                et_review.getText().toString(),
                et_favourite.getText().toString());
        System.out.println("progesss: " + starBar.getProgress());



        if (isUpdate == true) {
            Toast.makeText(EditMovieActivity.this, "Movie Details Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,EditMainMoviesActivity.class);
            super.finish();
            startActivity(intent);

        } else {
            Toast.makeText(EditMovieActivity.this, "Movie Detail Is Not Updated", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public void finish() {
        Intent intent = new Intent(this,EditMainMoviesActivity.class);
        super.finish();
        startActivity(intent);
    }

}