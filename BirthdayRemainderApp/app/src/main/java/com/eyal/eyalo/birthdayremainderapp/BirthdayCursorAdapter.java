package com.eyal.eyalo.birthdayremainderapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eyal.eyalo.birthdayremainderapp.infrastracture.DBOpenHelper;

import java.io.IOException;

/**
 * Created by eyalo on 2/6/2017.
 */

public class BirthdayCursorAdapter extends CursorAdapter {


    public BirthdayCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, false);


    }

    private static class ViewHolder {
        ImageView photo;
        TextView firstName, lastName, birthday;

    }


    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.photo = (ImageView) view.findViewById(R.id.list_item_image_view);
        holder.firstName = (TextView) view.findViewById(R.id.list_item_first_name);
        holder.lastName = (TextView) view.findViewById(R.id.list_item_last_name);
        holder.birthday = (TextView) view.findViewById(R.id.list_item_date);


        view.setTag(holder);
        return view;
    }


    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        final String imageName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COL_IMAGE_FILE_URI));
        Bitmap bitmap = null;
        try {
            // convert the uri to bitmap
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.photo.setImageBitmap(bitmap);

        if (bitmap == null){

            // parse thr drawable image to uri
            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.user);
            try {
                // convert the uri to bitmap
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            holder.photo.setImageBitmap(bitmap);


        }

        // when clicking the photo in the list view;
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayPhotoActivity.class);
                intent.putExtra("image", imageName);
                context.startActivity(intent);
            }
        });
        String firstName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COL_FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COL_LAST_NAME));
        String birthday = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COL_BIRTHDAY));
        holder.firstName.setText(firstName);
        holder.lastName.setText(lastName);
        holder.birthday.setText(birthday);


    }


}
