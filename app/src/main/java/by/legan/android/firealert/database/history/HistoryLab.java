package by.legan.android.firealert.database.history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreyLS on 21.02.2017.
 */

public class HistoryLab {
    private static HistoryLab historyLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public HistoryLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new HistoryBaseHelper(mContext)
                .getWritableDatabase();
    }

    public static HistoryLab get(Context context){
        if (historyLab == null) {
            historyLab = new HistoryLab(context);
        }
        return historyLab;
    }

    public void addHistory_item(History_item item){
        ContentValues values = getContentValues(item);
        mDatabase.insert(HistoryDbSchema.HistoryTable.NAME, null, values);
    }

    public void updateHistory_item(History_item item) {
        String idString = item.get_id().toString();
        ContentValues values = getContentValues(item);
        mDatabase.update(HistoryDbSchema.HistoryTable.NAME, values,
                HistoryDbSchema.HistoryTable.Cols._ID + " = ?",
                new String[] { idString });
    }

    public void removeHistory_item(History_item item){
        String uuidString = item.get_id().toString();
        ContentValues values = getContentValues(item);
        mDatabase.delete(HistoryDbSchema.HistoryTable.NAME, HistoryDbSchema.HistoryTable.Cols._ID + " = ?",
                new String[] { uuidString });
    }


    private static ContentValues getContentValues(History_item item) {
        ContentValues values = new ContentValues();
        values.put(HistoryDbSchema.HistoryTable.Cols._ID, item.get_id().toString());
        values.put(HistoryDbSchema.HistoryTable.Cols.id_boiler, item.getIdBoiler().toString());
        values.put(HistoryDbSchema.HistoryTable.Cols.data, item.getDate_alert());
        values.put(HistoryDbSchema.HistoryTable.Cols.alert_name, item.getAlert_name());
        values.put(HistoryDbSchema.HistoryTable.Cols.alert_msg, item.getAlert_msg());
        return values;
    }


    public List<History_item> getHistory() {
        List<History_item> items = new ArrayList<>();
        HistoryCursorWrapper cursor = queryHistory_item(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getHistory_item());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    private HistoryCursorWrapper queryHistory_item(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                HistoryDbSchema.HistoryTable.NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new HistoryCursorWrapper(cursor);
    }


}
