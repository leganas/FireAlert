package by.legan.android.firealert.view.boilerList;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import by.legan.android.firealert.R;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.databinding.BoilerListFragmentBinding;
import by.legan.android.firealert.suport.LongResultCallback;
import by.legan.android.firealert.view.boilerControl.BoilerControlActivity;


/**
 * Created by AndreyLS on 24.01.2017.
 */

public class BoilerListFragment extends Fragment {
    public static final int REQUEST_CODE_PERMISSION_RECEIVE_SMS = 1;
    public static final int REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;

    BoilerListFragmentBinding binding;
    BoilerListFragmentModel model;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = BoilerListFragmentBinding.inflate(inflater,container,false);
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(BoilerListFragmentModel.class);

        binding.crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        model.setListAdapter(new BoilerListAdapter(getViewLifecycleOwner(), new LongResultCallback() {
            @Override
            public void run(Long value) {
                Intent intent = new Intent(getContext(),BoilerControlActivity.class);
                intent.putExtra("Id",value);
                startActivity(intent);
            }
        }));
        binding.crimeRecyclerView.setAdapter(model.getListAdapter());

        model.getBoilerList().observe(getViewLifecycleOwner(), new Observer<List<Boiler>>() {
            @Override
            public void onChanged(List<Boiler> boilers) {
                model.getListAdapter().setItems(boilers);
                model.getListAdapter().notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        checkPermissionStatusSMS();
    }

    private void checkPermissionStatusSMS() {
        int permissionStatus = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) requestPermissions(new String[] {Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_PERMISSION_RECEIVE_SMS);  else checkPermissionStatusSTORAGE();
    }
    private void checkPermissionStatusSTORAGE() {
        int permissionStatus = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_RECEIVE_SMS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.d(this.getClass().getName(),"RECEIVE_SMS permission granted");
                    checkPermissionStatusSTORAGE();
                } else {
                    // permission denied
                    Log.d(this.getClass().getName(),"RECEIVE_SMS permission denied");
                    checkPermissionStatusSTORAGE();
                }
                return;
            case REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.d(this.getClass().getName(),"STORAGE permission granted");
                } else {
                    // permission denied
                    Log.d(this.getClass().getName(),"STORAGE permission denied");
                }
                return;
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_boiler:
                newBoiler();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newBoiler() {
        Boiler boiler = model.boilerService.createNewBoiler();
        Intent in = new Intent(getContext(), BoilerControlActivity.class);
        in.putExtra("Id", boiler.getId());
        startActivity(in);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        model.loadAll();
    }
}
