package com.example.navarna.test;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Navarna on 09/12/2015.
 * Ici c est le content provider . Il est fini et tu n'as tien a utiliser ici .
 */
public class baseD extends ContentProvider{
    public static final Uri lienUri = Uri.parse("content://com.example.navarna.test.baseD");
    public static final String nomContent = "ContentBase";
    public static final int versionContent = 1 ;
    public static final String Information = "Restaurant";
    public static final String  contentMime = "vnd.android.cursor.item/vnd.example.navarna.test.nomChamps";

    private static class Mabase extends SQLiteOpenHelper{
        public Mabase(Context context){
                super(context,baseD.nomContent,null,baseD.versionContent);
        }

        public void onCreate(SQLiteDatabase db){
                db.execSQL("CREATE TABLE "+baseD.Information + " ("
                        + Nomcolonne.ListeColonne.restaurant_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Nomcolonne.ListeColonne.restaurant_Nom + " VARCHAR (255)," + Nomcolonne.ListeColonne.restaurant_Description +" VARCHAR (255),"
                        + Nomcolonne.ListeColonne.restaurant_types +" VARCHAR (255),"
                        + Nomcolonne.ListeColonne.restaurant_Adresse + " VARCHAR (255),"
                        + Nomcolonne.ListeColonne.restaurant_Telephone + " VARCHAR (10)," + Nomcolonne.ListeColonne.restaurant_Site + " VARCHAR (255),"
                               + Nomcolonne.ListeColonne.restaurant_note + " INTEGER,"+ Nomcolonne.ListeColonne.restaurant_PrixMoy +" DECIMAL(5,2),"
                                + Nomcolonne.ListeColonne.restaurant_CoordonneLat + " DECIMAL(10,7)," + Nomcolonne.ListeColonne.restaurant_CoordonneLong +" DECIMAL(10,7),"
                        + Nomcolonne.ListeEmploiDuTemps.Lundi_Horaire +" VARCHAR (17),"
                                + Nomcolonne.ListeEmploiDuTemps.Mardi_Horaire +" VARCHAR (17)," + Nomcolonne.ListeEmploiDuTemps.Mercredi_Horaire+" VARCHAR (17),"
                        + Nomcolonne.ListeEmploiDuTemps.Jeudi_Horaire +" VARCHAR (17)," + Nomcolonne.ListeEmploiDuTemps.Vendredi_Horaire +" VARCHAR (17),"
                        + Nomcolonne.ListeEmploiDuTemps.Samedi_Horaire+" VARCHAR (17)," + Nomcolonne.ListeEmploiDuTemps.Dimanche_Horaire +" VARCHAR (17)"+");"

                );
        }

        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
                if(newVersion>oldVersion){
                    db.execSQL("drop table if exists " + Information);
                onCreate(db);
                }
        }
    }

    private Mabase dbase ;

    public boolean onCreate() {
        dbase = new Mabase(getContext());
        return true ;
    }

    public String getType (Uri u){
        return baseD.contentMime;
    }

    public long getId(Uri u){
        String lps = u.getLastPathSegment();
        if(lps != null){
            try {
                return Long.parseLong(lps);
            }
            catch (NumberFormatException e){
                Log.e("baseD","NumberFormatException"+e);
            }
        }
        return -1;
    }

    public Uri insert ( Uri u , ContentValues val){
        SQLiteDatabase db  = dbase.getWritableDatabase();
        try {
            long id =db.insertOrThrow(baseD.Information,null,val);
            if(id == -1){
                throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","baseD", val, u));
            }
            else {
                return ContentUris.withAppendedId(u,id);
            }

        }
        finally {
            db.close();
        }
    }
    public int update(Uri u, ContentValues val, String selection, String[] arguments) {
        long id = getId(u);
        SQLiteDatabase db = dbase.getWritableDatabase();

        try {
            if (id < 0)
                return db.update(
                        baseD.Information, val, selection, arguments);
            else
                return db.update(
                        baseD.Information, val, Nomcolonne.ListeColonne.restaurant_ID + "=" + id, null);
        }
        finally {
            db.close();
        }
    }

    public int delete(Uri u, String selection, String[] arguments) {
        long id = getId(u);
        SQLiteDatabase db = dbase.getWritableDatabase();
        try {
            if (id < 0)
                return db.delete(baseD.Information, selection, arguments);
            else
                return db.delete(baseD.Information, Nomcolonne.ListeColonne.restaurant_ID + "=" + id, arguments);
        }
        finally {
            db.close();
        }
    }
    public Cursor query(Uri u, String[] projection, String selection, String[] arguments, String ordre) {
        long id = getId(u);
        SQLiteDatabase db = dbase.getReadableDatabase();
        if (id < 0) {
            return db
                    .query(baseD.Information, projection, selection, arguments, null, null, ordre);
        } else {
            return db.query(baseD.Information, projection, Nomcolonne.ListeColonne.restaurant_ID + "=" + id, null, null, null, null);
        }
    }

}
