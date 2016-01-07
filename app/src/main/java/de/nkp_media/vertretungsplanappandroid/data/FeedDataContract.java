package de.nkp_media.vertretungsplanappandroid.data;

import android.provider.BaseColumns;

/**
 * Created by paul on 29.04.15.
 */
public class FeedDataContract {

    public FeedDataContract() {}

    /* Inner class that defines the table contents */
    public static abstract class VertretungsEntry implements BaseColumns {
        public static final String TABLE_NAME = "vertretung";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_AUSFALL = "faelltAus";
        public static final String COLUMN_NAME_LEHRER = "lehrer";
        public static final String COLUMN_NAME_DATUM = "datum";
        public static final String COLUMN_NAME_FACH = "fach";
        public static final String COLUMN_NAME_STUNDE = "stunde";
        public static final String COLUMN_NAME_VERTRETUNG= "vertretung";
        public static final String COLUMN_NAME_ZIELFACH = "zielfach";
        public static final String COLUMN_NAME_RAUM = "raum";


        static final String TEXT_TYPE = " TEXT";
        static final String COMMA_SEP = ",";
        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + VertretungsEntry.TABLE_NAME + " (" +
                        VertretungsEntry._ID + " INTEGER PRIMARY KEY," +
                        VertretungsEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                        VertretungsEntry.COLUMN_NAME_AUSFALL + TEXT_TYPE + COMMA_SEP +
                        VertretungsEntry.COLUMN_NAME_LEHRER + TEXT_TYPE + COMMA_SEP +
                        VertretungsEntry.COLUMN_NAME_DATUM + TEXT_TYPE + COMMA_SEP +
                        VertretungsEntry.COLUMN_NAME_FACH + TEXT_TYPE + COMMA_SEP +
                        VertretungsEntry.COLUMN_NAME_STUNDE + TEXT_TYPE + COMMA_SEP +
                        VertretungsEntry.COLUMN_NAME_VERTRETUNG + TEXT_TYPE + COMMA_SEP +
                        VertretungsEntry.COLUMN_NAME_ZIELFACH + TEXT_TYPE + COMMA_SEP +
                        VertretungsEntry.COLUMN_NAME_RAUM + TEXT_TYPE +
                " )";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + VertretungsEntry.TABLE_NAME;
    }

    public static abstract class NewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATUM = "datum";


        static final String TEXT_TYPE = " TEXT";
        static final String COMMA_SEP = ",";
        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + NewsEntry.TABLE_NAME + " (" +
                        NewsEntry._ID + " INTEGER PRIMARY KEY," +
                        NewsEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                        NewsEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        NewsEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                        NewsEntry.COLUMN_NAME_DATUM + TEXT_TYPE +
                        " )";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + NewsEntry.TABLE_NAME;
    }
}
