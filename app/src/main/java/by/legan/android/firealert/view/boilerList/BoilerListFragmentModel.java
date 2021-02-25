package by.legan.android.firealert.view.boilerList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.service.BoilerService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoilerListFragmentModel extends AndroidViewModel {
    BoilerService boilerService;
    MutableLiveData<List<Boiler>> boilerList = new MutableLiveData<>();

    BoilerListAdapter listAdapter;

    public BoilerListFragmentModel(@NonNull Application application) {
        super(application);
        boilerService = new BoilerService(getApplication());
    }

    public MutableLiveData<List<Boiler>> loadAll(){
        boilerList.setValue(boilerService.getAll());
        return boilerList;
    }
}
