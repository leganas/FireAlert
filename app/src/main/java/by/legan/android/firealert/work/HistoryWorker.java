package by.legan.android.firealert.work;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import by.legan.android.firealert.R;
import by.legan.android.firealert.data.model.HistoryItem;
import by.legan.android.firealert.service.HistoryService;
import by.legan.android.firealert.utils.DateUtils;

import static by.legan.android.firealert.IncomingSmsReceiver.SMS_MSG;

public class HistoryWorker extends Worker {
    static final public String TAG = "Notification";
    static final public String NAME = "HistoryWorker";

    public HistoryWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, this.getClass().getName()+": start");
        HistoryService historyService = new HistoryService(getApplicationContext());
        HistoryItem historyItem = new HistoryItem();
        getInputData().getString(SMS_MSG);

        historyItem.setAlertMessage(getInputData().getString(SMS_MSG));
        historyItem.setAlertName(getInputData().getString("BOILER_NAME"));
        historyItem.setDate_alert(DateUtils.getCurrentBestDate().toString());

        historyService.saveToHistory(historyItem);

        Log.d(TAG, this.getClass().getName()+": end");
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }
}
