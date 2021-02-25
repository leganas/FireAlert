package by.legan.android.firealert.view.historyList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import by.legan.android.firealert.service.HistoryService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryListFragmentModel extends AndroidViewModel {
    HistoryService historyService;
    HistoryListAdapter historyListAdapter;

    public HistoryListFragmentModel(@NonNull Application application) {
        super(application);
        historyService = new HistoryService(getApplication());
    }
}
