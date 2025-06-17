package ro.raul_aon.meal_planner.data_access;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ro.raul_aon.meal_planner.models.Recipe;
import ro.raul_aon.meal_planner.models.RecipeIngredient;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe ORDER BY name")
    LiveData<List<Recipe>> getAll();

    @Query("SELECT * FROM recipe where name = :val")
    Recipe getByName(String val);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipeingredient where recipeId = :val")
    LiveData<List<RecipeIngredient>> getRecipeIngredients(int val);

    @Query("SELECT * FROM recipeingredient where recipeId = :val")
    List<RecipeIngredient> getRecipeIngredientsNow(int val);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addIngredient(RecipeIngredient recipeIngredient);

    @Delete
    void deleteIngredient(RecipeIngredient recipeIngredient);

}
