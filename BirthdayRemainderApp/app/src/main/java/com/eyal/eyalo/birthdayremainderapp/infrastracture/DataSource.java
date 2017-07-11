package com.eyal.eyalo.birthdayremainderapp.infrastracture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.EditText;

/**
 * Created by eyalo on 2/7/2017.
 */


public class DataSource {

    private SQLiteDatabase db;

    public DataSource(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    // -- insert data --//
    public long insert(String firstName, String lastName, String phone, String birthday, String birthdayText, String fileName) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COL_FIRST_NAME, firstName);
        values.put(DBOpenHelper.COL_LAST_NAME, lastName);
        values.put(DBOpenHelper.COL_PHONE, phone);
        values.put(DBOpenHelper.COL_BIRTHDAY, birthday);
        values.put(DBOpenHelper.COL_BIRTHDAY_TEXT, birthdayText);
        values.put(DBOpenHelper.COL_IMAGE_FILE_URI, fileName);
        long id = db.insert(DBOpenHelper.TABLE_NAME, null, values);
        db.close();
        return id;
    }


    // -- delete contact by id -- //
    public void delete(String id) {

        db.delete(
                DBOpenHelper.TABLE_NAME,
                DBOpenHelper.COL_ID + "=?",
                new String[]{id}
        );


    }

    // -- get all data from the SQlite table -- //
    public Cursor getData() {
        return db.query(
                DBOpenHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null

        );
    }


    // -- update data -- //
    public int update(int id, String newFirstName, String newLastName, String newBirthday, String newBirthdayText, String newPhone, String newImage) {
        ContentValues values = new ContentValues();
        if (newFirstName != null) {
            values.put(DBOpenHelper.COL_FIRST_NAME, newFirstName);
        }
        if (newLastName != null) {
            values.put(DBOpenHelper.COL_LAST_NAME, newLastName);
        }
        if (newBirthday != null) {
            values.put(DBOpenHelper.COL_BIRTHDAY, newBirthday);
        }
        if (newBirthdayText != null) {
            values.put(DBOpenHelper.COL_BIRTHDAY_TEXT, newBirthdayText);
        }
        if (newPhone != null) {
            values.put(DBOpenHelper.COL_PHONE, newPhone);
        }
        if (newImage != null) {
            values.put(DBOpenHelper.COL_IMAGE_FILE_URI, newImage);
        }
        return update(id, values);
    }

    private int update(int id, ContentValues values) {

        int rowsAffected = db.update(
                DBOpenHelper.TABLE_NAME,
                values,
                DBOpenHelper.COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );
        return rowsAffected;
    }

    // -- do query to filtered the first name in the list view -- //
    public Cursor query(String filter) {

        if (filter.trim().isEmpty()) {
            return db.query(DBOpenHelper.TABLE_NAME,
                    null, null, null, null, null, null, null);


        }

        return db.query(DBOpenHelper.TABLE_NAME, null,
                DBOpenHelper.COL_FIRST_NAME + "=?", new String[]{filter}, null, null, null);


    }

}


