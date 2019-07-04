package by.legan.android.firealert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import by.legan.android.firealert.database.boiler.BoilerBaseHelper;
import by.legan.android.firealert.database.boiler.BoilerCursorWrapper;
import by.legan.android.firealert.database.boiler.BoilerDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс Лаборатория для работы с набором котельных
 */

public class BoilerLab {
    private static BoilerLab sBoilerLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private BoilerLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BoilerBaseHelper(mContext)
                .getWritableDatabase();
    }

    public static BoilerLab get(Context context){
        if (sBoilerLab == null) {
            sBoilerLab = new BoilerLab(context);
        }
        return sBoilerLab;
    }

    public List<Boiler> getBoilers() {
        List<Boiler> boilers = new ArrayList<>();
        BoilerCursorWrapper cursor = queryBoiler(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                boilers.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return boilers;
    }

    public Boiler getBoiler(UUID id){
        BoilerCursorWrapper cursor = queryBoiler(
                BoilerDbSchema.BoilerTable.Cols._ID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public void addBoiler(Boiler boiler){
        ContentValues values = getContentValues(boiler);
        mDatabase.insert(BoilerDbSchema.BoilerTable.NAME, null, values);
    }

    public void updateBoiler(Boiler boiler) {
        String idString = boiler.get_id().toString();
        ContentValues values = getContentValues(boiler);
        mDatabase.update(BoilerDbSchema.BoilerTable.NAME, values,
                BoilerDbSchema.BoilerTable.Cols._ID + " = ?",
                new String[] { idString });
    }

    public void removeBoiler(Boiler boiler){
        String uuidString = boiler.get_id().toString();
        ContentValues values = getContentValues(boiler);
        mDatabase.delete(BoilerDbSchema.BoilerTable.NAME, BoilerDbSchema.BoilerTable.Cols._ID + " = ?",
                new String[] { uuidString });
    }


    private static ContentValues getContentValues(Boiler boiler) {
        ContentValues values = new ContentValues();
        values.put(BoilerDbSchema.BoilerTable.Cols._ID, boiler.get_id().toString());
        values.put(BoilerDbSchema.BoilerTable.Cols.NAME, boiler.getName());
        values.put(BoilerDbSchema.BoilerTable.Cols.PHONE_NUMBER_STATIONARY, boiler.getPhone_number_stationary());
        values.put(BoilerDbSchema.BoilerTable.Cols.PHONE_NUMBER_MOBILE, boiler.getPhone_number_mobile());
        values.put(BoilerDbSchema.BoilerTable.Cols.ALERT_NUMBER, boiler.getAlert_number());
        values.put(BoilerDbSchema.BoilerTable.Cols.ADDRESS, boiler.getAddress());
        values.put(BoilerDbSchema.BoilerTable.Cols.JSON_ITEM, boiler.getJson_item());
        return values;
    }

    private BoilerCursorWrapper queryBoiler(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                BoilerDbSchema.BoilerTable.NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new BoilerCursorWrapper(cursor);
    }
}
