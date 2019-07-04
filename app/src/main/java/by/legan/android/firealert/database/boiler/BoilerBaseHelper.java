package by.legan.android.firealert.database.boiler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AndreyLS on 19.02.2017.
 */

public class BoilerBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "boilerBase.db";

    public BoilerBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + BoilerDbSchema.BoilerTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BoilerDbSchema.BoilerTable.Cols._ID + ", " +
                BoilerDbSchema.BoilerTable.Cols.NAME + ", " +
                BoilerDbSchema.BoilerTable.Cols.PHONE_NUMBER_STATIONARY + ", " +
                BoilerDbSchema.BoilerTable.Cols.PHONE_NUMBER_MOBILE + ", " +
                BoilerDbSchema.BoilerTable.Cols.ALERT_NUMBER + ", " +
                BoilerDbSchema.BoilerTable.Cols.ADDRESS + ", " +
                BoilerDbSchema.BoilerTable.Cols.JSON_ITEM +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
