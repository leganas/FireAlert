package by.legan.android.firealert.database.history;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AndreyLS on 21.02.2017.
 */

public class HistoryBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "historyBase.db";

    public HistoryBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + HistoryDbSchema.HistoryTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                HistoryDbSchema.HistoryTable.Cols._ID + ", " +
                HistoryDbSchema.HistoryTable.Cols.id_boiler + ", " +
                HistoryDbSchema.HistoryTable.Cols.data + ", " +
                HistoryDbSchema.HistoryTable.Cols.alert_name + ", " +
                HistoryDbSchema.HistoryTable.Cols.alert_msg +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
