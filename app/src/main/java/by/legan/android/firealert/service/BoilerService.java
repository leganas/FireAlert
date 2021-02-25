package by.legan.android.firealert.service;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import by.legan.android.firealert.data.BoilerDataBase;
import by.legan.android.firealert.data.dto.SMSEvent;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.data.repository.BoilerRepository;
import lombok.Data;

@Data
public class BoilerService {
    private final Context context;
    private BoilerRepository dao_repository;

    public BoilerService(Context context) {
        this.context = context;
        dao_repository = BoilerDataBase.getDataBase(getContext()).dao_repository();
    }

    public Boiler createNewBoiler(){
        Boiler boiler= new Boiler();
        boiler.setId(dao_repository.insert(boiler));
        return boiler;
    }

    public int removeBoiler(Boiler boiler){
        return dao_repository.delete(boiler);
    }

    public Boiler getBoilerFromId(Long Id){
        return dao_repository.get(Id);
    }

    public List<Boiler> getAll(){
        return dao_repository.getAll();
    }

    public void saveBoiler(Boiler boiler) {
        if (boiler != null)
            if (boiler.getId() == null) {
                dao_repository.insert(boiler);
            } else {
                dao_repository.update(boiler);
            }
    }

}
