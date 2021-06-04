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

public class FavouritesMovieActivity extends AppCompatActivity {
    //------Declaration checkedItems as arrayList-----//
    ArrayList<String> checkedItems1 = new ArrayList<>();
    //------Declaration of listView ------//
    ListView ListViewFavourite;
    //------Declaration of save button ------//
    Button saveButton;
    //------Declaration checkedItems as arrayList-----//
    MovieDatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_movie);

        databaseHelper = new MovieDatabaseHelper(this);
        //-----------Getting the id of listView------------//
        ListViewFavourite = (ListView) findViewById(R.id.list_view_display);
        //----------Getting the id of save button--------//
        saveButton = (Button) findViewById(R.id.save);
        //-----TO select multiple checkbox---------//
        ListViewFavourite.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        populateFavouriteListView();
    }

    /**Reference : https://developer.android.com/reference/android/widget/ListView**/
    //-----------To populate the register movie to store in database------------//
    private void populateFavouriteListView() {
        //--------Get all favourite movies to the listView--------//
        Cursor data = databaseHelper.displayFavourite();
        ArrayList<String> listData1 = new ArrayList<>();
        while (data.moveToNext()) {
            listData1.add(data.getString(0));
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.checkbox, R.id.txt_favourite, listData1);
        ListViewFavourite.setAdapter(adapter);


        for(int x=0; x<listData1.size(); x++)
            ListViewFavourite.setItemChecked(x, true);
        ListViewFavourite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //--------------selected item----------------------//
                String selectedItem = ((TextView) view).getText().toString();
                if (checkedItems1.contains(selectedItem))
                    checkedItems1.remove(selectedItem);
                else
                    checkedItems1.add(selectedItem);

            }
        });


    }

    public void saveFavouriteStatus(View view){

        String checkItems = "";
        for (String item : checkedItems1) {
            databaseHelper.addToUnFavouriteList(item);

            if (checkItems == "") {
                checkItems = item;

            }
            else
                checkItems += "/" + item;

        }
        Toast.makeText(this, checkItems+" "+"removed from favorites", Toast.LENGTH_LONG).show();


    }


}
