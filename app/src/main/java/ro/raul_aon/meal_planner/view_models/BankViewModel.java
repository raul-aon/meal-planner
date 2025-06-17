package ro.raul_aon.meal_planner.view_models;

import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.handlers.AddClearHandler;
import ro.raul_aon.meal_planner.models.BankItem;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.models.Recipe;
import ro.raul_aon.meal_planner.models.RecipeIngredient;
import ro.raul_aon.meal_planner.models.ShopListItem;

public class BankViewModel extends ViewModel implements AddClearHandler {

    private String bankMealName;
    public String getBankMealName() {
        return bankMealName;
    }

    public void setBankMealName(String bankMealName) {
        this.bankMealName = bankMealName;
    }

    @Override
    public void onAddClick(View view) {
        BankItem bankItem = new BankItem();
        bankItem.name = bankMealName;
        bankMealName = "";
        ((EditText)view.getRootView().findViewById(R.id.bankMealName)).setText("");

        RecipeBankDatabase.databaseWriteExecutor.execute(() -> {
            Recipe rec = RecipeBankDatabase.getInstance().recipeDao().getByName(bankItem.name);
            if(rec != null){
                bankItem.mealsLeft = rec.servings;
            }
            RecipeBankDatabase.getInstance().bankDao().insert(bankItem);

            List<RecipeIngredient> list = RecipeBankDatabase.getInstance().recipeDao().getRecipeIngredientsNow(Objects.requireNonNull(rec).id);
            list.forEach((ing) ->{
                Ingredient ingredient = RecipeBankDatabase.getInstance().ingredientDao().getById(ing.ingredientId);
                ShopListItem sli = new ShopListItem();
                sli.name = ingredient.name;
                sli.units = rec.reheat ? ing.quantity / ingredient.unitsPerPack : ing.quantity * rec.servings / ingredient.unitsPerPack;
                RecipeBankDatabase.getInstance().shopListDao().insert(sli);
            });

        });
    }

    @Override
    public void onClearClick(View view) {

    }
}