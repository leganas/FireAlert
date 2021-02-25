package by.legan.android.firealert.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainActivityModel extends AndroidViewModel {
    private String[] mPlanetTitles;

    public MainActivityModel(@NonNull Application application) {
        super(application);
    }
}
