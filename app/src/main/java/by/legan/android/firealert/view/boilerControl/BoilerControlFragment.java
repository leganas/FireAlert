package by.legan.android.firealert.view.boilerControl;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import by.legan.android.firealert.R;
import by.legan.android.firealert.data.dto.SMSEvent;
import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.databinding.FrBoilerControlBinding;
import java9.util.stream.Collectors;
import java9.util.stream.StreamSupport;

/**
 * Created by AndreyLS on 19.02.2017.
 */

public class BoilerControlFragment extends Fragment {
    FrBoilerControlBinding binding;
    BoilerControlFragmentModel model;
    BoilerControlActivityModel model_activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrBoilerControlBinding.inflate(inflater, container, false);
        model_activity = new ViewModelProvider(requireActivity()).get(BoilerControlActivityModel.class);
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(BoilerControlFragmentModel.class);
        binding.setModel(model);
        binding.setView(this);

        binding.tabHost.setup();
        TabHost.TabSpec tabSpec = binding.tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Параметры");
        binding.tabHost.addTab(tabSpec);
        tabSpec = binding.tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Оповещения");
        binding.tabHost.addTab(tabSpec);
        // это нужно будет менять в зависимости от того для чего мы вызвали активность
        binding.tabHost.setCurrentTab(0);

        binding.alertRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        model.setListAdapter(new SMSEventListAdapter(getViewLifecycleOwner(), value -> {
            List<SMSEvent> list =model.getAdapterData().getValue();
            list.remove((int)value);
            model.getAdapterData().setValue(list);
        }));
        binding.alertRecyclerView.setAdapter(model.getListAdapter());

        model_activity.getBoilerId().observe(getViewLifecycleOwner(), boilerId ->
                model.loadingBoiler(boilerId).observe(getViewLifecycleOwner(),
                        new Observer<Boiler>() {
                            @Override
                            public void onChanged(Boiler boiler) {
                                model.getAdapterData().setValue(boiler.getSmsEvents());
                            }
                        }));
        model.getAdapterData().observe(getViewLifecycleOwner(), new Observer<List<SMSEvent>>() {
            @Override
            public void onChanged(List<SMSEvent> smsEvents) {
                if (smsEvents != null) {
                    List<MutableLiveDataSMSEvent> result = StreamSupport.stream(smsEvents).map(event -> {
                        MutableLiveDataSMSEvent ldSMSEvent = new MutableLiveDataSMSEvent();
                        ldSMSEvent.setValue(event);
                        return ldSMSEvent;
                    }).collect(Collectors.toList());
                    updateSMSEventAdapterData(result);
                }
            }
        });
        return binding.getRoot();
    }

    public void onClickCreateSMSEvent(View view){
        model.addNewSMSEvent();
    }

    public void onClickSave(View view) {
        Log.d("SAVE", "SAVE");
        Boiler boiler = model.getBoiler().getValue();
        if (boiler != null) {
           boiler.setSmsEvents(model.getAdapterData().getValue());
           model.getService().saveBoiler(boiler);
        }
        getActivity().finish();
    }

    public void onClickCancel(View view){
        getActivity().finish();
    }

    private void updateSMSEventAdapterData(List<MutableLiveDataSMSEvent> liveSMSEvent){
        model.getListAdapter().setItems(liveSMSEvent);
        model.getListAdapter().notifyDataSetChanged();
        binding.invalidateAll();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_control, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_del_boiler:
                delBoiler();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void delBoiler() {
        model.getService().removeBoiler(model.getBoiler().getValue());
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        model.loadingBoiler(model_activity.getBoilerId().getValue());
    }
}
