package by.legan.android.firealert.view.setting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import by.legan.android.firealert.databinding.ActivitySettingsBinding;

public class SettingsAct extends AppCompatActivity {
    ActivitySettingsBinding binding;
    SettingActModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(SettingActModel.class);

        setContentView(binding.getRoot());
    }
}
