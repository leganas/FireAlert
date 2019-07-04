package by.legan.android.firealert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import by.legan.android.firealert.database.history.HistoryLab;
import by.legan.android.firealert.database.history.History_item;
import com.andreyls.mytestprojectsolo.R;

import java.util.List;
import java.util.UUID;

/**
 * Created by AndreyLS on 24.01.2017.
 */

public class HistoryListFragment extends Fragment {
    TextView mTextView;

    RecyclerView recycleView;
    ListAdapter listAdapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_history, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        HistoryLab historyLab = new HistoryLab(getContext());
        List<History_item> history;
        history = historyLab.getHistory();

        switch (item.getItemId()) {
            case R.id.clear_history:
                for (int i=0; i<history.size();i++){
                    historyLab.removeHistory_item(history.get(i));
                    updateUI();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);

        mTextView = (TextView) view.findViewById(R.id.text_history);
        mTextView.setText("История тревог");

        recycleView = (RecyclerView) view.findViewById(R.id.history_recycler_view);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    public void updateUI(){
        HistoryLab historyLab = new HistoryLab(getContext());
        List<History_item> history;
        history = historyLab.getHistory();

        // Создаём адаптер для нашего recycleView
        if (listAdapter == null){
            listAdapter = new ListAdapter();
            listAdapter.setItems(history);
            recycleView.setAdapter(listAdapter);
        } else {
            // если мы сюда попали после поворота то адаптор уже создан
            // сделаем привязку к значениям и обновим
            listAdapter.setItems(history);
            listAdapter.notifyDataSetChanged();
        }
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        History_item item;
        private TextView boiler_name;
        private TextView alert_name;
        private TextView alert_msg;
        private TextView text_h_data;

        public ListHolder(View itemView) {
            super(itemView);
            boiler_name = (TextView) itemView.findViewById(R.id.boiler_name);
            alert_name = (TextView) itemView.findViewById(R.id.alert_name);
            alert_msg = (TextView) itemView.findViewById(R.id.alert_msg);
            text_h_data = (TextView) itemView.findViewById(R.id.text_h_data);
        }

        public void setItem(History_item item){
            this.item = item;

            UUID boiler_id = item.getIdBoiler();
            BoilerLab boilerLab = BoilerLab.get(getActivity());
            Boiler boiler = boilerLab.getBoiler(boiler_id);
            if (boiler != null) {
                if (boiler.getName() != null) boiler_name.setText(boiler.getName());
                if (item.getAlert_name() != null) alert_name.setText(item.getAlert_name());
                if (item.getAlert_msg() != null) alert_msg.setText(item.getAlert_msg());
                if (item.getDate_alert() != null) text_h_data.setText(item.getDate_alert());
            }
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<ListHolder>{
        List<History_item> items;

        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.history_item, parent, false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            History_item item = items.get(position);
            holder.setItem(item);
        }

        public void setItems(List<History_item> items){this.items = items;}

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
