package ro.raul_aon.meal_planner.view_models;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.Date;

import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.handlers.EditButtonsClickHandler;

public class IngredientEditViewModel extends ViewModel implements EditButtonsClickHandler {

    public boolean isNew;
    public Ingredient ingredient;

    public String getName(){
        return ingredient.name;
    }
    public void setName(String value){
        ingredient.name = value;
    }
    public String getUnitsPerPack(){
        return Float.toString(ingredient.unitsPerPack);
    }
    public void setUnitsPerPack(String value){
        try {
            ingredient.unitsPerPack = Float.parseFloat(value);
        }catch(Exception e){
            ingredient.unitsPerPack = 0;
        }
    }
    public String getCaloriesPerUnit(){
        return Float.toString(ingredient.caloriesPerUnit);
    }
    public void setCaloriesPerUnit(String value){
        try {
            ingredient.caloriesPerUnit = Float.parseFloat(value);
        } catch (Exception e){
            ingredient.caloriesPerUnit = 0;
        }
    }
    public String getProteinPerUnit(){
        return Float.toString(ingredient.proteinPerUnit);
    }
    public void setProteinPerUnit(String value){
        try {
            ingredient.proteinPerUnit = Float.parseFloat(value);
        } catch (Exception e){
            ingredient.proteinPerUnit = 0;
        }
    }
    public String getCarbsPerUnit(){
        return Float.toString(ingredient.carbsPerUnit);
    }
    public void setCarbsPerUnit(String value){
        try{
        ingredient.carbsPerUnit = Float.parseFloat(value);
        } catch (Exception e){
            ingredient.carbsPerUnit = 0;
        }
    }
    public String getFatsPerUnit(){
        return Float.toString(ingredient.fatsPerUnit);
    }
    public void setFatsPerUnit(String value){
        try {
            ingredient.fatsPerUnit = Float.parseFloat(value);
        } catch (Exception e){
            ingredient.fatsPerUnit = 0;
        }
    }
    public String getPricePerPack(){
        return Float.toString(ingredient.pricePerPack);
    }
    public void setPricePerPack(String value){
        try {
            ingredient.pricePerPack = Float.parseFloat(value);
        } catch (Exception e){
            ingredient.pricePerPack = 0;
        }
    }


    public MutableLiveData<Boolean> close = new MutableLiveData<>();
    public LiveData<Boolean> getCloseEvent() {
        return this.close;
    }

    @Override
    public void onSaveClick(View view) {
        ingredient.lastPriceUpdate = new Date();
        RecipeBankDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                RecipeBankDatabase.getInstance().ingredientDao().insert(ingredient);
            }
        });
        onCancelClick(view);
    }

    @Override
    public void onCancelClick(View view) {
        this.close.setValue(true);
    }

    @Override
    public void onDeleteClick(View view) {
        RecipeBankDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                RecipeBankDatabase.getInstance().ingredientDao().delete(ingredient);
            }
        });
        onCancelClick(view);
    }
}