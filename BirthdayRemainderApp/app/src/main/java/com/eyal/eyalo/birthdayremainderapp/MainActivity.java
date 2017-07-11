package com.eyal.eyalo.birthdayremainderapp;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.eyal.eyalo.birthdayremainderapp.infrastracture.DBOpenHelper;
import com.eyal.eyalo.birthdayremainderapp.infrastracture.DataSource;

public class MainActivity extends AppCompatActivity {

    private static final int GO_TO_USER_DETAILS_ACTIVITY = 1;
    private static final int GO_TO_ADD_BIRTHDAY_ACTIVITY = 2;

    private BirthdayCursorAdapter adapter;
    private ListView listView;
    private EditText search;
    private DataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.main_activity_list_view);
        search = (EditText) findViewById(R.id.main_activity_edit_text_search);

        dataSource = new DataSource(this);
        adapter = new BirthdayCursorAdapter(this, dataSource.getData());
        listView.setAdapter(adapter);


        // -- delete user by on long item click in list view -- //
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                String myColumnValue = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COL_ID));
                showAlert(myColumnValue);

                return true;
            }
        });

        // -- go to UserDetailsActivity and export the data -- //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
                Cursor c = (Cursor) adapterView.getItemAtPosition(position);
                String firstNameValue = c.getString(c.getColumnIndex(DBOpenHelper.COL_FIRST_NAME));
                String LastNameValue = c.getString(c.getColumnIndex(DBOpenHelper.COL_LAST_NAME));
                String PhoneValue = c.getString(c.getColumnIndex(DBOpenHelper.COL_PHONE));
                String PhotoValue = c.getString(c.getColumnIndex(DBOpenHelper.COL_IMAGE_FILE_URI));
                String BirthdayValue = c.getString(c.getColumnIndex(DBOpenHelper.COL_BIRTHDAY));
                String BirthdayTextValue = c.getString(c.getColumnIndex(DBOpenHelper.COL_BIRTHDAY_TEXT));


                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("name", firstNameValue);
                intent.putExtra("lastName", LastNameValue);
                intent.putExtra("phone", PhoneValue);
                intent.putExtra("photo", PhotoValue);
                intent.putExtra("birthday", BirthdayValue);
                intent.putExtra("birthdayText", BirthdayTextValue);

                startActivityForResult(intent, GO_TO_USER_DETAILS_ACTIVITY);
            }
        });

        // -- TextWatcher to search Edit Text -- //
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // change the cursor by the filter and update the list view
                Cursor cursor = dataSource.query(search.getText().toString());

                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();
                BirthdayCursorAdapter filterAdapter = (BirthdayCursorAdapter) listView.getAdapter();
                filterAdapter.getFilter().filter(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });


    }


    // -- inflate the add button on the action bar  -- //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, AddBirthdayActivity.class);
                startActivityForResult(intent, GO_TO_ADD_BIRTHDAY_ACTIVITY);
                return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GO_TO_ADD_BIRTHDAY_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                adapter.changeCursor(dataSource.getData());
            }
        }

        adapter.changeCursor(dataSource.getData());

        if (requestCode == GO_TO_USER_DETAILS_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                adapter.changeCursor(dataSource.getData());
            }
        }
    }

    // -- create alert dialog -- //
    private void showAlert(final String userID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete User")
                .setMessage("Are You Sure ?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataSource.delete(userID);
                        adapter.changeCursor(dataSource.getData());
                    }
                });


        builder.setNegativeButton("No", null);
        builder.create().show();
    }


}






