package de.nkp_media.vertretungsplanappandroid.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import de.nkp_media.vertretungsplanappandroid.Ausfall2;

/**
 * Created by paul on 29.04.15.
 */
public class FeedDataProvider {

    private final DBHelper dbHelper;

    public FeedDataProvider(Context context)
    {
        this.dbHelper = new DBHelper(context);
    }

    public void updateDatabase(ArrayList<Ausfall2> ausfaelle)
    {
        System.out.println("update Database");
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
            System.out.println("Insert");
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

    public boolean newData(ArrayList<Ausfall2> ausfaelle) {
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
            if(!gefundenInDatabase) {
                return true;
            }
        }
        return false;
    }
}
