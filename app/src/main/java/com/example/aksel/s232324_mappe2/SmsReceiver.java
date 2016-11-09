package com.example.aksel.s232324_mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Aksel on 28/10/2016.
 */

public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Er i", "SmsReceiver");
        Intent i = new Intent(context, Smsperiodisk.class);
        context.startService(i);
    }
}
