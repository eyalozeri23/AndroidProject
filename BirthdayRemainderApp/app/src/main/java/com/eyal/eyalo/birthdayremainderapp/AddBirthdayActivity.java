package com.eyal.eyalo.birthdayremainderapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.eyal.eyalo.birthdayremainderapp.infrastracture.DataSource;
import com.eyal.eyalo.birthdayremainderapp.infrastracture.Validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddBirthdayActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private static final int CAMERA_APP_REQUEST_CODE = 1;
    private static final int MAIN_ACTIVITY_REQUEST_CODE = 2;

    private EditText firstNameET, lastNameET, phoneET, dateOfBirthET, birthdayTextET;
    private ImageView imageView;
    private File imageFile;
    private AlarmManager alarmManager;
    private Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);


        firstNameET = (EditText) findViewById(R.id.add_birthday_activity_first_name);
        lastNameET = (EditText) findViewById(R.id.add_birthday_activity_last_name);
        phoneET = (EditText) findViewById(R.id.add_birthday_activity_phone);
        dateOfBirthET = (EditText) findViewById(R.id.add_birthday_activity_date_of_birth);
        birthdayTextET = (EditText) findViewById(R.id.add_birthday_activity_birthday_text);
        imageView = (ImageView) findViewById(R.id.add_birthday_activity_image_view);


        dateOfBirthET.setOnClickListener(this);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // create file in external storage
                imageFile = new File(Environment.getExternalStorageDirectory(), "img_" + System.currentTimeMillis() + ".jpg");

                // open the camera app
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                startActivityForResult(intent, CAMERA_APP_REQUEST_CODE);

            }
        });


        // -- display the back button on action bar -- //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    // -- go back when click the back button on action bar -- //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_APP_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                try {
                    // parse the imageFile to bitmap
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(imageFile));
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else if (requestCode == MAIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                saveToDataBase();

            }

        }


    }

    // -- save to the SQlite -- //
    private void saveToDataBase() {
        String firstName = firstNameET.getText().toString().trim();
        String lastName = lastNameET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        String dateOfBirth = dateOfBirthET.getText().toString().trim();
        String birthdayText = birthdayTextET.getText().toString().trim();
        String imageAddress = Uri.fromFile(imageFile).toString();
        DataSource dataSource = new DataSource(this);

        // save the selected data to the SQlite
        dataSource.insert(firstName, lastName, phone, dateOfBirth, birthdayText, imageAddress);
        setResult(RESULT_OK);
        finish();


    }


    // -- save button -- //
    public void save(View view) {

        // if the bitmap is null create new file
        if (bitmap == null) {

            imageFile = new File(Environment.getExternalStorageDirectory(), "img_" + System.currentTimeMillis() + ".jpg");
            if (!imageFile.isDirectory()) {
                imageFile.mkdir();

                // convert the drawable image to uri
                Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.user);
                try {
                    // convert from uri to bitmap
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imageView.setImageBitmap(bitmap);
            }


        }

          // check if one of the Edit Text stay empty
        if (Validation.validateLogin(firstNameET, lastNameET, phoneET, dateOfBirthET, birthdayTextET)) {

            saveToDataBase();
            String getPhone = phoneET.getText().toString().trim();
            String getBirthdayText = birthdayTextET.getText().toString().trim();

            // create alarm manager
            alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, SmsReceiver.class);
            intent.putExtra("phoneNumber", getPhone);
            intent.putExtra("birthdayMessage", getBirthdayText);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            try {
                // parse from string date to long
                String getBirthday = dateOfBirthET.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat();
                Date date = sdf.parse(getBirthday);
                long startDate = date.getTime();

                // set the alarm manager by the date of birth
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startDate, AlarmManager.INTERVAL_DAY, pendingIntent);
                finish();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

    // -- dateOfBirthET on click -- //
    @Override
    public void onClick(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerFragment fragment = new DatePickerFragment(this, this, now.get(now.YEAR), now.get(now.MONTH), now.get(now.DAY_OF_MONTH));
        fragment.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        dateOfBirthET.setText(dayOfMonth + "/" + (monthOfYear + 1));


    }
}



