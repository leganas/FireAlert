package by.legan.android.firealert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.service.BoilerService;
import by.legan.android.firealert.view.alert.AlertActivity;
import by.legan.android.firealert.work.NotificationFireAlertWorker;

/**
 * Created by AndreyLS on 18.02.2017.
 */

public class IncomingSmsReceiver extends BroadcastReceiver {
    public static final String SMS_MSG =  "by.legan.android.firealert.sms_msg";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the object of SmsManager
        final SmsManager sms = SmsManager.getDefault();
            // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                    BoilerService boilerService = new BoilerService(context);
                    List<Boiler> boilers = boilerService.getAll();
                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }

    private void showNotificationAlarm(Context context, String message, ArrayList<Boiler> boilers, int b) {
        Log.d("SmsReceiver", "StartNotification");
        Constraints constraints = new Constraints.Builder()
                .build();

        Data.Builder data = new Data.Builder();
        data.putString(SMS_MSG, message);

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(NotificationFireAlertWorker.class)
                .addTag(NotificationFireAlertWorker.TAG)
                .setInputData(data.build())
                .setInitialDelay(0, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build();
        WorkManager workManager = WorkManager.getInstance(context);
        workManager
                .beginUniqueWork(NotificationFireAlertWorker.NAME, ExistingWorkPolicy.KEEP, request)
                .enqueue();
    }

    /* Требуется , но если его добавить то перестанут работать уведомления (не вылазят на передний план)
       и удаление этого разрешения не помогает
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
       в настройках приложения появлятся возможность выставить приложению флаг "на предний план"
     */
    private void showActivityBADRelise(Context context, String message, ArrayList<Boiler> boilers, int b) {
        Intent in = new Intent(context, AlertActivity.class);
//        in.putExtra(ID_BOILER, boilers.get(b).get_id());
        in.putExtra(SMS_MSG, message);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in); // Для Android 10, требует выставления в настройках приложения Поверх всех окон
    }
}
