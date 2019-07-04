package by.legan.android.firealert.database.history;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Элемент истории
 */

public class History_item {

    public static final String MY_HISTORY_ITEM = "com.leganas.firealert.mobile.History_item";

    UUID _id;
    UUID idBoiler;
    String date_alert;
    String alert_name;
    String alert_msg;

    public History_item(){
        this(UUID.randomUUID());
    }

    public History_item(UUID _id) {
        this._id = _id;
    }

    public static String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return dateFormat.format(new Date());
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public UUID getIdBoiler() {
        return idBoiler;
    }

    public void setIdBoiler(UUID idBoiler) {
        this.idBoiler = idBoiler;
    }

    public String getDate_alert() {
        return date_alert;
    }

    public void setDate_alert(String date_alert) {
        this.date_alert = date_alert;
    }

    public String getAlert_name() {
        return alert_name;
    }

    public void setAlert_name(String alert_name) {
        this.alert_name = alert_name;
    }

    public String getAlert_msg() {
        return alert_msg;
    }

    public void setAlert_msg(String alert_msg) {
        this.alert_msg = alert_msg;
    }
}
