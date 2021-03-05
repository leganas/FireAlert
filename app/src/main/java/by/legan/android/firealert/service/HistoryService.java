package by.legan.android.firealert.service;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.legan.android.firealert.data.BoilerDataBase;
import by.legan.android.firealert.data.HistoryDataBase;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.data.model.HistoryItem;
import by.legan.android.firealert.data.repository.HistoryRepository;
import lombok.Data;

@Data
public class HistoryService {
    private final Context context;
    private HistoryRepository dao_repository;
    private MutableLiveData<List<HistoryItem>> historyList = new MutableLiveData<>();

    public HistoryService(Context context) {
        this.context = context;
        dao_repository = HistoryDataBase.getDataBase(getContext()).dao_repository();
    }

    public MutableLiveData<List<HistoryItem>> getHistory(){
        historyList.setValue(dao_repository.getAll());
        return historyList;
    }

    public long saveToHistory(HistoryItem item){
        return dao_repository.insert(item);
    }

    public void clearAll(){
        dao_repository.deleteAll();
    }
}
