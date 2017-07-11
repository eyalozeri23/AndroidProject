package com.eyal.eyalo.birthdayremainderapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;

public class DisplayPhotoActivity extends AppCompatActivity {

    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);

        ImageView imageView = (ImageView) findViewById(R.id.display_activity_image_view);

        Intent intent = getIntent();
        String imageAddress = intent.getStringExtra("image");

        Uri imageUri = Uri.parse(imageAddress);

        try {
            // parse from uri to bitmap
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if bitmap is null set the default image
        if (bitmap == null){
            imageView.setImageResource(R.drawable.user);
        }








    }
}
