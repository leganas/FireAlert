package by.legan.android.firealert;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by AndreyLS on 20.02.2017.
 */

public class SMSEvent {
    String sms_text;
    String alert_text;
    Alert alert = SMSEvent.Alert.showAlertWindow;
    public enum Alert {
        none,
        showAlertWindow,
        showAlertWindow_SendAlertToServer,
        sendAlertToServer
    }


    public String getSms_text() {
        return sms_text;
    }

    public void setSms_text(String sms_text) {
        this.sms_text = sms_text;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public String getAlert_text() {
        return alert_text;
    }

    public void setAlert_text(String alert_text) {
        this.alert_text = alert_text;
    }

    public static class SMSEventConverter implements JsonSerializer<SMSEvent>, JsonDeserializer<SMSEvent> {
        @Override
        public SMSEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            String sms_text = "";
            if (object.has("sms_text")) sms_text= object.get("sms_text").getAsString();
            String alert_text = "";
            if (object.has("alert_text")) alert_text= object.get("alert_text").getAsString();
            String alertS = object.get("alert").getAsString();
            Alert alert;
            switch (alertS) {
                case "none":alert=Alert.none;
                    break;
                case "showAlertWindow":alert=Alert.showAlertWindow;
                    break;
                case "showAlertWindow_SendAlertToServer":alert=Alert.showAlertWindow_SendAlertToServer;
                    break;
                case "sendAlertToServer":alert=Alert.sendAlertToServer;
                    break;
                default: alert=Alert.none;
            }
            SMSEvent smsEvent = new SMSEvent();
            smsEvent.setSms_text(sms_text);
            smsEvent.setAlert_text(alert_text);
            smsEvent.setAlert(alert);
            return smsEvent;
        }

        @Override
        public JsonElement serialize(SMSEvent src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("sms_text", src.getSms_text());
            object.addProperty("alert_text", src.getAlert_text());
            object.addProperty("alert", src.getAlert().toString());
            return object;
        }
    }
}
