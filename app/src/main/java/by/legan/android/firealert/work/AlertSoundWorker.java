package by.legan.android.firealert.work;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import by.legan.android.firealert.R;

public class AlertSoundWorker extends Worker {
    static final public String TAG = "Notification";
    static final public String NAME = "AlertSoundWorker";
    private MediaPlayer mediaPlayer;

    public AlertSoundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, this.getClass().getName()+": start");
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.fire_alert);
        mediaPlayer.start();

        do {
            try {
                Thread.sleep(1000);
//                Log.d(TAG," | " + isStopped());
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        } while (!isStopped());

        Log.d(TAG, this.getClass().getName()+": end");
        return Result.success();
    }

    @Override
    public void onStopped() {
        mediaPlayer.stop();
        super.onStopped();
    }
}
