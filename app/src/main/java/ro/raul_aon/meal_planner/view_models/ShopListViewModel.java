package ro.raul_aon.meal_planner.view_models;

import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.handlers.AddClearHandler;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.models.ShopListItem;

public class ShopListViewModel extends ViewModel implements AddClearHandler {

    private final MutableLiveData<String> slTotal = new MutableLiveData<>("Total: ");

    private ShopListItem sli = new ShopListItem();

    public String getSliName() {
        return sli.name;
    }

    public void setSliName(String sliName) {
        sli.name = sliName;
    }

    public String getSliUnits() {
        return Float.toString(sli.units);
    }

    public void setSliUnits(String sliUnits) {
        try {
            sli.units = Float.parseFloat(sliUnits);
        } catch (Exception e){
            sli.units = 0;
        }
    }

    public LiveData<String> getSlTotal() {
        return slTotal;
    }

    public void setSlTotal(float total, boolean plus) {
        this.slTotal.setValue("Total: "+ total + (plus ? "+" : ""));
    }

    @Override
    public void onAddClick(View view) {
        ShopListItem shopListItem = sli;
        sli = new ShopListItem();
        ((EditText)view.getRootView().findViewById(R.id.sliName)).setText("");
        ((EditText)view.getRootView().findViewById(R.id.sliUnits)).setText("1");

        RecipeBankDatabase.databaseWriteExecutor.execute(() -> {
            Ingredient ing = RecipeBankDatabase.getInstance().ingredientDao().getByName(shopListItem.name);
            if(ing != null){
                shopListItem.pricePerUnit = ing.pricePerPack;
            }
            RecipeBankDatabase.getInstance().shopListDao().insert(shopListItem);
        });
    }

    @Override
    public void onClearClick(View view) {
        RecipeBankDatabase.databaseWriteExecutor.execute(() -> RecipeBankDatabase.getInstance().shopListDao().clear());
    }
}