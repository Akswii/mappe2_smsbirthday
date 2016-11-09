package com.example.aksel.s232324_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Aksel on 09/10/2016.
 */

public class DBAdapter  {
    Context context;
    static final String TAG="DbHelper";
    static final String DB_NAVN="Fodselsdag";
    static final String TABELL="personer";
    static final String FORNAVN="fornavn";
    static final String ETTERNAVN="etternavn";
    static final String TLFNR="telefonnummer";
    static final String FDAG="dag", FMAANED="fmaaned", FAAR="aar";
    static final String ID="id";
    static final int DB_VERSJON=5;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context,DB_NAVN,null,DB_VERSJON);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String sql="CREATE TABLE " + TABELL + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + FORNAVN + " TEXT, "
                    + ETTERNAVN + " TEXT, "
                    + TLFNR + " INTEGER, "
                    + FDAG + " TEXT, "
                    + FMAANED + " TEXT, "
                    + FAAR + " TEXT)";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("drop table if exists " + TABELL);
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
    }

    public void insert(ContentValues cv)
    {
        db.insert(TABELL,null,cv);
    }

    public boolean slett(int id)
    {
        return db.delete(TABELL, ID + "='" + id +"'", null) > 0;
    }
    public boolean oppdater(String fornavn, String etternavn, String telefonnummer, int dag, int mnd, int aar, int iden)
    {
        ContentValues cv = new ContentValues(1);
        cv.put(FORNAVN,fornavn);
        cv.put(ETTERNAVN, etternavn);
        cv.put(TLFNR, telefonnummer);
        cv.put(FDAG, dag);
        cv.put(FMAANED, mnd);
        cv.put(FAAR, aar);
        return db.update(TABELL,cv,ID + "=" + "'" + iden + "'", null) > 0;
    }
    public Cursor finnalle()
    {
        Cursor cur;
        String[] cols={FORNAVN,ETTERNAVN,TLFNR,FDAG,FMAANED,FAAR,ID};
        cur=db.query(TABELL, cols, null, null, null, null, null);
        return cur;
    }
    public Cursor finnen(int i)
    {
        Cursor cur;
        String[] cols = {FORNAVN,ETTERNAVN,TLFNR,FDAG,FMAANED,FAAR};
        cur = db.query(TABELL, cols, ID + "='" + i + "'", null, null, null, null);
        return cur;
    }

    public Cursor finnBursdag(int day, int month){
        String[] cols = {FDAG, FMAANED, TLFNR};
        return db.query(TABELL, cols, FDAG + " == '" + day + "' AND " + FMAANED + " == '" + month + "'", null, null, null, null);
    }

}
