package com.example.aksel.s232324_mappe2;

import android.content.ContentValues;
import android.content.Intent;
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
 * Created by Aksel on 05/10/2016.
 */

public class Databaseeditor extends AppCompatActivity {

    private Button settinn;
    private EditText fornavn, etternavn, telefonnummer;
    private DatePicker dato;
    private int day, month, year;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.databaseeditor);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        db = new DBAdapter(this);
        db.open();

        dato = (DatePicker) findViewById(R.id.bursdagsdato);
        fornavn = (EditText) findViewById(R.id.fornavn);
        etternavn = (EditText) findViewById(R.id.etternavn);
        telefonnummer = (EditText) findViewById(R.id.telefonnummer);
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
        inflater.inflate(R.menu.liste_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.avslutt:
                avsluttApp();
                return true;
            case R.id.lagre:
                String fnavn = fornavn.getText().toString();
                String enavn = etternavn.getText().toString();
                String tlf = "";

                try {
                    tlf = telefonnummer.getText().toString();
                    if(tlf.length() < 8 || tlf.length() > 8){throw new NumberFormatException("");}
                }
                catch(NumberFormatException nfe){
                    Toast.makeText(this, "Må ha et norskt nummer (8 siffer)", Toast.LENGTH_LONG).show();
                    telefonnummer.setText("");
                    return false;
                }

                day = dato.getDayOfMonth();
                month = dato.getMonth();
                year = dato.getYear();

                ContentValues cv = new ContentValues();
                cv.put(db.FORNAVN,fnavn);
                cv.put(db.ETTERNAVN,enavn);
                cv.put(db.TLFNR,tlf);
                cv.put(db.FDAG,day);
                cv.put(db.FMAANED,month);
                cv.put(db.FAAR,year);
                db.insert(cv);
                Intent intent = new Intent(this, Liste.class);
                startActivity(intent);
                Toast.makeText(this, fnavn + " lagt til", Toast.LENGTH_SHORT).show();
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
