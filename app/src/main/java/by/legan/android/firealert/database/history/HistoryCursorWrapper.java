package by.legan.android.firealert.database.history;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

/**
 * Created by AndreyLS on 21.02.2017.
 */

public class HistoryCursorWrapper extends CursorWrapper {

    public HistoryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public History_item getHistory_item() {

        String _ID = getString(getColumnIndex(HistoryDbSchema.HistoryTable.Cols._ID));
        String id_boiler = getString(getColumnIndex(HistoryDbSchema.HistoryTable.Cols.id_boiler));
        String data = getString(getColumnIndex(HistoryDbSchema.HistoryTable.Cols.data));
        String alert_name = getString(getColumnIndex(HistoryDbSchema.HistoryTable.Cols.alert_name));
        String alert_msg = getString(getColumnIndex(HistoryDbSchema.HistoryTable.Cols.alert_msg));

        History_item item = new History_item(UUID.fromString(_ID));
        item.setIdBoiler(UUID.fromString(id_boiler));
        item.setDate_alert(data);
        item.setAlert_name(alert_name);
        item.setAlert_msg(alert_msg);
        return item;
    }
}
