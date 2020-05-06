package by.legan.android.firealert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import static by.legan.android.firealert.BoilerControlAct.ID_BOILER;

/**
 * Created by AndreyLS on 18.02.2017.
 */

public class IncomingSms extends BroadcastReceiver {
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
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                    BoilerLab boilerLab = BoilerLab.get(context);
                    ArrayList<Boiler> boilers;
                    boilers = (ArrayList<Boiler>) boilerLab.getBoilers();

                    for (int b=0;i<boilers.size();b++) {
                        Log.d("SmsReceiver", "Проверка номера : " + boilers.get(b).getAlert_number());
                        if (boilers.get(b).getAlert_number() != null) {
                            if (boilers.get(b).getAlert_number().equals(phoneNumber)) {
                                Intent in = new Intent(context, AlertActivity.class);
                                in.putExtra(ID_BOILER, boilers.get(b).get_id());
                                in.putExtra(SMS_MSG, message);
                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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
                                if (flag) context.startActivity(in);
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
