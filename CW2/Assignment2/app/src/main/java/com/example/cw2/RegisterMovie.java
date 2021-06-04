package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterMovie extends AppCompatActivity {
    //------Declaration of MovieDatabaseHelper Class-----//
    MovieDatabaseHelper databaseHelper;
    //------Declaration of titleOfTheMovie editText------//
    EditText titleOfTheMovie;
    //------Declaration of year editText------//
    EditText year;
    //------Declaration of director editText------//
    EditText director;
    //------Declaration of movieCast editText------//
    EditText movieCast;
    //------Declaration of ratings editText------//
    EditText ratings;
    //------Declaration of review editText------//
    EditText review;
    //------Declaration of save button ------//
    Button saveButton;
    //----------Assigning minimum year as 1895--------//
    int minimumYear = 1895;
    int maximumYear = 2021;
    //------Defining Array list-----------//
    ArrayList<String> movieArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        databaseHelper = new MovieDatabaseHelper(this);
        //---------EditText is assign to titleOfTheMovie using id----------//
        titleOfTheMovie = findViewById(R.id.titleOfTheMovie_et);
        //---------EditText is assign to year using id----------//
        year = findViewById(R.id.year_et);
        //---------EditText is assign to director using id----------//
        director = findViewById(R.id.director_et);
        //---------EditText is assign to movieCast using id----------//
        movieCast = findViewById(R.id.actor_actresses_et);
        //---------EditText is assign to ratings using id----------//
        ratings = findViewById(R.id.ratings_et);
        //---------EditText is assign to review using id----------//
        review = findViewById(R.id.review_et);
        //---------Button is assign to saveButton using id----------//
        saveButton = findViewById(R.id.update_button);

        /**Reference : https://stackoverflow.com/questions/41505465/making-edittext-accept-a-range-of-values-without-post-validation----**/
        //-------------Setting range for movie rating between 1 - 10-------------//
        int minRatingValue = 1;
        int maxRatingValue = 10;

        /**Reference :https://developer.android.com/reference/android/text/InputFilter**/
        //------------Setting input type for numeric text----------//
        ratings.setInputType(InputType.TYPE_CLASS_NUMBER);

        ratings.setFilters(new InputFilter[]{
                new InputFilterMinMax(minRatingValue, maxRatingValue),
                new InputFilter.LengthFilter(String.valueOf(maxRatingValue).length())
        });
        ratings.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

    }


    public void saveButton(View v) {
        //----To get the content from user and to store them----//
        String movieTitleText = titleOfTheMovie.getText().toString();
        String movieYearText = year.getText().toString();
        String movieDirectorText = director.getText().toString();
        String movieCastText = movieCast.getText().toString();
        String movieRatingText = ratings.getText().toString();
        String movieReviewText = review.getText().toString();

        Cursor cursor = databaseHelper.getAllData();
        while (cursor.moveToNext()) {
            if (Integer.valueOf(movieYearText) < minimumYear) {
                year.setTextColor(Color.RED);
                Toast.makeText(RegisterMovie.this, "Year textbox accept only numeric values which are greater than 1895", Toast.LENGTH_SHORT).show();
            }

            if (Integer.valueOf(movieYearText) > maximumYear) {
                year.setTextColor(Color.RED);
                Toast.makeText(RegisterMovie.this, "Year textbox accept only numeric values which are lesser than 2021", Toast.LENGTH_SHORT).show();
            }
            movieArrayList.add(cursor.getString(0));
        }


        //-------------Converts different types of values into string------------//
        String addedMovieName = String.valueOf(titleOfTheMovie.getText());

        //--------------Checking whether movie is added or not-------------------//
        boolean foundMovie = movieArrayList.contains(addedMovieName);
        if (foundMovie) {
            Toast.makeText(RegisterMovie.this, "This movie is already added", Toast.LENGTH_SHORT).show();
            titleOfTheMovie.setText("");
            year.setText("");
            year.setTextColor(Color.BLACK);
            movieCast.setText("");
            director.setText("");
            ratings.setText("");
            review.setText("");
        }

        //------------------Checking the movie year is greater than 1895---------------//
        else if (Integer.valueOf(movieYearText) < minimumYear ) {
            year.setTextColor(Color.RED);
            Toast.makeText(RegisterMovie.this, "Details are not added, Movies should be released after 1895", Toast.LENGTH_LONG).show();

        } else if (Integer.valueOf(movieYearText) > maximumYear) {
            year.setTextColor(Color.RED);
            Toast.makeText(RegisterMovie.this, "Details are not added, Movies should be released before 2021", Toast.LENGTH_LONG).show();

        } else {
            //------------------Checking if the data is inserted or not---------------//
            boolean checkInsertData = databaseHelper.insertData(movieTitleText, movieYearText, movieDirectorText, movieCastText, movieRatingText, movieReviewText);
            if (checkInsertData == true) {
                Toast.makeText(RegisterMovie.this, "Movie registered successfully", Toast.LENGTH_SHORT).show();

                //----------------clearing all the edit text details after adding movie details----------------//
                titleOfTheMovie.setText("");
                year.setText("");
                year.setTextColor(Color.BLACK);
                movieCast.setText("");
                director.setText("");
                ratings.setText("");
                review.setText("");

            } else {
                Toast.makeText(RegisterMovie.this, "Movie is not registered, TRY AGAIN", Toast.LENGTH_SHORT).show();
                titleOfTheMovie.setText("");
                year.setText("");
                year.setTextColor(Color.BLACK);
                movieCast.setText("");
                director.setText("");
                ratings.setText("");
                review.setText("");
            }
        }
    }


    /**
     * Reference : https://stackoverflow.com/questions/41505465/making-edittext-accept-a-range-of-values-without-post-validation
     */
    private class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int minRatingValue, int maxRatingValue) {
            this.min = minRatingValue;
            this.max = maxRatingValue;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}