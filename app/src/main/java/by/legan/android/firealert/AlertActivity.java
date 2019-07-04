package by.legan.android.firealert;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import by.legan.android.firealert.database.history.HistoryLab;
import by.legan.android.firealert.database.history.History_item;
import com.andreyls.mytestprojectsolo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

import static by.legan.android.firealert.BoilerControlAct.ID_BOILER;
import static by.legan.android.firealert.IncomingSms.SMS_MSG;

public class AlertActivity extends AppCompatActivity {
    Button btalert;
    private ImageView mImageView;
    MediaPlayer mediaPlayer;
    TextView text_ob, text_ale;

    Boiler boiler;
    SMSEventArray smsEventArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);


        UUID boiler_id = (UUID) getIntent().getSerializableExtra(ID_BOILER);
        String message = getIntent().getStringExtra(SMS_MSG);

        Log.d("AlertAct",""+boiler_id);
        Log.d("AlertAct",""+message);

        text_ob = (TextView) findViewById(R.id.text_ob);
        text_ale = (TextView) findViewById(R.id.text_ale);

        BoilerLab boilerLab = BoilerLab.get(this);
        boiler = boilerLab.getBoiler(boiler_id);
        if (boiler != null) {
            if (boiler.getName() != null) {
                text_ob.setText(boiler.getName().toString());
            }
            if (boiler.getJson_item() != null) {
                Gson gson = getGson();
                smsEventArray = gson.fromJson(boiler.getJson_item(), SMSEventArray.class);
            };

            if (smsEventArray != null) {
                for (int i=0; i<smsEventArray.getEvents().size();i++) {
                    SMSEvent ev = smsEventArray.getEvents().get(i);
                    if (message.equals(ev.getSms_text())) {
                        if (ev.alert_text != null) {
                            text_ale.setText(ev.alert_text);
                        }
                    }
                }
            }
        }
        if (text_ale.getText().equals("внимание")) {
            if (message != null) text_ale.setText(message);
        }
        btalert = (Button) findViewById(R.id.btn_alertalert);
        btalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                finish();
            }
        });

        History_item item = new History_item();
        item.setIdBoiler(boiler_id);
        item.setAlert_name(text_ale.getText().toString());
        item.setAlert_msg("ТРЕВОГА");
        item.setDate_alert(History_item.getDateString());
        HistoryLab.get(this).addHistory_item(item);


        mImageView = (ImageView) findViewById(R.id.imageView7);
        mImageView.setImageResource(R.drawable.alarme);
        mediaPlayer = MediaPlayer.create(this, R.raw.fire_alert);
        mediaPlayer.start();
    }

    @NonNull
    private Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(SMSEvent.class, new SMSEvent.SMSEventConverter());
        builder.registerTypeAdapter(SMSEventArray.class, new SMSEventArray.SMSEventArrayConverter());
        return builder.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
