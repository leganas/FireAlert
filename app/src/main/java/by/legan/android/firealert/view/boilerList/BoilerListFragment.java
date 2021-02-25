package by.legan.android.firealert.view.boilerList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
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
