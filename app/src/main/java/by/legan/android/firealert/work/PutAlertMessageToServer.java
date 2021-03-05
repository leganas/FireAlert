package by.legan.android.firealert.work;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import by.legan.android.firealert.GlobalValue;
import by.legan.android.firealert.R;
import by.legan.android.firealert.data.dto.AlertMessage;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.service.BoilerService;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.TELEPHONY_SERVICE;

public class PutAlertMessageToServer extends Worker {
    static final public String TAG = "PutAlertMessageToServer";
    static final public String NAME = "PutAlertMessageToServer";
    private MediaPlayer mediaPlayer;

    public PutAlertMessageToServer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, this.getClass().getName()+": start");
        SharedPreferences settings = getApplicationContext().getSharedPreferences("GlobalValue", MODE_PRIVATE);
        if (settings.getBoolean("uploadFlag", false)) {
            String Url = settings.getString("url","host:port");
            BoilerService service = new BoilerService(getApplicationContext());
            Boiler boiler = service.getBoilerFromId(GlobalValue.AlertBoilerId);
            AlertMessage message = new AlertMessage();
            message.setMessage(GlobalValue.AlertMsg);
            message.setJson_intent(new Gson().toJson(boiler));
            message.setMessage_agent_id(settings.getString("message_agent_id", Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID)));

            // Set the Content-Type header
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(new MediaType("application","json"));
            HttpEntity<AlertMessage> requestEntity = new HttpEntity<AlertMessage>(message, requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the Jackson and String message converters
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
            ResponseEntity<AlertMessage> responseEntity = restTemplate.exchange(Url + "/MessageAlertPool/api/public/putAlertMessage", HttpMethod.PUT, requestEntity, AlertMessage.class);
            AlertMessage result = responseEntity.getBody();
            Log.d(TAG, result.toString());
        }
        Log.d(TAG, this.getClass().getName()+": end");
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }
}
