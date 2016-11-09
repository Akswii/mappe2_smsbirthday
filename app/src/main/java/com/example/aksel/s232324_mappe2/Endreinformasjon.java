package com.example.aksel.s232324_mappe2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Aksel on 25/10/2016.
 */

public class Endreinformasjon extends AppCompatActivity{
    private Button knpoppdater, knpslett;
    private EditText fornavn, etternavn, telefonnummer;
    private DatePicker dato;
    private int day, month, year, iden;
    private DBAdapter db;
    private Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endre_layout);
        getSupportActionBar().setTitle("");

        db = new DBAdapter(this);
        db.open();

        dato = (DatePicker) findViewById(R.id.bursdagsdato);
        fornavn = (EditText) findViewById(R.id.fornavn);
        etternavn = (EditText) findViewById(R.id.etternavn);
        telefonnummer = (EditText) findViewById(R.id.telefonnummer);

        Intent mIntent = getIntent();
        iden = mIntent.getIntExtra("Posnr", 0);

        cur = db.finnen(iden);
        if(cur.moveToFirst())
        {
            do{
                fornavn.setText(cur.getString(0));
                etternavn.setText(cur.getString(1));
                telefonnummer.setText(cur.getString(2));
                dato.updateDate(cur.getInt(5), cur.getInt(4), cur.getInt(3));
            }while(cur.moveToNext());
        }
        cur.close();
    }

    @Override
    public void onResume(){
        super.onResume();
        db.open();
    }

    @Override
    public void onPause(){
        super.onPause();
        db.close();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.endre_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        String fnavn = fornavn.getText().toString();
        String enavn = etternavn.getText().toString();
        String tlf = "";

        try {
            tlf = telefonnummer.getText().toString();
            if(tlf.length() < 8 || tlf.length() > 8){throw new NumberFormatException("");}
        }
        catch(NumberFormatException nfe){
            Toast.makeText(this, "Må ha et norskt telefonnummer (8 siffer)", Toast.LENGTH_LONG).show();
            telefonnummer.setText("");
            return false;
        }
        day = dato.getDayOfMonth();
        month = dato.getMonth();
        year = dato.getYear();
        Intent intent = new Intent(this, Liste.class);
        switch(item.getItemId()){
            case R.id.lagre1:
                db.oppdater(fnavn, enavn, tlf, day, month, year, iden);
                Toast.makeText(this, fnavn + " oppdatert!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;
            case R.id.slett:
                db.slett(iden);
                Toast.makeText(this, fnavn + " slettet", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
