package com.eyal.eyalo.birthdayremainderapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.eyal.eyalo.birthdayremainderapp.infrastracture.DBOpenHelper;
import com.eyal.eyalo.birthdayremainderapp.infrastracture.DataSource;

/**
 * Created by eyalo on 6/26/2017.
 */

public class SmsReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        String getPhone = intent.getStringExtra("phoneNumber");
        String getBirthdayText = intent.getStringExtra("birthdayMessage");

        // -- send the sms to the user -- //
        SmsManager manager = SmsManager.getDefault();

        // create pending intent to pop notification after the message sent
        PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(context, 1, new Intent(context,DeliveredReceiver.class), 0);
        manager.sendTextMessage(
                getPhone,
                null,
                getBirthdayText,
                null,
                deliveredPendingIntent
        );

    }

}
