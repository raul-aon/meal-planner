package ro.raul_aon.meal_planner.view_models;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.handlers.AddClearHandler;
import ro.raul_aon.meal_planner.handlers.EditButtonsClickHandler;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.models.Recipe;
import ro.raul_aon.meal_planner.models.RecipeIngredient;

public class RecipeEditViewModel extends ViewModel implements EditButtonsClickHandler, AddClearHandler {
    public boolean isNew;
    public Recipe recipe;

    public String getName(){
        return recipe.name;
    }
    public void setName(String value){
        recipe.name = value;
    }

    public String getServings(){ return Integer.toString(recipe.servings); }
    public void setServings(String value){
        try{
            recipe.servings = Integer.parseInt(value);
        }catch (Exception e){
            recipe.servings = 1;
        }
    }
    public String getInstructions(){
        return recipe.instructions;
    }
    public void setInstructions(String value){
        recipe.instructions = value;
    }
    public boolean getReheat(){
        return recipe.reheat;
    }
    public void setReheat(boolean value){
        recipe.reheat = value;
    }

    public MutableLiveData<Boolean> close = new MutableLiveData<>();
    public LiveData<Boolean> getCloseEvent() {
        return this.close;
    }

    @Override
    public void onSaveClick(View view) {
        RecipeBankDatabase.databaseWriteExecutor.execute(() -> RecipeBankDatabase.getInstance().recipeDao().insert(recipe));
        onCancelClick(view);
    }

    @Override
    public void onCancelClick(View view) {
        this.close.setValue(true);
    }

    @Override
    public void onDeleteClick(View view) {
        RecipeBankDatabase.databaseWriteExecutor.execute(() -> RecipeBankDatabase.getInstance().recipeDao().delete(recipe));
        onCancelClick(view);
    }

    private String newIngName;

    public String getNewIngName() {
        return newIngName;
    }

    public void setNewIngName(String newIngName) {
        this.newIngName = newIngName;
    }

    public String getNewIngQty(){ return Float.toString(this.newIngQty); }
    public void setNewIngQty(String value){
        try{
            this.newIngQty = Float.parseFloat(value);
        }catch (Exception e){
            this.newIngQty = 1;
        }
    }

    private float newIngQty;

    private String recipeTotals;

    public String getRecipeTotals() {
        return recipeTotals;
    }

    public void setRecipeTotals(String recipeTotals) {
        this.recipeTotals = recipeTotals;
    }

    @Override
    public void onAddClick(View view) {
        RecipeBankDatabase.databaseWriteExecutor.execute(() -> {
            Ingredient ing = RecipeBankDatabase.getInstance().ingredientDao().getByName(this.newIngName);
            if(ing == null) return;
            RecipeIngredient ri = new RecipeIngredient();
            ri.ingredientId = ing.id;
            ri.recipeId = this.recipe.id;
            ri.quantity = this.newIngQty;
            RecipeBankDatabase.getInstance().recipeDao().addIngredient(ri);

            this.newIngName = "";
            this.newIngQty = 1f;
        });
        ((EditText)view.getRootView().findViewById(R.id.newIngName)).setText("");
        ((EditText)view.getRootView().findViewById(R.id.newIngQty)).setText("1");
    }

    @Override
    public void onClearClick(View view) {
        ((TextView)view.getRootView().findViewById(R.id.recipeTotals)).setText(this.getRecipeTotals());
    }
}