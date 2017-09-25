package com.example.pcroize.poloapp;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Belal on 1/27/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Constants for Database name, table name, and column names...
    public static final String DB_NAME = "android";
    public static final String TABLE_NAME = "consumers";
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_DEVICEID = "deviceid";

    //database version
    private static final int DB_VERSION = 66;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_USERID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_EMAIL + " VARCHAR(100), " +
                COLUMN_FIRSTNAME + " VARCHAR(30), " +
                COLUMN_NAME + " VARCHAR(30), " +
                COLUMN_YEAR + " INTEGER ,"+
                COLUMN_MONTH + " INTEGER ,"+
                COLUMN_DAY + " INTEGER ,"+
                COLUMN_DEVICEID + " INTEGER "+
                ");";
        db.execSQL(sql);
    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(sql);
//        sql = "SELECT * FROM "+ TABLE_NAME;
//        db.execSQL(sql);
        Log.i("myInfo", "Dropped Table "+TABLE_NAME);
        onCreate(db);
    }

    /*
    * This method is taking two arguments
    * first one is the name that is to be saved
    * second one is the status
    * 0 means the name is synced with the server
    * 1 means the name is not synced with the server
    * */
    public boolean addName(ContentValues contentValues, int status) {
        SQLiteDatabase db = this.getWritableDatabase();

        String email = contentValues.getAsString(COLUMN_EMAIL);
        String firstname = contentValues.getAsString(COLUMN_FIRSTNAME);
        String name = contentValues.getAsString(COLUMN_NAME);
        String year = contentValues.getAsString(COLUMN_YEAR);
        String month = contentValues.getAsString(COLUMN_MONTH);
        String day = contentValues.getAsString(COLUMN_DAY);
        String deviceid = contentValues.getAsString(COLUMN_DEVICEID);

        Log.i("myInfo", email +"-"+ firstname +"-"+ name +"-"+ year +"-"+ month +"-"+ day +"-"+ deviceid);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    /*
    * This method taking two arguments
    * first one is the id of the name for which
    * we have to update the sync status
    * and the second one is the status that will be changed
    * */
    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERID, status);
        db.update(TABLE_NAME, contentValues, COLUMN_USERID + "=" + id, null);
        db.close();
        return true;
    }

    /*
    * this method will give us all the name stored in sqlite
    * */
    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_USERID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /*
    * this method is for getting all the unsynced name
    * so that we can sync it with database
    * */
    public Cursor getUnsyncedNames() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERID + " = 0;";
        Cursor c = null;// db.rawQuery(sql, null);
        return c;
    }
}