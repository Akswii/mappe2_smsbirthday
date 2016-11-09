package com.example.aksel.s232324_mappe2;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class Smservice extends Service {
    private DBAdapter db;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Pls", "onbind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = new DBAdapter(this);
        db.open();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH), month = cal.get(Calendar.MONTH);

        Cursor mldpers = db.finnBursdag(day, month);
        SmsManager smsManager = SmsManager.getDefault();
        if (mldpers.moveToFirst()) {
            do{
                Log.d("Er i ", "Smservice");
                String mld = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("edittext_preference", "Gratulerer!");
                smsManager.sendTextMessage(mldpers.getString(mldpers.getColumnIndex(db.TLFNR)), null, mld, null, null);
            }while (mldpers.moveToNext());
        }
        mldpers.close();
        return super.onStartCommand(intent, flags, startId);
    }
}
