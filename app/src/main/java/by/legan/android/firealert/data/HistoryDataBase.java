package by.legan.android.firealert.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import by.legan.android.firealert.data.repository.HistoryRepository;
import by.legan.android.firealert.data.model.HistoryItem;

@Database(entities = {HistoryItem.class}, version = 1, exportSchema = true)
public abstract class HistoryDataBase extends RoomDatabase {
    public abstract HistoryRepository dao_repository();

    public static HistoryDataBase getDataBase(Context context){
        return Room.databaseBuilder(context, HistoryDataBase.class, "history-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

}
