package com.example.navarna.test;

/**
 * Created by Navarna on 09/12/2015.
 * Dans ce fichier se trouve tout les noms des champs de la base de données
 */

import android.provider.BaseColumns;

public class Nomcolonne {
    public Nomcolonne() {}

    public static final class ListeColonne implements BaseColumns {
        private ListeColonne() {}

        public static final String restaurant_ID  = "ID";
        public static final String restaurant_Nom = "Nom";
        public static final String restaurant_Description = "Description";
        public static final String restaurant_Adresse = "Adresse";
        public static final String restaurant_Telephone ="Telephone";
        public static final String restaurant_Site = "Sites" ;
        public static final String restaurant_note = "Note";
        public static final String restaurant_CoordonneLat ="Latitude";
        public static final String restaurant_CoordonneLong = "Longitude";
        public static final String restaurant_PrixMoy = "PrixMoyen";
        public static final String restaurant_types = "Types_de_cuisine";
    }

    public static final class ListeEmploiDuTemps implements BaseColumns {
        private ListeEmploiDuTemps () {}

        public static final String Lundi_Horaire ="Lundi";
        public static final String Mardi_Horaire ="Mardi";
        public static final String Mercredi_Horaire ="Mercredi";
        public static final String Jeudi_Horaire ="Jeudi";
        public static final String Vendredi_Horaire ="Vendredi";
        public static final String Samedi_Horaire ="Samedi";
        public static final String Dimanche_Horaire ="Dimanche";
    }
}
