package ro.raul_aon.meal_planner.data_access;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ro.raul_aon.meal_planner.models.ShopListItem;

@Dao
public interface ShopListDao {
    @Query("SELECT * FROM shopListItem ORDER BY done, name")
    LiveData<List<ShopListItem>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShopListItem item);

    @Query("DELETE FROM shopListItem where done")
    void clear();
}
