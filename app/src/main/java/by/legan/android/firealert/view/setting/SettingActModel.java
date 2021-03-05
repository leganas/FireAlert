package by.legan.android.firealert.view.setting;

import android.app.Application;
import android.content.SharedPreferences;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import static android.content.Context.MODE_PRIVATE;

public class SettingActModel extends AndroidViewModel {
    public MutableLiveData<String> Url = new MutableLiveData<>();
    public MutableLiveData<String> message_agent_id = new MutableLiveData<>();
    public MutableLiveData<Boolean> uploadFlag = new MutableLiveData<>();
    public SharedPreferences settings;

    public SettingActModel(@NonNull Application application) {
        super(application);
        settings = getApplication().getSharedPreferences("GlobalValue", MODE_PRIVATE);
        Url.setValue(settings.getString("url","http://host:port"));
        message_agent_id.setValue(settings.getString("message_agent_id", Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID)));
        uploadFlag.setValue(settings.getBoolean("uploadFlag", false));
    }
}
