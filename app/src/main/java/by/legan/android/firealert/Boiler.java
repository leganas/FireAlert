package by.legan.android.firealert;

import java.util.UUID;

/**
 * Класс описывающий котельную
 */

public class Boiler
{
    public static final String MY_BOILER = "com.leganas.firealert.mobile.boiler";
    UUID _id;
    String name;
    String phone_number_stationary;
    String phone_number_mobile;
    String alert_number;
    String address;
    String master_name;
    String json_item;

    public Boiler(){
        this(UUID.randomUUID());
    }

    public Boiler(UUID _id) {
        this._id = _id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getPhone_number_stationary() {
        return phone_number_stationary;
    }

    public void setPhone_number_stationary(String phone_number_stationary) {
        this.phone_number_stationary = phone_number_stationary;
    }

    public String getPhone_number_mobile() {
        return phone_number_mobile;
    }

    public void setPhone_number_mobile(String phone_number_mobile) {
        this.phone_number_mobile = phone_number_mobile;
    }

    public String getAlert_number() {
        return alert_number;
    }

    public void setAlert_number(String alert_number) {
        this.alert_number = alert_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJson_item() {
        return json_item;
    }

    public void setJson_item(String json_item) {
        this.json_item = json_item;
    }
}
