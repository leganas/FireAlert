package by.legan.android.firealert.view.boilerControl;

import android.app.Application;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.legan.android.firealert.R;
import by.legan.android.firealert.data.dto.SMSEvent;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.databinding.SmsEventBinding;
import by.legan.android.firealert.service.BoilerService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoilerControlFragmentModel extends AndroidViewModel {
    private MutableLiveData<Boiler> boiler = new MutableLiveData<>();
    private BoilerService service;
    private MutableLiveData<List<SMSEvent>> adapterData = new MutableLiveData<>();
    private SMSEventListAdapter listAdapter;

    public BoilerControlFragmentModel(@NonNull Application application) {
        super(application);
        service = new BoilerService(application);
    }

    public MutableLiveData<Boiler> loadingBoiler(Long Id){
        boiler.setValue(service.getBoilerFromId(Id));
        return boiler;
    }

    public MutableLiveData<List<SMSEvent>> addNewSMSEvent(){
        if (adapterData.getValue() == null) adapterData.setValue(new ArrayList<>());
        List<SMSEvent> list = adapterData.getValue();
        list.add(new SMSEvent());
        adapterData.setValue(list);
        return adapterData;
    }
}
