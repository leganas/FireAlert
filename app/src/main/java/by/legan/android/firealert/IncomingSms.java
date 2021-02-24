package by.legan.android.firealert;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.andreyls.mytestprojectsolo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static by.legan.android.firealert.BoilerControlAct.ID_BOILER;

/**
 * Created by AndreyLS on 18.02.2017.
 */

public class IncomingSms extends BroadcastReceiver {
    public static final String SMS_MSG =  "by.legan.android.firealert.sms_msg";
    private static String channel_name = "IncomingSmsReceiver";
    private static String channel_description = "";

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channel_name;
            String description = channel_description;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(SMS_MSG, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


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
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
//                    toast.show();

                    BoilerLab boilerLab = BoilerLab.get(context);
                    ArrayList<Boiler> boilers;
                    boilers = (ArrayList<Boiler>) boilerLab.getBoilers();

                    for (int b=0; b < boilers.size();b++) {
                        if (boilers.get(b).getAlert_number() != null) Log.d("SmsReceiver", "Проверка номера : " + boilers.get(b).getAlert_number());
                        if (boilers.get(b).getAlert_number() != null) {
                            if (boilers.get(b).getAlert_number().equals(phoneNumber)) {
                                // Дальше проверяем есть ли у нас обозначеные эвенты
                                // если есть и нужно открывать активность и реагировать то делаем это
                                // если нет то забиваем хуй
                                Gson gson = getGson();

                                SMSEventArray smsEventArray;
                                smsEventArray = gson.fromJson(boilers.get(b).getJson_item(), SMSEventArray.class);

                                boolean flag = false;
                                if (smsEventArray.getEvents() != null) {
                                    for (int x = 0; x < smsEventArray.getEvents().size(); x++) {
                                        if (smsEventArray.getEvents().get(x).getAlert() != SMSEvent.Alert.none)
                                            flag = true;
                                    }
                                }
                                if (flag){
                                    Intent in = new Intent(context, AlertActivity.class);
                                    in.putExtra(ID_BOILER, boilers.get(b).get_id());
                                    in.putExtra(SMS_MSG, message);
                                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(in); // Для Android 10, требует выставления в настройках приложения Поверх всех окон



/*                                    Log.d("SmsReceiver", "StartNotification");
                                    createNotificationChannel(context);
                                    Intent fullScreenIntent = new Intent(context, AlertActivity.class);
                                    fullScreenIntent.setAction(Intent.ACTION_VIEW);
                                    fullScreenIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                    fullScreenIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    fullScreenIntent.putExtra(ID_BOILER, boilers.get(b).get_id());
                                    fullScreenIntent.putExtra(SMS_MSG, message);

                                    PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                                            fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                    NotificationCompat.Builder notificationBuilder =
                                            new NotificationCompat.Builder(context, SMS_MSG)
                                                    .setSmallIcon(R.drawable.alarme)
                                                    .setContentTitle("Incoming call")
                                                    .setContentText("(919) 555-1234")
                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setCategory(NotificationCompat.CATEGORY_CALL)
                                                    .setFullScreenIntent(fullScreenPendingIntent, true);

                                    Notification incomingCallNotification = notificationBuilder.build();

                                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                    // notificationId is a unique int for each notification that you must define
                                    notificationManager.notify(1010, incomingCallNotification);*/

                                }
                            }
                        }

                    }
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }


    @NonNull
    private Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(SMSEvent.class, new SMSEvent.SMSEventConverter());
        builder.registerTypeAdapter(SMSEventArray.class, new SMSEventArray.SMSEventArrayConverter());
        return builder.create();
    }
}
