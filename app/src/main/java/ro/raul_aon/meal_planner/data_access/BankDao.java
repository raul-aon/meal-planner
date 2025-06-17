package ro.raul_aon.meal_planner.data_access;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ro.raul_aon.meal_planner.models.BankItem;

@Dao
public interface BankDao {

    @Query("SELECT * FROM bankItem")
    LiveData<List<BankItem>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BankItem item);

    @Query("DELETE FROM bankItem where mealsLeft=0")
    void clear();
}
