package by.legan.android.firealert.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import by.legan.android.firealert.data.model.Boiler;
import by.legan.android.firealert.data.repository.BoilerRepository;

@Database(entities = {Boiler.class}, version = 1, exportSchema = true)
public abstract class BoilerDataBase extends RoomDatabase {
    public abstract BoilerRepository dao_repository();

    public static BoilerDataBase getDataBase(Context context){
        return Room.databaseBuilder(context, BoilerDataBase.class, "boiler-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

}
