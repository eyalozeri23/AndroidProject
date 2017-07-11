package com.eyal.eyalo.birthdayremainderapp.infrastracture;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eyalo on 2/7/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "eyalDB.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "BirthdayContacts";
    public static final String COL_ID = "_id";
    public static final String COL_FIRST_NAME = "firstName";
    public static final String COL_LAST_NAME = "lastName";
    public static final String COL_PHONE = "phone";
    public static final String COL_BIRTHDAY = "birthday";
    public static final String COL_BIRTHDAY_TEXT = "birthdayText";
    public static final String COL_IMAGE_FILE_URI = "imgFileUri";

    private static final String CREATE_TABLE_COMMAND = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_FIRST_NAME + " TEXT, " +
            COL_LAST_NAME + " TEXT, " +
            COL_PHONE + " TEXT, " +
            COL_BIRTHDAY + " TEXT, " +
            COL_BIRTHDAY_TEXT + " TEXT, " +
            COL_IMAGE_FILE_URI + " TEXT)";

    private static final String DELETE_TABLE_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COMMAND);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_COMMAND);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }


}
