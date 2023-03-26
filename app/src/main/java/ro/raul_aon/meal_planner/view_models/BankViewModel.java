package ro.raul_aon.meal_planner.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BankViewModel extends ViewModel {
    private final MutableLiveData<String> title =
            new MutableLiveData<String>(new String("Bank"));

    public LiveData<String> getTitle() {
        return title;
    }

    public void updateTitle() {
        title.setValue("recipe bank");
    }
}