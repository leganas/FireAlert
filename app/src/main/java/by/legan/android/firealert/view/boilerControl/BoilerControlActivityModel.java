package by.legan.android.firealert.view.boilerControl;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.service.BoilerService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoilerControlActivityModel extends AndroidViewModel {
    public MutableLiveData<Long> boilerId = new MutableLiveData<>();


    public BoilerControlActivityModel(@NonNull Application application) {
        super(application);
    }
}
