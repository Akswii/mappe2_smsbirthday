package com.example.aksel.s232324_mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Aksel on 28/10/2016.
 */

public class Smsperiodisk extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar cal = Calendar.getInstance();

        //testy

        Intent i = new Intent(this, Smservice.class);
        PendingIntent pIntent = PendingIntent.getService(this,0,i,0);

        String tid = PreferenceManager.getDefaultSharedPreferences(this).getString("tidshjulpref", "12:00");
        int time = PreferenceTidhjul.getHour(tid);
        int minute = PreferenceTidhjul.getMinute(tid);

        cal.set(Calendar.HOUR_OF_DAY, time);
        cal.set(Calendar.MINUTE, minute);

        boolean b = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("checkbox_preference", true);

        Log.d("Er i ", "Smsperiodisk");
        if(b) {
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
        }
        else{
            this.stopService(i);
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
