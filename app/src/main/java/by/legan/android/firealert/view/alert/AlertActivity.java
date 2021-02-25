package by.legan.android.firealert.view.alert;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkManager;

import by.legan.android.firealert.GlobalValue;
import by.legan.android.firealert.R;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.databinding.ActivityAlertBinding;
import by.legan.android.firealert.work.AlertSoundWorker;

public class AlertActivity extends AppCompatActivity {
    ActivityAlertBinding binding;
    AlertActModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlertBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AlertActModel.class);
        binding.setModel(model);
        if (GlobalValue.AlertBoilerId != -1L) model.getBoilerFromId(GlobalValue.AlertBoilerId).observe(this, new Observer<Boiler>() {
            @Override
            public void onChanged(Boiler boiler) {
                binding.textOb.setText(boiler.getName());
            }
        });
        model.getMessage().setValue(GlobalValue.AlertMsg);
        model.getMessage().observe(this, msg1 -> binding.textAle.setText(msg1));

        binding.btnAlertalert.setOnClickListener(view -> {
            WorkManager.getInstance(getApplication()).cancelUniqueWork(AlertSoundWorker.NAME);
            finish();
        });
        binding.imageView7.setImageResource(R.drawable.alarme);
        setTurnScreenOn();
        setContentView(binding.getRoot());
    }

    private void setTurnScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        }
    }
}
