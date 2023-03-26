package ro.raul_aon.meal_planner.data_access;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import ro.raul_aon.meal_planner.models.Ingredient;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredient")
    public LiveData<List<Ingredient>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Ingredient ingredientsList);
}
