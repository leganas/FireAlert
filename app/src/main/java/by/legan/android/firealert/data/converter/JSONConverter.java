package by.legan.android.firealert.data.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import by.legan.android.firealert.data.dto.SMSEvent;

public class JSONConverter {
    @TypeConverter
    public String ListString_toJSON(List<String> list) {
        return new Gson().toJson(list);
    }
    @TypeConverter
    public List<String> json_toListString(String data) {
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public String ListSMSEvent_toJSON(List<SMSEvent> list) {
        return new Gson().toJson(list);
    }
    @TypeConverter
    public List<SMSEvent> json_toListSMSEvent(String data) {
        Type listType = new TypeToken<List<SMSEvent>>(){}.getType();
        return new Gson().fromJson(data, listType);
    }

}
