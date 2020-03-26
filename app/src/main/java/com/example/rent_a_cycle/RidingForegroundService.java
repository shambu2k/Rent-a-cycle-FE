package com.example.rent_a_cycle;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.rent_a_cycle.RidingActivity.CHANNEL_Timer;

public class RidingForegroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationPressed = new Intent(this, RidingActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationPressed, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_Timer)
                .setContentTitle("Ride On")
                .setContentText("00:00:00")
                .setSmallIcon(R.drawable.bicycle)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(2, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
