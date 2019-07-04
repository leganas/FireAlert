package by.legan.android.firealert;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.andreyls.mytestprojectsolo.R;

import java.util.List;

import static by.legan.android.firealert.BoilerControlAct.ID_BOILER;

/**
 * Created by AndreyLS on 24.01.2017.
 */

public class BoilerListFragment extends Fragment {
    TextView mTextView;

//    List<Boiler> itemsFromList;

    RecyclerView recycleView;
    ListAdapter listAdapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_boiler:
                updateUI();
                newBoiler();
                return true;
//            case R.id.menu_testAler:
//                Intent in = new Intent(getActivity(),AlertActivity.class);
//                in.putExtra(ID_BOILER, BoilerLab.get(getContext()).getBoilers().get(0).get_id());
//                in.putExtra(IncomingSms.SMS_MSG,"12");
//                startActivity(in);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newBoiler() {
        Boiler boiler = new Boiler();
        BoilerLab.get(getContext()).addBoiler(boiler);

        Intent in = new Intent(getContext(),BoilerControlAct.class);
        in.putExtra(ID_BOILER,boiler.get_id().toString());
        startActivity(in);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment,container,false);

        mTextView = (TextView) view.findViewById(R.id.textView);
        mTextView.setText("Список объектов");

        recycleView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    public void updateUI(){
        BoilerLab boilerLab = BoilerLab.get(getContext());
        List<Boiler> boilers;
        boilers = boilerLab.getBoilers();

        // Создаём адаптер для нашего recycleView
        if (listAdapter == null){
            listAdapter = new ListAdapter();
            listAdapter.setItems(boilers);
            recycleView.setAdapter(listAdapter);
        } else {
            // если мы сюда попали после поворота то адаптор уже создан
            // сделаем привязку к значениям и обновим
            listAdapter.setItems(boilers);
            listAdapter.notifyDataSetChanged();
        }
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        Boiler item;
        private TextView mText;
        private Button btn;

        public ListHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.item_name);
            btn = (Button) itemView.findViewById(R.id.button_control);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getContext(),BoilerControlAct.class);
                    in.putExtra(ID_BOILER,item.get_id().toString());
                    startActivity(in);
                }
            });
        }

        public void setItem(Boiler item){
            this.item = item;
            mText.setText(item.name);
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<ListHolder>{
        List<Boiler> items;

        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            Boiler item = items.get(position);
            holder.setItem(item);
        }

        public void setItems(List<Boiler> items){this.items = items;}

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
