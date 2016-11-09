package com.example.aksel.s232324_mappe2;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Aksel on 17/10/2016.
 */

public class Liste extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_layout);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setIcon(R.mipmap.bursdags_ikon);

        //this.startService(new Intent(this, Smsperiodisk.class));

        DBAdapter db = new DBAdapter(this);
        db.open();

        ListView l1 = (ListView) findViewById(R.id.testliste);

        ArrayList<String> list = new ArrayList<String>();
        final ArrayList<Integer> tallListe = new ArrayList<Integer>();

        Cursor cur = db.finnalle();
        if(cur.moveToFirst()){
            do {
                list.add(cur.getString(0) + " " + cur.getString(1));
                tallListe.add(cur.getInt(6));
            }while(cur.moveToNext());
        }
        cur.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        l1.setAdapter(adapter);

        final Context thisC = this;

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(thisC, Endreinformasjon.class);
                intent.putExtra("Posnr", tallListe.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hoved_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        final Context context = this;
        Intent intent;
        switch(item.getItemId()){
            case R.id.avslutt:
                avsluttApp();
                return true;
            case R.id.startliste:
                intent = new Intent(Liste.this, Databaseeditor.class);
                startActivity(intent);
                return true;
            case R.id.pref:
                intent=new Intent(Liste.this,Preferences.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void avsluttApp()
    {
        this.finishAffinity();
    }
}
