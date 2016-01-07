package de.nkp_media.vertretungsplanappandroid.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;
import de.nkp_media.vertretungsplanappandroid.News;

/**
 * Created by paul on 29.04.15.
 */
public class FeedDataProvider {

    private final DBHelper dbHelper;
    private final Context context;

    public FeedDataProvider(Context context)
    {
        this.context = context;
        this.dbHelper = new DBHelper(this.context);
    }

    public void updateDatabase(ArrayList<Ausfall2> ausfaelle)
    {
        this.deleteAll();
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        for(Ausfall2 ausfall : ausfaelle)
        {
            ContentValues values = new ContentValues();
            if(ausfall.isEntfall()) {
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_AUSFALL, String.valueOf(ausfall.isEntfall()));
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_DATUM, ausfall.getZieldatumString());
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_FACH, ausfall.getFach());
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_STUNDE, ausfall.getStunde());
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_LEHRER, ausfall.getLehrer());
            }
            else
            {
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_AUSFALL, String.valueOf(ausfall.isEntfall()));
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_DATUM, ausfall.getZieldatumString());
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_FACH, ausfall.getFach());
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_STUNDE, ausfall.getStunde());
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_LEHRER, ausfall.getLehrer());
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_RAUM, ausfall.getRaum());
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_VERTRETUNG, ausfall.getVertretung());
                values.put(FeedDataContract.VertretungsEntry.COLUMN_NAME_ZIELFACH, ausfall.getZielfach());

            }
            long newRowId;
            newRowId = db.insert(
                    FeedDataContract.VertretungsEntry.TABLE_NAME,
                    null,
                    values);
        }
    }

    private void deleteAll() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String[] selectionArgs = {};
        db.delete(FeedDataContract.VertretungsEntry.TABLE_NAME, null, selectionArgs);
    }

    public ArrayList<Ausfall2> getPlanData()
    {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String[] projection = {
                FeedDataContract.VertretungsEntry.COLUMN_NAME_RAUM,
                FeedDataContract.VertretungsEntry.COLUMN_NAME_ZIELFACH,
                FeedDataContract.VertretungsEntry.COLUMN_NAME_VERTRETUNG,
                FeedDataContract.VertretungsEntry.COLUMN_NAME_STUNDE,
                FeedDataContract.VertretungsEntry.COLUMN_NAME_AUSFALL,
                FeedDataContract.VertretungsEntry.COLUMN_NAME_DATUM,
                FeedDataContract.VertretungsEntry.COLUMN_NAME_FACH,
                FeedDataContract.VertretungsEntry.COLUMN_NAME_LEHRER
        };

        String selection = " 0 = 0";

        Cursor c = db.query(
                FeedDataContract.VertretungsEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        ArrayList<Ausfall2> ausList = new ArrayList<Ausfall2>();
        while (c.moveToNext())
        {
            Ausfall2 aus = new Ausfall2();
            if(Boolean.getBoolean(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_AUSFALL))) == true)
            {
                aus.setEntfall(Boolean.parseBoolean(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_AUSFALL))));
                aus.setZieldatumString(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_DATUM)));
                aus.setFach(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_FACH)));
                aus.setLehrer(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_LEHRER)));
                aus.setRaum(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_RAUM)));
                aus.setStunde(Integer.parseInt(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_STUNDE))));
            }
            else
            {
                aus.setEntfall(Boolean.parseBoolean(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_AUSFALL))));
                aus.setZieldatumString(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_DATUM)));
                aus.setFach(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_FACH)));
                aus.setLehrer(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_LEHRER)));
                aus.setRaum(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_RAUM)));
                aus.setStunde(Integer.parseInt(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_STUNDE))));
                aus.setVertretung(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_VERTRETUNG)));
                aus.setZielfach(c.getString(c.getColumnIndex(FeedDataContract.VertretungsEntry.COLUMN_NAME_ZIELFACH)));
            }
            ausList.add(aus);
        }

        return ausList;
    }

    public boolean isNewData(ArrayList<Ausfall2> ausfaelle) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();



        for (Ausfall2 ausfall : ausfaelle) {
            String[] projection = {
                    FeedDataContract.VertretungsEntry.COLUMN_NAME_RAUM,
                    FeedDataContract.VertretungsEntry.COLUMN_NAME_ZIELFACH,
                    FeedDataContract.VertretungsEntry.COLUMN_NAME_VERTRETUNG,
                    FeedDataContract.VertretungsEntry.COLUMN_NAME_STUNDE,
                    FeedDataContract.VertretungsEntry.COLUMN_NAME_AUSFALL,
                    FeedDataContract.VertretungsEntry.COLUMN_NAME_DATUM,
                    FeedDataContract.VertretungsEntry.COLUMN_NAME_FACH,
                    FeedDataContract.VertretungsEntry.COLUMN_NAME_LEHRER
            };
            String selection = "";
            if(ausfall.isEntfall()) {
                selection = FeedDataContract.VertretungsEntry.COLUMN_NAME_AUSFALL + " = '" + String.valueOf(ausfall.isEntfall()) + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_DATUM + " = '" + ausfall.getZieldatumString() + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_FACH + " = '" + ausfall.getFach() + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_LEHRER + " = '" + ausfall.getLehrer() + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_STUNDE + " = '" + ausfall.getStunde() +
                        "'";
            }
            else
            {
                selection = FeedDataContract.VertretungsEntry.COLUMN_NAME_AUSFALL + " = '" + String.valueOf(ausfall.isEntfall()) + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_DATUM + " = '" + ausfall.getZieldatumString() + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_FACH + " = '" + ausfall.getFach() + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_LEHRER + " = '" + ausfall.getLehrer() + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_RAUM + " = '" + ausfall.getRaum() + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_VERTRETUNG + " = '" + ausfall.getVertretung() + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_ZIELFACH + " = '" + ausfall.getZielfach() + "' AND " +
                        FeedDataContract.VertretungsEntry.COLUMN_NAME_STUNDE + " = '" + ausfall.getStunde() +
                        "'";
            }
            Cursor c = db.query(
                    FeedDataContract.VertretungsEntry.TABLE_NAME,  // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );

            boolean gefundenInDatabase = false;
            while (c.moveToNext())
            {
                gefundenInDatabase = true;
            }
            if(!gefundenInDatabase)
            {
                return true;
            }
        }
        return false;
    }
















    public void updateNewsDatabase(ArrayList<News> newsList) {
        this.deleteAllNews();
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        for(News news : newsList)
        {
            ContentValues values = new ContentValues();
            values.put(FeedDataContract.NewsEntry.COLUMN_NAME_TITLE, String.valueOf(news.getTitle()));
            values.put(FeedDataContract.NewsEntry.COLUMN_NAME_CONTENT, news.getContent());
            values.put(FeedDataContract.NewsEntry.COLUMN_NAME_DATUM, news.getDate());

            long newRowId;
            newRowId = db.insert(
                    FeedDataContract.NewsEntry.TABLE_NAME,
                    null,
                    values);
        }
    }

    private void deleteAllNews() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String[] selectionArgs = {};
        db.delete(FeedDataContract.NewsEntry.TABLE_NAME, null, selectionArgs);
    }

    public ArrayList<News> getNewsData()
    {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String[] projection = {
                FeedDataContract.NewsEntry.COLUMN_NAME_TITLE,
                FeedDataContract.NewsEntry.COLUMN_NAME_CONTENT,
                FeedDataContract.NewsEntry.COLUMN_NAME_DATUM,
        };

        String selection = " 0 = 0";

        Cursor c = db.query(
                FeedDataContract.NewsEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        ArrayList<News> newsList = new ArrayList<News>();
        while (c.moveToNext())
        {
            News news = new News();
            news.setDate(c.getString(c.getColumnIndex(FeedDataContract.NewsEntry.COLUMN_NAME_DATUM)));
            news.setContent(c.getString(c.getColumnIndex(FeedDataContract.NewsEntry.COLUMN_NAME_CONTENT)));
            news.setTitle(c.getString(c.getColumnIndex(FeedDataContract.NewsEntry.COLUMN_NAME_TITLE)));
            newsList.add(news);
        }

        return newsList;
    }
}
