package by.legan.android.firealert.view.boilerControl;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import by.legan.android.firealert.databinding.ActivityBoilerControlBinding;

import static by.legan.android.firealert.utils.FragmentUtils.bindingFragmentToContentFrame;

public class BoilerControlActivity extends AppCompatActivity {
    ActivityBoilerControlBinding binding;
    BoilerControlActivityModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoilerControlBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(BoilerControlActivityModel.class);
        Long boilerId = getIntent().getLongExtra("Id",-1);
        model.getBoilerId().setValue(boilerId);
        bindingFragmentToContentFrame(getSupportFragmentManager(), new BoilerControlFragment(), binding.contentFrame.getId());
        setContentView(binding.getRoot());
    }

}
