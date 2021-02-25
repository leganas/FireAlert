package by.legan.android.firealert.work;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

import by.legan.android.firealert.GlobalValue;
import by.legan.android.firealert.view.alert.AlertActivity;
import by.legan.android.firealert.R;

import static by.legan.android.firealert.IncomingSmsReceiver.SMS_MSG;
import static by.legan.android.firealert.IncomingSmsReceiver.SMS_NUM;
import static by.legan.android.firealert.work.CheckCriteriaFromAlertWorker.BOILER_ID;

public class NotificationFireAlertWorker extends Worker {
    static final public String TAG = "Notification";
    static final public String NAME = "NotificationFireAlertWorker";

    public NotificationFireAlertWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, this.getClass().getName()+": start");

        Intent fullScreenIntent = new Intent(getApplicationContext(), AlertActivity.class);
        GlobalValue.AlertBoilerId = getInputData().getLong(SMS_NUM, -1);
        GlobalValue.AlertMsg = getInputData().getString(SMS_MSG);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, fullScreenIntent, 0);
/*
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.textView, "Custom notification text");
        remoteViews.setOnClickPendingIntent(R.id.root, fullScreenPendingIntent);
*/

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), SMS_MSG)
                        .setSmallIcon(R.drawable.alarme)
                        .setContentTitle("Объект № " + getInputData().getLong(SMS_NUM, -1))
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setContentText(getInputData().getString(SMS_MSG))
//                        .setContent(remoteViews)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setFullScreenIntent(fullScreenPendingIntent, true);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(SMS_MSG, "channel_name", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("channel_description");
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.getNotificationChannel(SMS_MSG);
        notificationManager.notify(0, notificationBuilder.build());

        runSoundWorker();

        Log.d(TAG, this.getClass().getName()+": end");
        return Result.success();
    }

    private void runSoundWorker() {
        Constraints constraints = new Constraints.Builder().build();
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(AlertSoundWorker.class)
                .addTag(AlertSoundWorker.TAG)
                .setInitialDelay(0, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build();
        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        workManager
                .beginUniqueWork(AlertSoundWorker.NAME, ExistingWorkPolicy.REPLACE, request)
                .enqueue();
    }
}
