package by.legan.android.firealert;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.andreyls.mytestprojectsolo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.UUID;

import static by.legan.android.firealert.BoilerControlAct.ID_BOILER;

/**
 * Created by AndreyLS on 19.02.2017.
 */

public class BoilerControlFragment extends Fragment {
    EditText edit_name,edit_address,edit_tel,edit_tel_mobile,edit_tel_alert;
    Button bt_save,bt_cansel,bt_addAlert;
    Boiler boiler;
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    SMSEventArray smsEventArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.boiler_control,container,false);

        TabHost tabHost = (TabHost) view.findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Параметры");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Оповещения");
        tabHost.addTab(tabSpec);

        // это нужно будет менять в зависимости от того для чего мы вызвали активность
        tabHost.setCurrentTab(0);

        edit_name = (EditText) view.findViewById(R.id.edit_name_boiler);
        edit_address = (EditText) view.findViewById(R.id.edit_address_boiler);
        edit_tel = (EditText) view.findViewById(R.id.edit_tel_boiler);
        edit_tel_mobile= (EditText) view.findViewById(R.id.edit_tel_mobile);
        edit_tel_alert= (EditText) view.findViewById(R.id.edit_tel_alert);

        bt_save = (Button) view.findViewById(R.id.button_save);
        bt_cansel = (Button) view.findViewById(R.id.button_cancel);
        bt_addAlert = (Button) view.findViewById(R.id.button_addalert);

        bt_addAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smsEventArray.getEvents().add(new SMSEvent());
                update();
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boiler.setName(edit_name.getText().toString());
                boiler.setAddress(edit_address.getText().toString());
                boiler.setPhone_number_stationary(edit_tel.getText().toString());
                boiler.setPhone_number_mobile(edit_tel_mobile.getText().toString());
                boiler.setAlert_number(edit_tel_alert.getText().toString());

                Gson gson = getGson();
                boiler.setJson_item(gson.toJson(smsEventArray,SMSEventArray.class));

                BoilerLab boilerLab = BoilerLab.get(getActivity());
                boilerLab.updateBoiler(boiler);

                getActivity().finish();
            }
        });

        bt_cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.alert_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        recyclerView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        update();
        return view;
    }

    public void update(){
        edit_name.setText(boiler.getName());
        edit_address.setText(boiler.getAddress());
        edit_tel.setText(boiler.getPhone_number_stationary());
        edit_tel_mobile.setText(boiler.getPhone_number_mobile());
        edit_tel_alert.setText(boiler.getAlert_number());

        // Создаём адаптер для нашего recycleView
        if (listAdapter == null){
            listAdapter = new ListAdapter();
            listAdapter.setItems(smsEventArray.getEvents());
            recyclerView.setAdapter(listAdapter);
        } else {
            // если мы сюда попали после поворота то адаптор уже создан
            // сделаем привязку к значениям и обновим
            listAdapter.setItems(smsEventArray.getEvents());
            listAdapter.notifyDataSetChanged();
        }


    }
    // Это представления каждого элемента списка
    public class ListHolder extends RecyclerView.ViewHolder {
        SMSEvent item;
        private Button btn;
        private EditText edit,edit2;
        private Spinner spinner;

        public ListHolder(final View itemView) {
            super(itemView);
            btn = (Button) itemView.findViewById(R.id.button_del_alert);
            edit = (EditText) itemView.findViewById(R.id.editText_sms);
            edit2 = (EditText) itemView.findViewById(R.id.edit_alertext);
            spinner = (Spinner) itemView.findViewById(R.id.spinner_alert);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    smsEventArray.getEvents().remove(item);
                    update();
                }
            });



            edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    item.setSms_text(edit.getText().toString());
                }
            });
            edit2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    item.setAlert_text(edit2.getText().toString());
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.d("Хуй",""+i);
                    switch (i) {
                        case 0:item.setAlert(SMSEvent.Alert.none);
                            break;
                        case 1:item.setAlert(SMSEvent.Alert.showAlertWindow);
                            break;
                        case 2:item.setAlert(SMSEvent.Alert.showAlertWindow_SendAlertToServer);
                            break;
                        case 3:item.setAlert(SMSEvent.Alert.sendAlertToServer);
                            break;
                        default: item.setAlert(SMSEvent.Alert.none);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }


        public void setItem(final SMSEvent item){
            this.item = item;
            if (item.getSms_text() != null) edit.setText(item.getSms_text());
            if (item.getAlert_text() != null) edit2.setText(item.getAlert_text());
            if (item.getAlert() != null) {
                if (item.getAlert() == SMSEvent.Alert.none) {
                    spinner.setSelection(0);
                }
                if (item.getAlert() == SMSEvent.Alert.showAlertWindow) {
                    spinner.setSelection(1);
                }
                if (item.getAlert() == SMSEvent.Alert.showAlertWindow_SendAlertToServer) {
                    spinner.setSelection(2);
                }
                if (item.getAlert() == SMSEvent.Alert.sendAlertToServer) {
                    spinner.setSelection(3);
                }
            }
        }
    }

    // Это адаптер который организует работу с RecyclerView
    public class ListAdapter extends RecyclerView.Adapter<ListHolder>{
        ArrayList<SMSEvent> items;

        // Тут мы создаём для каждого элемента списка представление и передаём его нашему Холдеру ,
        // который назначит всякие реакции на события и всю логику сделает
        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.sms_event, parent, false);
            return new ListHolder(view);
        }

        // После создания представления, передадим холдеру данные, пусть заполнить поля
        // и так до конца списка
        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            SMSEvent item = items.get(position);
            holder.setItem(item);
        }

        public void setItems(ArrayList<SMSEvent> items){this.items = items;}

        // При помощи этого метода адаптер знает сколько у нас элементов в списке
        // короче эта реализация адаптера вызывает этот метод чтобы значть столько элементов создать
        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID boiler_id = (UUID) getArguments().getSerializable(ID_BOILER);
        BoilerLab boilerLab = BoilerLab.get(getActivity());
        boiler = boilerLab.getBoiler(boiler_id);

        if (boiler.getJson_item()!= null) {
            Gson gson = getGson();

            smsEventArray = gson.fromJson(boiler.getJson_item(),SMSEventArray.class);
        } else {
            smsEventArray = new SMSEventArray(); // создаём пустой массив
        }

    }

    @NonNull
    private Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(SMSEvent.class, new SMSEvent.SMSEventConverter());
        builder.registerTypeAdapter(SMSEventArray.class, new SMSEventArray.SMSEventArrayConverter());
        return builder.create();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_control, menu);
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
        BoilerLab boilerLab = BoilerLab.get(getActivity());
        boilerLab.removeBoiler(boiler);
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }
}
