package by.legan.android.firealert.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import by.legan.android.firealert.R;
import by.legan.android.firealert.databinding.MainBinding;
import by.legan.android.firealert.view.about.About;
import by.legan.android.firealert.view.boilerList.BoilerListFragment;
import by.legan.android.firealert.view.historyList.HistoryListFragment;
import by.legan.android.firealert.view.setting.SettingsAct;
import by.legan.android.firealert.work.CheckCriteriaFromAlertWorker;
import by.legan.android.firealert.work.NotificationFireAlertWorker;

import static android.Manifest.permission.READ_SMS;
import static by.legan.android.firealert.IncomingSmsReceiver.SMS_MSG;
import static by.legan.android.firealert.IncomingSmsReceiver.SMS_NUM;
import static by.legan.android.firealert.utils.FragmentUtils.bindingFragmentToContentFrame;

/**
 * Created by AndreyLS on 26.01.2017.
 */

public class MainActivity extends AppCompatActivity implements  ActivityCompat.OnRequestPermissionsResultCallback{
    private MainBinding binding;
    private MainActivityModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainBinding.inflate(getLayoutInflater());
        binding.setView(this);
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainActivityModel.class);

        model.setMPlanetTitles(getResources().getStringArray(R.array.planets_array));
        binding.leftDrawer.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, model.getMPlanetTitles()));
        binding.leftDrawer.setOnItemClickListener(new DrawerItemClickListener() );
        bindingFragmentToContentFrame(getSupportFragmentManager(), new BoilerListFragment(), binding.contentFrame.getId());
        setContentView(binding.getRoot());
    }


    //  Слушатель для элементов списка в выдвижной панели
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void OnClickList(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentNews = new BoilerListFragment();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentNews).commit();
    }

    public void OnClickHistory(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment history = new HistoryListFragment();
        fragmentManager.beginTransaction().replace(R.id.content_frame, history).commit();
    }

    public void OnClickTest(View view){
        showNotificationAlarm(getApplicationContext(),"+375333290490","1");
    }


    private void showNotificationAlarm(Context context, String phoneNumber, String message) {
        Log.d("SmsReceiver", "Start CheckCriteriaFromAlertWorker");
        Constraints constraints = new Constraints.Builder().build();
        Data.Builder data = new Data.Builder();
        data.putString(SMS_NUM, phoneNumber);
        data.putString(SMS_MSG, message);

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(CheckCriteriaFromAlertWorker.class)
                .addTag(CheckCriteriaFromAlertWorker.TAG)
                .setInputData(data.build())
                .setInitialDelay(0, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build();
        WorkManager workManager = WorkManager.getInstance(context);
        workManager
                .beginUniqueWork(CheckCriteriaFromAlertWorker.NAME, ExistingWorkPolicy.REPLACE, request)
                .enqueue();
    }


    /** Смена фрагментов в основном окне программы */
    private void selectItem(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position){
            case 0:
                Fragment fragmentNews = new BoilerListFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentNews).commit();
                break;
            case 1:
                Fragment history = new HistoryListFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, history).commit();
                break;
            case 2:
                Intent in2 = new Intent(this, SettingsAct.class);
                startActivity(in2);
                break;
            case 3:
                Intent in3 = new Intent(this, About.class);
                startActivity(in3);
                break;
        }
        // обновим выбранный элемент списка и закрываем панель
        binding.leftDrawer.setItemChecked(position, true);
        binding.drawerLayout.closeDrawer(binding.leftDrawer);
    }
}
