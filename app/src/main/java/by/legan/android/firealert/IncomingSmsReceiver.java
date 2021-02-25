package by.legan.android.firealert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import by.legan.android.firealert.work.CheckCriteriaFromAlertWorker;
import java9.util.stream.StreamSupport;

/**
 * Created by AndreyLS on 18.02.2017.
 */

public class IncomingSmsReceiver extends BroadcastReceiver {
    public static final String SMS_MSG =  "by.legan.android.firealert.sms_msg";
    public static final String SMS_NUM =  "by.legan.android.firealert.sms_number";

    @Override
    public void onReceive(Context context, Intent intent) {
        final SmsManager sms = SmsManager.getDefault();
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                StreamSupport.stream(Arrays.asList(pdusObj)).forEach(pdusObjSingle -> {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObjSingle);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    Log.i("SmsReceiver", "senderNum: "+ phoneNumber + "; message: " + message);
                    showNotificationAlarm(context, phoneNumber,message);
                });
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }

    private void showNotificationAlarm(Context context, String phoneNumber, String message) {
        Log.d("SmsReceiver", "Start CheckCriteriaFromAlertWorker");
        Constraints constraints = new Constraints.Builder().build();
        Data.Builder data = new Data.Builder();
        data.putString(SMS_NUM, phoneNumber);
        data.putString(SMS_MSG, message);

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(CheckCriteriaFromAlertWorker.class)
                .addTag(CheckCriteriaFromAlertWorker.TAG)
                .setInputData(data.build())
                .setInitialDelay(0, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build();
        WorkManager workManager = WorkManager.getInstance(context);
        workManager
                .beginUniqueWork(CheckCriteriaFromAlertWorker.NAME, ExistingWorkPolicy.REPLACE, request)
                .enqueue();
    }
}
