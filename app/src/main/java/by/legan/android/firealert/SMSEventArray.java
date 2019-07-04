package by.legan.android.firealert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by AndreyLS on 20.02.2017.
 */

public class SMSEventArray {
    ArrayList<SMSEvent> events;

    public SMSEventArray(){
        events = new ArrayList<>();
    }

    public SMSEventArray(ArrayList<SMSEvent> events) {
        this.events = events;
    }

    public ArrayList<SMSEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<SMSEvent> events) {
        this.events = events;
    }

    public static class SMSEventArrayConverter implements JsonSerializer<SMSEventArray>, JsonDeserializer<SMSEventArray> {

        @Override
        public SMSEventArray deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            JsonObject smsEventOb = object.get("smsEventArray").getAsJsonObject();
            JsonArray eventJson = smsEventOb.get("events").getAsJsonArray();

            SMSEventArray smsEventArray = new SMSEventArray();
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(SMSEvent.class, new SMSEvent.SMSEventConverter());
            Gson gson = builder.create();

            for (int i=0;i<eventJson.size();i++){
                SMSEvent event;
                JsonElement element = eventJson.get(i);
                event = gson.fromJson(element, SMSEvent.class);
                smsEventArray.getEvents().add(event);
            }
            return smsEventArray;
        }

        @Override
        public JsonElement serialize(SMSEventArray src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            JsonObject smsEventOb = new JsonObject();
            JsonArray eventsJson = new JsonArray();

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(SMSEvent.class, new SMSEvent.SMSEventConverter());
            Gson gson = builder.create();

            for (int i = 0; i < src.getEvents().size(); i++){
                eventsJson.add(gson.toJsonTree(src.getEvents().get(i),SMSEvent.class));
            }

//            smsEventOb.addProperty("tbName",Assets.users.tbName);
            smsEventOb.add("events",eventsJson);
            object.add("smsEventArray",smsEventOb);
            return object;
        }
    }
}
