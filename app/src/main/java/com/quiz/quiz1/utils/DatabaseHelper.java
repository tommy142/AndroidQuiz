package com.quiz.quiz1.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.quiz.quiz1.data.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";
    // User table name
    private static final String TABLE_USER = "user";
    // User table column names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_BIRTHDAY = "user_birthday";
    private static final String COLUMN_USER_GENDER = "user_gender";
    //Create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_BIRTHDAY + " TEXT,"
            + COLUMN_USER_GENDER + " TEXT" + ")";

    //droup table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        onCreate(sqLiteDatabase);
    }

    // method for add user
    public void addUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Insert values
        values.put(COLUMN_USER_NAME, user.getUsername());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_BIRTHDAY, user.getBirthday());
        values.put(COLUMN_USER_GENDER, user.getGender());

        sqLiteDatabase.insert(TABLE_USER, null, values);
        sqLiteDatabase.close();
    }
    //check Email only
    public boolean checkUser(String email){
        //array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection arguments
        String[] selectionArgs = {email};

        //Query user table with conditions
        Cursor cursor = sqLiteDatabase.query(TABLE_USER, //Table to query
                columns,                                //columns to return
                selection,                              //columns for WHERE clause
                selectionArgs,                          //The Values for the WHERE clause
                null,                           //group the rows
                null,                               // filter by row groups
                null                                 // the sort order
        );
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    //check Email and Password
    public boolean checkUser(String email, String password) {
        //array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};

        //Query user table with conditions
        Cursor cursor = sqLiteDatabase.query(TABLE_USER, //Table to query
                columns,                                //columns to return
                selection,                              //columns for WHERE clause
                selectionArgs,                          //The Values for the WHERE clause
                null,                           //group the rows
                null,                               // filter by row groups
                null                                 // the sort order
        );
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}
