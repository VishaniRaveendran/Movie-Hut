package com.example.cw2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.Year;
import java.util.Arrays;
/**
 * References:
 * https://www.tutorialspoint.com/android/android_sqlite_database.htm
 * https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-4-saving-user-data/lesson-10-storing-data-with-room/10-0-c-sqlite-primer/10-0-c-sqlite-primer.html
 * https://developer.android.com/training/data-storage/sqlite#java
 *- Lecture 5 - Materials
 **/

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    //-------Inner class that defines the table contents-----------//
    private static final String DATABASE_NAME = "movie.db";
    public static final String TABLE_NAME1 = "movieList_table";
    public static final String COLUMN_1 = "TITLE";
    public static final String COLUMN_2 = "YEAR";
    public static final String COLUMN_3 = "DIRECTOR";
    public static final String COLUMN_4 = "MOVIECAST";
    public static final String COLUMN_5 = "RATING";
    public static final String COLUMN_6 = "REVIEW";
    public static final String COLUMN_7 = "FAVOURITES";



    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME1 +" (" + COLUMN_1 + " TEXT PRIMARY KEY , " + COLUMN_2 + " NUMBER, "+COLUMN_3 + " TEXT, "+COLUMN_4 + " TEXT,"+COLUMN_5 + " NUMBER, "+COLUMN_6 + " TEXT,"+COLUMN_7 + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    //---------------To insert data into the database----------//
    public Boolean insertData(String title , String year , String director , String actorActress ,String rating , String review ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, title);
        contentValues.put(COLUMN_2, year);
        contentValues.put(COLUMN_3, director);
        contentValues.put(COLUMN_4, actorActress);
        contentValues.put(COLUMN_5, rating);
        contentValues.put(COLUMN_6, review);
        contentValues.put(COLUMN_7, "NULL");
        long result = db.insert(TABLE_NAME1, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    //----------To get all the columns in movie details table----------//
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME1 + " order by " + COLUMN_1 + " collate NOCASE asc;" ,null);
        System.out.println(Arrays.toString(cursor.getColumnNames()));
        return cursor;
    }

    //---------To add favourite details to the favourite column-----------//
    public void addToFavouriteList(String TITLE){
        SQLiteDatabase db = this.getWritableDatabase();
        String test = "UPDATE "+TABLE_NAME1+" SET "+COLUMN_7+" = 'true' WHERE TITLE = '"+TITLE+"'";
        System.out.println(test);
        db.execSQL("UPDATE "+TABLE_NAME1+" SET "+COLUMN_7+" = 'true' WHERE TITLE = '"+TITLE+"'");
    }

    //---------To add not favourite details to the favourite column-----------//
    public void addToUnFavouriteList(String TITLE){
        SQLiteDatabase db = this.getWritableDatabase();
        String test = "UPDATE "+TABLE_NAME1+" SET "+COLUMN_7+" = 'false' WHERE TITLE = '"+TITLE+"'";
        System.out.println(test);
        db.execSQL("UPDATE "+TABLE_NAME1+" SET "+COLUMN_7+" = 'false' WHERE TITLE = '"+TITLE+"'");
    }

    //---------To save the details of favourite movies-----------//
    public Cursor displayFavourite(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from  "+TABLE_NAME1+" WHERE "+COLUMN_7+"= 'true'",null);
        return result;
    }

    //---------To display movies-----------//
    public Cursor displayMovie(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result=db.rawQuery("SELECT * FROM "+ TABLE_NAME1 + " ORDER BY "+ COLUMN_1 +" ASC", new String[] {} );
        System.out.println(Arrays.toString(result.getColumnNames()));
        return result;
    }

    //---------To update Edit data----------//
    public boolean updateEditData(String title, String year, String director, String actorActress, int rating, String review, String favourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, title);
        contentValues.put(COLUMN_2, year);
        contentValues.put(COLUMN_3, director);
        contentValues.put(COLUMN_4, actorActress);
        contentValues.put(COLUMN_5, rating);
        contentValues.put(COLUMN_6, review);
        contentValues.put(COLUMN_7, favourite);
        db.update(TABLE_NAME1, contentValues, "TITLE = ?",new String[] { title });
        return true;
    }

    //-------------For search movies----------//
    public Cursor search(String letters){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME1+" WHERE TITLE OR DIRECTOR OR MOVIECAST LIKE " +
                "'"+letters+"%' OR TITLE LIKE '%"+letters+"%' OR DIRECTOR LIKE '%"+letters+"%' OR MOVIECAST LIKE '%"+letters+"'",null);

    }

}
