package by.legan.android.firealert.data.repository;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.legan.android.firealert.data.model.HistoryItem;

@Dao
public interface HistoryRepository extends BaseCrudDAO_Repository<HistoryItem>{
    @Override
    @Query("SELECT * FROM HistoryItem WHERE Id IN (:id) LIMIT 1")
    HistoryItem get(Long id);

    @Override
    @Query("SELECT * FROM HistoryItem ORDER BY Id DESC LIMIT 100")
    List<HistoryItem> getAll();
}
