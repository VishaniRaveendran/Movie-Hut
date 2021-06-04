package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayMovie extends AppCompatActivity {

    //------Declaration checkedItems as arrayList-----//
    ArrayList<String> checkedItems = new ArrayList<>();
    //------Declaration of MovieDatabaseHelper Class-----//
    MovieDatabaseHelper databaseHelper;
    //------Declaration of listView ------//
    ListView listView;
    //------Declaration of add to favourite button ------//
    Button addToFavouriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        databaseHelper = new MovieDatabaseHelper(this);
        //-----------Getting the id of listView------------//
        listView = findViewById(R.id.List_View_Display);
        //-----TO select multiple checkbox---------//
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //----------Getting the id of favourite button--------//
        addToFavouriteButton = findViewById(R.id.addToFavorite);
        displayAllRegisteredMovie();
    }

    /**Reference : https://developer.android.com/reference/android/widget/ListView**/
    //-----------To populate the register movie to store in database------------//
    public void displayAllRegisteredMovie(){

        ArrayList<String> arrayList = new ArrayList<>();

        //--------Get all register movie to the listView--------//
        Cursor cursor = databaseHelper.getAllData();
        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0));
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_display, R.id.txt_title, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //------------Selected item-----------//
                String selectedItem = ((TextView) view).getText().toString();
                if (checkedItems.contains(selectedItem))
                    checkedItems.remove(selectedItem);
                else
                    checkedItems.add(selectedItem);

            }
        });
    }

    /**REFERENCE: https://stackoverflow.com/questions/8726272/get-checked-items-from-listview-in-android**/
    //-------------------Add selected items as favourite to data base-------------//
    public void addToFavourite(View view) {
        String checkItems = "";
        for (String item : checkedItems) {
            databaseHelper.addToFavouriteList(item);
            if (checkItems == "")
                checkItems = item;
            else
                checkItems += "/" + item;
        }
        Toast.makeText(this, checkItems+" "+"added", Toast.LENGTH_LONG).show();

    }
}