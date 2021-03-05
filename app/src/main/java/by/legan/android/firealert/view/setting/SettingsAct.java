package by.legan.android.firealert.view.setting;

import android.os.Bundle;
import android.view.View;

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
        binding.setModel(model);
        binding.setView(this);
        setContentView(binding.getRoot());
    }

    public void onClickSave(View view){
        model.settings.edit().putString("url",model.Url.getValue()).apply();
        model.settings.edit().putString("message_agent_id",model.message_agent_id.getValue()).apply();
        model.settings.edit().putBoolean("uploadFlag",model.uploadFlag.getValue()).apply();
        finish();
    }

    public void onClickCancel(View view){
        finish();
    }
}
