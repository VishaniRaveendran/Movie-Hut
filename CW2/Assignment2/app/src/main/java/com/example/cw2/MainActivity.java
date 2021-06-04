package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/*
 * References:
 * https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-1-get-started/lesson-2-activities-and-intents/2-1-c-activities-and-intents/2-1-c-activities-and-intents.html
 * https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-1-get-started/lesson-2-activities-and-intents/2-2-c-activity-lifecycle-and-state/2-2-c-activity-lifecycle-and-state.html
 * Lecture 3 - Materials
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchRegisterMovieActivity(View view) {
        Intent registerMovieIntent = new Intent(this, RegisterMovie.class);
        startActivity(registerMovieIntent);
    }

    public void launchDisplayMovieActivity(View view) {
        Intent displayMovieIntent = new Intent(this, DisplayMovie.class);
        startActivity(displayMovieIntent);
    }

    public void launchFavoriteMovieActivity(View view) {
        Intent registerMovieIntent = new Intent(this, FavouritesMovieActivity.class);
        startActivity(registerMovieIntent);
    }

    public void launchEditMovieActivity(View view) {
        Intent editMovieIntent = new Intent(this, EditMainMoviesActivity.class);
        startActivity(editMovieIntent);
    }

    public void launchSearchMovieActivity(View view) {
        Intent searchMovieIntent = new Intent(this, SearchMovieActivity.class);
        startActivity(searchMovieIntent);
    }

    public void launchRatingMovieActivity(View view) {
        Intent ratingMovieIntent = new Intent(this, RatingMovieActivity.class);
        startActivity(ratingMovieIntent);
    }

}