package by.legan.android.firealert.view.historyList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import by.legan.android.firealert.R;
import by.legan.android.firealert.data.model.HistoryItem;
import by.legan.android.firealert.databinding.FrHistoryBinding;

/**
 * Created by AndreyLS on 24.01.2017.
 */

public class HistoryListFragment extends Fragment {
    FrHistoryBinding binding;
    HistoryListFragmentModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(HistoryListFragmentModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FrHistoryBinding.inflate(inflater,container,false);
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_history, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_history:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI(){
        List<HistoryItem> history;
        history = model.getHistoryService().getHistory().getValue();

        // Создаём адаптер для нашего recycleView
        if (model.getHistoryListAdapter() == null){
            model.setHistoryListAdapter(new HistoryListAdapter());
            model.getHistoryListAdapter().setItems(history);
            binding.historyRecyclerView.setAdapter(model.getHistoryListAdapter());
        } else {
            // если мы сюда попали после поворота то адаптор уже создан
            // сделаем привязку к значениям и обновим
            model.getHistoryListAdapter().setItems(history);
            model.getHistoryListAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
