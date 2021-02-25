package by.legan.android.firealert.view.alert;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.service.BoilerService;
import by.legan.android.firealert.service.HistoryService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertActModel extends AndroidViewModel {
    MutableLiveData<Boiler> boiler = new MutableLiveData<>();
    MutableLiveData<String> message = new MutableLiveData<>();

    BoilerService boilerService;
    HistoryService historyService;

    public AlertActModel(@NonNull Application application) {
        super(application);
        boilerService = new BoilerService(getApplication());
        historyService = new HistoryService(getApplication());
    }

    public MutableLiveData<Boiler> getBoilerFromId(Long Id){
        if (Id != -1) boiler.setValue(boilerService.getBoilerFromId(Id));
        return boiler;
    }
}
