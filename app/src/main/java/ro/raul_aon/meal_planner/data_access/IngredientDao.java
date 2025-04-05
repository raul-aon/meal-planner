package ro.raul_aon.meal_planner.data_access;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ro.raul_aon.meal_planner.models.Ingredient;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredient ORDER BY name")
    LiveData<List<Ingredient>> getAll();

    @Query("SELECT * FROM ingredient where name = :val")
    Ingredient getByName(String val);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);
}
