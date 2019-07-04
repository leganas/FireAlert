package by.legan.android.firealert.database.boiler;

import android.database.Cursor;
import android.database.CursorWrapper;
import by.legan.android.firealert.Boiler;


import java.util.UUID;

/**
 * Created by AndreyLS on 19.02.2017.
 */

public class BoilerCursorWrapper extends CursorWrapper {
    public BoilerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Boiler getCrime() {
        String _id = getString(getColumnIndex(BoilerDbSchema.BoilerTable.Cols._ID));
        String name = getString(getColumnIndex(BoilerDbSchema.BoilerTable.Cols.NAME));
        String phone_number_stationary = getString(getColumnIndex(BoilerDbSchema.BoilerTable.Cols.PHONE_NUMBER_STATIONARY));
        String phone_number_mobile = getString(getColumnIndex(BoilerDbSchema.BoilerTable.Cols.PHONE_NUMBER_MOBILE));
        String alert_number = getString(getColumnIndex(BoilerDbSchema.BoilerTable.Cols.ALERT_NUMBER));
        String address = getString(getColumnIndex(BoilerDbSchema.BoilerTable.Cols.ADDRESS));
        String json_item = getString(getColumnIndex(BoilerDbSchema.BoilerTable.Cols.JSON_ITEM));

        Boiler boiler = new Boiler(UUID.fromString(_id));
        boiler.setName(name);
        boiler.setPhone_number_stationary(phone_number_stationary);
        boiler.setPhone_number_mobile(phone_number_mobile);
        boiler.setAlert_number(alert_number);
        boiler.setAddress(address);
        boiler.setJson_item(json_item);

        return boiler;
    }
}