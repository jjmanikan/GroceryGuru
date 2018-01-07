package com.example.justine.groceryguru;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by justine on 2018-01-06.
 */

public class NotificationBroadcast extends BroadcastReceiver {

    public  static String MY_NOTIFICATION = "my_notification";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notif = intent.getParcelableExtra(NOTIFICATION);
        int mynotif = intent.getIntExtra(MY_NOTIFICATION, 0);
        manager.notify(mynotif,notif);

    }
}
