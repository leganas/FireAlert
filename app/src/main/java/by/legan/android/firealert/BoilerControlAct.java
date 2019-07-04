package by.legan.android.firealert;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.andreyls.mytestprojectsolo.R;

import java.util.UUID;

public class BoilerControlAct extends AppCompatActivity {

    public static final String ID_BOILER =  "com.leganas.firealert.mobile.answer_is_true";
    private UUID boiler_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank);

        boiler_id = UUID.fromString(getIntent().getStringExtra(ID_BOILER));
        Bundle args = new Bundle();
        args.putSerializable(ID_BOILER,boiler_id);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment boilerControl = new BoilerControlFragment();
        boilerControl.setRetainInstance(true);
        boilerControl.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.blank, boilerControl).commit();
    }

}
