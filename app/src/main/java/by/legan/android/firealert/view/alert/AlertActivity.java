package by.legan.android.firealert.view.alert;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.WorkManager;

import by.legan.android.firealert.R;
import by.legan.android.firealert.work.AlertSoundWorker;

import static by.legan.android.firealert.IncomingSmsReceiver.SMS_MSG;

public class AlertActivity extends AppCompatActivity {
    Button btalert;
    private ImageView mImageView;
    TextView text_ale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        setTurnScreenOn();

        String message = getIntent().getStringExtra(SMS_MSG);
        Log.d("AlertAct",""+message);

        if (text_ale.getText().equals("внимание")) {
            if (message != null) text_ale.setText(message);
        }

        btalert = (Button) findViewById(R.id.btn_alertalert);
        btalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance(getApplication()).cancelUniqueWork(AlertSoundWorker.NAME);
                finish();
            }
        });
        mImageView = (ImageView) findViewById(R.id.imageView7);
        mImageView.setImageResource(R.drawable.alarme);
    }

    private void setTurnScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
