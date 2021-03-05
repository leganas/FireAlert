package by.legan.android.firealert.data.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BaseCrudDAO_Repository<T>  {
    public T get(Long id);
    @Insert
    public Long insert(T object);
    @Update
    public int update(T object);
    @Delete
    public int delete(T object);

    public List<T> getAll();
}
