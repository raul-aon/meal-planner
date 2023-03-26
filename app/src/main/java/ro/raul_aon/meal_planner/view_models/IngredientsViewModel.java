package ro.raul_aon.meal_planner.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.models.Ingredient;

public class IngredientsViewModel extends ViewModel {

    private LiveData<List<Ingredient>> ingredientsList =
            RecipeBankDatabase.getInstance().ingredientDao().getAll();

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredientsList;
    }

}