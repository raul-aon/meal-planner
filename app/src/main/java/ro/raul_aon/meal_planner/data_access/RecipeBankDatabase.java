package ro.raul_aon.meal_planner.data_access;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.models.ShopListItem;

@Database(entities = {Ingredient.class, ShopListItem.class}, version = 1, exportSchema = false)
public abstract class RecipeBankDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static RecipeBankDatabase instance = null;

    public static void startDbInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(
                    context, RecipeBankDatabase.class, "recipe-bank").build();
        }
    };

    public static RecipeBankDatabase getInstance() {
        return instance;
    };

    public abstract IngredientDao ingredientDao();
    public abstract ShopListDao shopListDao();
}
