package by.legan.android.firealert;

import android.app.Application;

import androidx.work.WorkManager;

import by.legan.android.firealert.work.AlertSoundWorker;

public class FireAlertApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WorkManager.getInstance(this).cancelUniqueWork(AlertSoundWorker.NAME);
    }
}
