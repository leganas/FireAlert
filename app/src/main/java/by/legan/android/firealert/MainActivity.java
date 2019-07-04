package by.legan.android.firealert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.andreyls.mytestprojectsolo.R;

/**
 * Created by AndreyLS on 26.01.2017.
 */

public class MainActivity extends AppCompatActivity {

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Button btList,btHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


//        Setting.IPAdressFromClient = "192.168.0.102";
//        Assets.clientController = new ClientController("MobileClient");
//
//        ClientMessage.RequestAutorization msg = new ClientMessage.RequestAutorization();
//        msg.login = "MobileUser";
//        Assets.clientController.addClientMessageToQuery(msg);

        btList = (Button) findViewById(R.id.bt_list);
        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragmentNews = new BoilerListFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentNews).commit();
            }
        });
        btHistory = (Button) findViewById(R.id.bt_history);
        btHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment history = new HistoryListFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, history).commit();
            }
        });

        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        FragmentManager fm = getSupportFragmentManager();
        // Ищем фрагмент (это если после поворота то он будет уже)
        Fragment fragment = fm.findFragmentById(R.id.content_frame);
        // Если не нашли то создаём новый
        if (fragment == null) {
            fragment = new BoilerListFragment();
            fm.beginTransaction()
                    .add(R.id.content_frame,fragment)
                    .commit();
        }


        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener() );

    }

    //  Слушатель для элементов списка в выдвижной панели
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    View mapView;

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
                Intent in2 = new Intent(this,SettingsAct.class);
                startActivity(in2);
                break;
            case 3:
                Intent in3 = new Intent(this,About.class);
                startActivity(in3);
                break;
        }
        // обновим выбранный элемент списка и закрываем панель
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getActionBar().setTitle(mTitle);
    }
}
