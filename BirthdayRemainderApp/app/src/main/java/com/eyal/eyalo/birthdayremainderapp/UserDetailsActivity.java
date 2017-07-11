package com.eyal.eyalo.birthdayremainderapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class UserDetailsActivity extends AppCompatActivity {


    private EditText firstName, lastName, phone, dateOfBirth, birthdayText;
    private ImageView photo;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        firstName = (EditText) findViewById(R.id.on_click_adapter_activity_first_name);
        lastName = (EditText) findViewById(R.id.on_click_adapter_activity_last_name);
        phone = (EditText) findViewById(R.id.on_click_adapter_activity_phone);
        dateOfBirth = (EditText) findViewById(R.id.on_click_adapter_activity_date_of_birth);
        photo = (ImageView) findViewById(R.id.on_click_adapter_activity_image_view);
        birthdayText = (EditText) findViewById(R.id.on_click_adapter_activity_edit_birthday_text);



        // -- display the back button on action bar -- //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String getName = getIntent().getStringExtra("name");
        String getLastName = getIntent().getStringExtra("lastName");
        String getPhone = getIntent().getStringExtra("phone");
        String getBirthday = getIntent().getStringExtra("birthday");
        String getBirthdayText = getIntent().getStringExtra("birthdayText");

        String getPhoto = getIntent().getStringExtra("photo");
        Uri imageUri = Uri.parse(getPhoto);

        try {
            // parse from uri to bitmap
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            photo.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if bitmap is null set the default image
        if (bitmap == null){
            photo.setImageResource(R.drawable.user);
        }


        firstName.setText(getName);
        lastName.setText(getLastName);
        phone.setText(getPhone);
        dateOfBirth.setText(getBirthday);
        birthdayText.setText(getBirthdayText);


    }


    // -- go back when click the back button -- //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }


}
