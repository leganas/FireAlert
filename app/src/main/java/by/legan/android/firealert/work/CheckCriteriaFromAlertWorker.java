package by.legan.android.firealert.work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;
import java.util.concurrent.TimeUnit;

import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.service.BoilerService;
import java9.util.stream.StreamSupport;

import static by.legan.android.firealert.IncomingSmsReceiver.SMS_MSG;
import static by.legan.android.firealert.IncomingSmsReceiver.SMS_NUM;

public class CheckCriteriaFromAlertWorker extends Worker {
    public static final String BOILER_ID =  "by.legan.android.firealert.boiler_id";

    @lombok.Data
    public class ResultCheckCriteria {
        String name;
        Long boilerId;
        String message;
    }

    static final public String TAG = "Notification";
    static final public String NAME = "CheckCriteriaFromAlertWorker";

    public CheckCriteriaFromAlertWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(this.getClass().getName(), "CheckCriteriaFromAlertWorker");

        ResultCheckCriteria resultCheckCriteria = checkAlarmCriteria(getInputData().getString(SMS_NUM), getInputData().getString(SMS_MSG));
        if (resultCheckCriteria.getBoilerId() != null) {
            runNotification(resultCheckCriteria.name,resultCheckCriteria.boilerId, resultCheckCriteria.message);
            runHistoryLog(resultCheckCriteria.name,resultCheckCriteria.boilerId, resultCheckCriteria.message);
            return Result.success();
        } else return Result.failure();
    }

    public void runNotification(String name, Long boilerId, String message){
        Constraints constraints = new Constraints.Builder().build();
        Data.Builder data = new Data.Builder();
        data.putString("BOILER_NAME", name);
        data.putLong(SMS_NUM, boilerId);
        data.putString(SMS_MSG, message);

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(NotificationFireAlertWorker.class)
                .addTag(NotificationFireAlertWorker.TAG)
                .setInputData(data.build())
                .setInitialDelay(0, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build();
        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        workManager
                .beginUniqueWork(NotificationFireAlertWorker.NAME, ExistingWorkPolicy.REPLACE, request)
                .enqueue();
    }

    public void runHistoryLog(String name, Long boilerId, String message){
        Constraints constraints = new Constraints.Builder().build();
        Data.Builder data = new Data.Builder();
        data.putString("BOILER_NAME", name);
        data.putLong(SMS_NUM, boilerId);
        data.putString(SMS_MSG, message);

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(HistoryWorker.class)
                .addTag(HistoryWorker.TAG)
                .setInputData(data.build())
                .setInitialDelay(0, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build();
        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        workManager
                .beginUniqueWork(HistoryWorker.NAME, ExistingWorkPolicy.REPLACE, request)
                .enqueue();
    }


    public ResultCheckCriteria checkAlarmCriteria(String phoneNumber, String message){
        BoilerService service = new BoilerService(getApplicationContext());
        List<Boiler> boilers = service.getAll();
        ResultCheckCriteria result = new ResultCheckCriteria();
        StreamSupport.stream(boilers).forEach(boiler -> {
            if (phoneNumber.contains(boiler.getAlert_number())){
                StreamSupport.stream(boiler.getSmsEvents()).forEach(event -> {
                    if (message.contains(event.getSms_text())) {
                        result.setName(boiler.getName());
                        result.setBoilerId(boiler.getId());
                        result.setMessage(event.getAlert_text());
                    }
                });
            }
        });
        return result;
    }
}
