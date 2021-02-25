package by.legan.android.firealert.data.repository;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.legan.android.firealert.data.model.Boiler;

@Dao
public interface BoilerRepository extends BaseCrudDAO_Repository<Boiler> {
    @Override
    @Query("SELECT * FROM boiler WHERE Id IN (:id) LIMIT 1")
    Boiler get(Long id);

    @Override
    @Query("SELECT * FROM Boiler ORDER BY Id DESC")
    List<Boiler> getAll();

    @Query("SELECT * FROM Boiler WHERE Id IN (:id_list)")
    List<Boiler> getFromIdListQuery(List<Long> id_list);

    @Query("SELECT * FROM boiler WHERE UPPER(name) LIKE UPPER(:name)")
    List<Boiler> getWhereName(String name);
}
