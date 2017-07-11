package com.eyal.eyalo.birthdayremainderapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.eyal.eyalo.birthdayremainderapp.infrastracture.DataSource;

/**
 * Created by eyalo on 7/5/2017.
 */

public class DeliveredReceiver extends BroadcastReceiver {

    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {


        int status = getResultCode();
        switch (status) {
            case Activity.RESULT_OK:
                // sms delivered successfully
                 showNotification();
                break;
            case Activity.RESULT_CANCELED:
                // problem during the delivered
                Toast.makeText(context,"problem during the delivered",Toast.LENGTH_LONG).show();
                break;
        }
    }

    // -- show the Notification -- //
    private void showNotification() {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_menu_send);
        builder.setContentTitle("Message sent successfully");
        builder.setContentText("");
        PendingIntent pi = PendingIntent.getBroadcast(context, 1, new Intent(context, SmsReceiver.class), PendingIntent.FLAG_ONE_SHOT);
        android.support.v4.app.NotificationCompat.Action showMessageAction = new android.support.v4.app.NotificationCompat.Action(R.drawable.message, "See Message", pi);
        builder.addAction(showMessageAction);
        manager.notify(2, builder.build());
    }
}
