package com.akshit.ontime;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

public class AlertReciever extends BroadcastReceiver {
    Context context;
    
    @Override
    public void onReceive(Context context, Intent intent) {


        String get=intent.getStringExtra("Name");
        int id=intent.getIntExtra("Id",0);
        String work=intent.getStringExtra("Work");
        createNotification(context, get, id,work);
    }


    private void createNotification(Context context, String alert ,int id ,String work) {

        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,new Intent(context,Todo.class),0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context).setContentTitle(work).setContentText(alert)
                .setTicker("Hi").setSmallIcon(R.drawable.signin_icon);
        builder.setContentIntent(pendingIntent).setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(id,builder.build());
        }
    }
}