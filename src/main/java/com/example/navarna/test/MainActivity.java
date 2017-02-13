package com.example.navarna.test;

/*Ici class pricipal
* Dans le create il y a des example des fonctions dont tu as besoin , pour ajouter , supprimer et modifier des données , il y a également des commentaires
* tout les fonction que tu dois utiliser sont dans cette classe
* il y a le fonction insert pour inserer des données
* les fonction deteteID si tu veux suppimer une ligne en connaissant son id , ou deleteOther si tu souhaite utiliser d'autre paramettre de la base pour trouver les ligne a supprimer
 * les fonction modificationId si tu veux modifier une ligne en connaissant son id , et modificationOther si tu utilise d'autre parametre.
 *
 * Attention : tu n'as pas besoin de modifier les fonctions de BaseD.java , ni les element de Nomcolonne.java , de plus ici ne modifie AUCUNE fonction SAUF le Oncreate(...) (le main xd) et le displayContentprovider() si tu souhaite changer se qu'il affiche (pour t aider) , tu peux evidemment rajouter toutes les fonction que tu veux apres !
 * Deuxieme Attention : tant que tu ne supprime pas l'application de ton telephone , la base de données reste enregistrée. Donc si tu fais 1 fois tout tes insert tu n'est plus obliger de les faire apres mais si tu recompile ton application.
 * Il me reste encore a faire la recherche par critere je la ferai dans la fin de la semaine.
 * Bon travail a toi
 */
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
/*
        // exemple d 'insertion de donnée
        insertRecords("test1", "test sur l insertion", "example", "10 rue general leclerc", "0312457869", "http://test.com", 2, 15.20, 48.8534100, 2.3488000, "10h30-14h30", "8h30-17h30", "10h-14h30", "16h-19h30", "14h30-23h", "10h30-21h", "fermer");
        insertRecords("test2", "test sur la supression", "example2", "5 rue de la paix", "0312457412", "http://testons.com", 5, 9.20, 15.8364100, 5.4898000, "10h30-14h30", "8h30-17h30", "10h-14h30", "16h-19h30", "14h30-23h", "10h30-21h", "14h30-17h");

         // affiches certaine donnée dans un toast pour voir ce qu'il y a dans la base
        displayContentProvider();

        // efface une ligne en mettant en argument le numero de l ID
        deleteID(1);
        displayContentProvider();

        //efface une liste en mettant les champs dans nomColonne et les valeur dans valeur delete
        insertRecords("test3", "test sur la reinsertion", "example", "10 rue general leclerc", "0312457869", "http://test3.com", 2, 15.20, 48.8534100, 2.3488000, "10h30-14h30", "8h30-17h30", "10h-14h30", "16h-19h30", "14h30-23h", "10h30-21h", "fermer");
        displayContentProvider();
        String nomColonne = Nomcolonne.ListeColonne.restaurant_Nom +"=?"; // peut etre ecrit "Nom=?"
        String [] valeurDelete = {"test2"};
        deleteOther(nomColonne, valeurDelete);
        displayContentProvider();

        // modification d'une ligne en utilisant l ID
        // colonneAmodifier possede toujours 17 strings , soit le nom des champs a modifier soit "" pour dire que ces champs ne sont pas a modifier
        // ici on modifie le nom , la note , la coordonnee latitude , l horaire du mardi
        String [] colonneAmodifier  ={
                    Nomcolonne.ListeColonne.restaurant_Nom, "","","","","",
                    Nomcolonne.ListeColonne.restaurant_note,
                            "", Nomcolonne.ListeColonne.restaurant_CoordonneLat,"",
                            "",Nomcolonne.ListeEmploiDuTemps.Mardi_Horaire,"","","","",""};
        //valeurDesStrings possede toujours 13 champs : description en dessus,
        // ici tout les element etant de types String
        String [] valeurDesString  = {
                "test4","","","","","", // valeur de nom [0], description[1] , types[2] , adresse[3] , numero[4] , sites[5]
                "","00h00-12h","","","","",""// valeur des horaire de lundi [6], mardi , mercredi , jeudi , vendredi ,samedi , dimanche[12]
        };
        // valeur denote , permet de modifier la note, met a zero si tu veux pas modifier.
        int ValeurDenote = 1 ;
        // valeur de double permet de modifier dans l ordre : prix , coordonnee de lattittude , puis coordonnee de longitude
        double [] valeurDesDouble = {
                0.0, 6.1234567, 0.0,
        };
        //Ici id de la ligne a modifier
        int NumerodelaligneAmodifier = 3;
        modificationID(colonneAmodifier,valeurDesString,ValeurDenote,valeurDesDouble,NumerodelaligneAmodifier);
        displayContentProvider();

        // Modification d'une ligne en utilisant le nom et le prix pour reconnaitre la ligne
        // tout d'abord insertion d'une ligne ayant le meme nom.
        insertRecords("test4", "test sur la modification2", "example", "8 rue de paris", "0314895869", "http://test5.com", 2, 18.05, 38.8534100, 60.3488000, "10h30-14h30", "12h30-19h", "10h-14h30", "16h-19h30", "14h30-23h", "10h30-21h", "5h00-17h");
        displayContentProvider();
        // nouveau tableaudeString
        String [] valeurDesString2  = {
                "test6","","","","","",
                "","00h00-12h","","","","",""
        };
        //nouvelle note
        int ValeurDenote2 = 4 ;
        //nouveau tableau de doubles
        double [] valeurDesDouble2 = {
                0.0, 12.1234567, 0.0,
        };
        // nom des colonne pour choisir la ligne
        String nomColonne2 = Nomcolonne.ListeColonne.restaurant_Nom +"=?"+ "AND " + Nomcolonne.ListeColonne.restaurant_PrixMoy +"=?"; // peut etre ecrit "Nom=? AND PrixMoyen=?"
        // valeur des colonnes
        String [] valeurModification = {"test4",18.05+""};
        modificationOther(colonneAmodifier,valeurDesString2,ValeurDenote2,valeurDesDouble2,nomColonne2,valeurModification);
        displayContentProvider();

        //query , recherche des données
        // colonne qu'on souhaite recevoir dans le curseur (donc les informations qu'on a besoin
        String colonnevoulu[] = new String[]{Nomcolonne.ListeColonne.restaurant_ID, Nomcolonne.ListeColonne.restaurant_Nom, Nomcolonne.ListeColonne.restaurant_Description, Nomcolonne.ListeColonne.restaurant_note, Nomcolonne.ListeColonne.restaurant_CoordonneLat, Nomcolonne.ListeEmploiDuTemps.Mardi_Horaire};
        // colonne du where (nom des colonne des conditions )
        String  colonneDonnee = Nomcolonne.ListeColonne.restaurant_Nom + "=?" +" AND "+ Nomcolonne.ListeColonne.restaurant_note  + ">?";
        // colonne des egaliter  des condditions
        String [] egaliter = {"test4",1+""};
        // le dernier argument est si tu veux avoir un ordre dans ce que tu affiche
        recherche(colonnevoulu,colonneDonnee,egaliter,null);
        */
        //OuvrirPageWeb("http:mangahere.co");
        //localiser(48.892684,2.237703);
        //localiser("8 rue De Londres","Paris");
        //telephoner("0954169809");
        //sms("0781724664"," Chocolat A volonter","6 rue du rêve");
        //double[] coordonner = CoordoneesGPS("9 rue général Leclerc+Groslay");
        //if(coordonner != null)
        //localiser(coordonner[0],coordonner[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // affiche avec un toast certain element
    private void displayContentProvider() {
        // ce tableau indique les donnees qu'on souhaite que le curseur enregistre
        String columns[] = new String[]{Nomcolonne.ListeColonne.restaurant_ID, Nomcolonne.ListeColonne.restaurant_Nom, Nomcolonne.ListeColonne.restaurant_Description, Nomcolonne.ListeColonne.restaurant_note, Nomcolonne.ListeColonne.restaurant_CoordonneLat, Nomcolonne.ListeEmploiDuTemps.Mardi_Horaire};
        Uri mContacts = baseD.lienUri;
        Cursor cur = managedQuery(mContacts, columns, null, null, null);
        Toast.makeText(MainActivity.this,"nombre d'element: " + cur.getCount() ,
                Toast.LENGTH_LONG).show();

        if (cur.moveToFirst()) {
            String name = "";
            do {
                // ici on recupere les données
                name ="id: " + cur.getString(cur.getColumnIndex(Nomcolonne.ListeColonne.restaurant_ID)) + "\n" +
                        "nom: " +cur.getString(cur.getColumnIndex(Nomcolonne.ListeColonne.restaurant_Nom)) + "\n" +
                        "descritption: " +cur.getString(cur.getColumnIndex(Nomcolonne.ListeColonne.restaurant_Description)) + "\n" +
                        "note: " +cur.getString(cur.getColumnIndex(Nomcolonne.ListeColonne.restaurant_note)) + "\n" +
                        "horaire mardi: " + cur.getString(cur.getColumnIndex(Nomcolonne.ListeEmploiDuTemps.Mardi_Horaire)) + "\n" +
                        "coordonnee latitude: " +cur.getString(cur.getColumnIndex(Nomcolonne.ListeColonne.restaurant_CoordonneLat));
                Toast.makeText(this, name + " ", Toast.LENGTH_LONG).show();
            } while (cur.moveToNext());
        }

    }
    // insertion de données , tout les donnee doivent etre renseigner meme si elle sont de valeur null ;
    private void insertRecords(String Nom, String description, String types, String adresse, String numero, String site, int note, double prix, double lattitude, double longitude,
                               String Hlundi, String Hmardi, String Hmercredi, String Hjeudi, String Hvendredi, String Hsamedi, String Hdimache) {
        ContentValues contact = new ContentValues();
        contact.put(Nomcolonne.ListeColonne.restaurant_Nom, Nom);
        contact.put(Nomcolonne.ListeColonne.restaurant_Description, description);
        contact.put(Nomcolonne.ListeColonne.restaurant_types, types);
        contact.put(Nomcolonne.ListeColonne.restaurant_Adresse, adresse);
        contact.put(Nomcolonne.ListeColonne.restaurant_Telephone, numero);
        contact.put(Nomcolonne.ListeColonne.restaurant_Site, site);
        contact.put(Nomcolonne.ListeColonne.restaurant_note, note);
        contact.put(Nomcolonne.ListeColonne.restaurant_PrixMoy, prix);
        contact.put(Nomcolonne.ListeColonne.restaurant_CoordonneLat, lattitude);
        contact.put(Nomcolonne.ListeColonne.restaurant_CoordonneLong, longitude);
        contact.put(Nomcolonne.ListeEmploiDuTemps.Lundi_Horaire, Hlundi);
        contact.put(Nomcolonne.ListeEmploiDuTemps.Mardi_Horaire, Hmardi);
        contact.put(Nomcolonne.ListeEmploiDuTemps.Mercredi_Horaire, Hmercredi);
        contact.put(Nomcolonne.ListeEmploiDuTemps.Jeudi_Horaire, Hjeudi);
        contact.put(Nomcolonne.ListeEmploiDuTemps.Vendredi_Horaire, Hvendredi);
        contact.put(Nomcolonne.ListeEmploiDuTemps.Samedi_Horaire, Hsamedi);
        contact.put(Nomcolonne.ListeEmploiDuTemps.Dimanche_Horaire, Hdimache);
        getContentResolver().insert(baseD.lienUri, contact);

    }

    // supprimer une ligne grace a son id
    public void deleteID(int id) {
        String[] idString = {id + ""};
        getContentResolver().delete(baseD.lienUri, "ID=?", idString);
    }

    // suprimer une ligne grace au string colone
    public void deleteOther(String colonne, String[] egaliter) {
        getContentResolver().delete(baseD.lienUri, colonne, egaliter);
    }

    // cree un content values , tu n as pas besoin de l'utiliser normalement
    public ContentValues constructionContentValeurModification (String[] colonne, String[] valeurString, int valeurInt, double[] valeurDouble) {
        ContentValues val = new ContentValues();
        for (int i = 0; i < 17; i++) {
            if (colonne[i] != "") {
                if ((i < 6)) {
                    val.put(colonne[i], valeurString[i]);
                } else if (i < 7) {
                    val.put(colonne[i], valeurInt);
                } else if (i < 10) {
                    val.put(colonne[i], valeurDouble[i - 7]);
                } else {
                    val.put(colonne[i], valeurString[i - 4]);
                }
            }
        }
        return val ;
    }

    // ici modifier une ligne grace a son id
    public void modificationID(String[] colonne, String[] valeurString, int valeurInt, double[] valeurDouble, int id){
        ContentValues val = constructionContentValeurModification(colonne,valeurString,valeurInt,valeurDouble);
        String [] idString = {id+""};
        getContentResolver().update(baseD.lienUri, val, "ID=?",idString );
    }

    // modifier une ligne grace au string where
    public void modificationOther(String[] colonne, String[] valeurString, int valeurInt, double[] valeurDouble, String where, String[] valeurWhere) {
        ContentValues val = constructionContentValeurModification(colonne,valeurString,valeurInt,valeurDouble);
        getContentResolver().update(baseD.lienUri, val, where, valeurWhere);
    }

    public Cursor recherche(String[] colonneVoulu ,String colonneDonnee,String [] egaliter ,String ordre){
        Cursor cur = getContentResolver().query(baseD.lienUri, colonneVoulu, colonneDonnee, egaliter, ordre);
        return cur;
    }

    public void OuvrirPageWeb (String page){
        Intent intent = new Intent(Intent.ACTION_VIEW ,Uri.parse(page));
        startActivity(intent);
    }

    public void localiser(double longitude , double latitude){
        String u = "geo:";
        u+=longitude +","+latitude ;
        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(u) );
        try {
            startActivity(intent);
        }catch( ActivityNotFoundException ex){
            Log.w("ltm","Vous n'avez pas GMap ou un système équivalent");
        }

    }
    public void localiser (String rueVille){
        String u = "geo:0,0?q=";
        u+= rueVille  ;
        Intent intent = new Intent( Intent.ACTION_NEW_OUTGOING_CALL, Uri.parse(u) );
        try {
            startActivity(intent);
        }catch( ActivityNotFoundException ex){
            Log.v("ltm", "Vous n'avez pas GMap ou système équivalent");
        }
    }

    public void telephoner (String numero){
        String call = "tel:";
        call+=numero ;
        Intent appel = new Intent(Intent.ACTION_CALL,Uri.parse(call));
        try {
            startActivity(appel);
        }
        catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Activiter pas trouver", Toast.LENGTH_SHORT).show();
    }
    }

    public void sms (String numero , String nomRestaurant ,String adresse ){
        String messageNum = "smsto:";
        messageNum += numero ;
        Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse(messageNum));
        String message = "Veux tu venir au restaurant " +nomRestaurant + " à l'adresse " + adresse + " à ...";
        sms.putExtra("sms_body", message);
        startActivity(sms);
    }

    public double[] CoordoneesGPS (String Adresse){
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        boolean existe = geocoder.isPresent();
        if(existe){
            List<Address> a ;
            try {
                a = geocoder.getFromLocationName(Adresse, 1);
                Address result = a.get(0);
                double [] resultat = new double[2];
                resultat[0] = result.getLatitude();
                resultat[1] = result.getLongitude();
                return resultat ;
            }
            catch (IOException e){
                Toast.makeText(getApplicationContext(), "Adresse probleme", Toast.LENGTH_SHORT).show();
            }
            return null ;
        }
        return null ;
    }
}



