package ro.raul_aon.meal_planner.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.models.ShopListItem;

public class ShopListViewModel extends ViewModel {
    public LiveData<List<ShopListItem>> shoppingList =
            RecipeBankDatabase.getInstance().shopListDao().getAll();

    public String sliName = "";
    public int sliUnits = 0;
}