package com.example.rent_a_cycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class RidingActivity extends AppCompatActivity {

    @BindView(R.id.hours)
    TextView ridingtime_hours;
    @BindView(R.id.mins)
    TextView ridingtime_mins;
    @BindView(R.id.secs)
    TextView ridingtime_secs;
    @BindView(R.id.cycle_status)
    TextView cycleStatus;

    @BindView(R.id.riding_goback)
    ImageView gobackImage;

    @BindView(R.id.stop_button)
    Button stop;

    public static final String CHANNEL_Timer = "Timer Foreground";
    public static final String CHANNEL_RideEnd = "Ride details";
    NotificationManager manager;
    int Seconds, Minutes, Hours;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler mHandler;
    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            Hours = Minutes / 60;

            String timeS;

            if (Seconds / 10 == 0) {
                ridingtime_secs.setText(":0" + Seconds);
            } else {
                ridingtime_secs.setText(":" + Seconds);
            }

            if (Minutes / 10 == 0) {
                ridingtime_mins.setText(":0" + Minutes);
            } else {
                ridingtime_mins.setText(":" + Minutes);
            }

            if (Hours / 10 == 0) {
                ridingtime_hours.setText("0" + Hours);
            } else {
                ridingtime_hours.setText(Hours);
            }

            timeS = ridingtime_hours.getText().toString()+ridingtime_mins.getText().toString()
                    +ridingtime_secs.getText().toString();

            manager.notify(2, getNotification(timeS));
            mHandler.postDelayed(this, 0);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riding);
        ButterKnife.bind(this);

        mHandler = new Handler();

        StartTime = SystemClock.uptimeMillis();
        mHandler.postDelayed(runnable, 0);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel timerChannel = new NotificationChannel(
                    CHANNEL_Timer,
                    "Timer Foreground",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            timerChannel.setDescription("This is the notification which shows when you are currently riding.");

            NotificationChannel rideEndChannel = new NotificationChannel(
                    CHANNEL_RideEnd,
                    "Ride Details",
                    NotificationManager.IMPORTANCE_HIGH
            );
            rideEndChannel.setDescription("This is the notification which shows when you finish a ride.");

            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(timerChannel);
            manager.createNotificationChannel(rideEndChannel);
        }
        startTimerService();
    }

    @OnTouch(R.id.riding_goback)
    void gobacktoFrag() {
        finish();
    }

    @OnClick(R.id.stop_button)
    void stopButton() {
        if (stop.getText().toString().equals("Lock")) {
            TimeBuff += MillisecondTime;
            mHandler.removeCallbacks(runnable);
            cycleStatus.setText("Current Cycle Status: Locked");
            stop.setText("Unlock");
            stopTimerService();
        } else {
            MillisecondTime = 0L;
            StartTime = 0L;
            TimeBuff = 0L;
            UpdateTime = 0L;
            Seconds = 0;
            Minutes = 0;
            Hours = 0;

            mHandler = new Handler();
            StartTime = SystemClock.uptimeMillis();
            mHandler.postDelayed(runnable, 0);
            cycleStatus.setText("Current Cycle Status: Unlocked");
            stop.setText("Lock");
            startTimerService();
        }
    }

    public void startTimerService() {

        Intent timerServiceIntent = new Intent(this, RidingForegroundService.class);
        startService(timerServiceIntent);

        ContextCompat.startForegroundService(this, timerServiceIntent);

    }

    public void stopTimerService() {
        Intent timerServiceIntent = new Intent(this, RidingForegroundService.class);
        stopService(timerServiceIntent);
    }

    private Notification getNotification(String text){
        Intent notificationPressed = new Intent(this, RidingActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationPressed, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_Timer)
                .setContentTitle("Ride On")
                .setContentText(text)
                .setOnlyAlertOnce(true)
                .setSmallIcon(R.drawable.bicycle)
                .setContentIntent(pendingIntent)
                .build();

        return notification;
    }

}
