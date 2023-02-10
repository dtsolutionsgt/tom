package com.dts.servicios;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.widget.RemoteViews;


import androidx.core.app.NotificationCompat;

import com.dts.tom.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class srvBaseJob extends JobService {

    public String URL = "", error = "";

    private NotificationManager notificationManager;

    private final boolean idle=false;

    private final String appname="TomWMS Nueva tarea";
    private final int iconresource=R.drawable.tom_wms;


    @Override
    public boolean onStartJob(JobParameters params) {
        try {
            String paramstr=params.getExtras().getString("params");
            if (initSession(paramstr)) execute();
        } catch (Exception e) {
            error=e.getMessage();
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    @Override
    public void onDestroy() {}

    //region Main

    public void execute() {}

    public boolean loadParams(String paramstr) {
        return true;
    }

    private boolean initSession(String paramstr) {
        try {
            notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            return loadParams(paramstr);
        } catch (Exception e) {
            return false;
        }
    }

    //endregion

    //region Notification

    public void notifynew(String ntext) {
        int notificationId = createID();
        String channelId = "channel-id",channelName = "Channel Name";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent fullScreenIntent = new Intent(this, srvBaseJob.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification);

        notificationLayout.setTextViewText(R.id.title, ntext);

        Notification notification = new Notification();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(iconresource)
                .setContentTitle(appname)
                .setContentText(ntext)
                .setLights(Color.YELLOW, 500, 5000)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContent(notificationLayout)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setColor(Color.parseColor("#6200EE"));

        mBuilder.setDefaults(notification.defaults);

        notificationManager.notify(notificationId, mBuilder.build());

    }

    public void notification(String message) {

        int notificationId = createID();
        String channelId = "channel-id";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(iconresource)
                .setContentTitle(appname)
                .setContentText(message)
                .setVibrate(new long[]{100, 250})
                .setLights(Color.YELLOW, 500, 5000)
                .setTimeoutAfter(30000)
                .setAutoCancel(true)
                .setColor(Color.parseColor("#6200EE"));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, srvBaseJob.class));
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());

        removeNotification(notificationId);
    }

    private void removeNotification(int id) {
        final int notifid=id;

        Handler handler = new Handler();
        long delayInMilliseconds = 30000;
        handler.postDelayed(new Runnable() {
            public void run() {
                notificationManager.cancel(notifid);
            }
        }, delayInMilliseconds);
    }

    private int createID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.ENGLISH).format(now));
        return id;
    }

    //endregion

}
