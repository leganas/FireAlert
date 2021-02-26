package by.legan.android.firealert.work.requestPermissions;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import by.legan.android.firealert.R;

public class RequestPermissionSMSWorker extends Worker {
    static final public String TAG = "RequestPermission";
    static final public String NAME = "RequestPermissionSMSWorker";

    public RequestPermissionSMSWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, this.getClass().getName() + ": start");


        Log.d(TAG, this.getClass().getName() + ": end");
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }
}