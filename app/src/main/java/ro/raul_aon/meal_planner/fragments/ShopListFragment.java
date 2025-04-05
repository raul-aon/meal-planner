package ro.raul_aon.meal_planner.fragments;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.models.ShopListItem;
import ro.raul_aon.meal_planner.utils.MyIngredientRecyclerViewAdapter;
import ro.raul_aon.meal_planner.utils.MyShopListRecyclerViewAdapter;
import ro.raul_aon.meal_planner.view_models.ShopListViewModel;

public class ShopListFragment extends Fragment implements ListClickHandler, View.OnClickListener {

    private ShopListViewModel mViewModel;
    private Button addSLIBtn;
    private Button clearSLIBtn;

    public static ShopListFragment newInstance() {
        return new ShopListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_shop_list, container, false);

        RecipeBankDatabase.getInstance().shopListDao().getAll().observe(
                getViewLifecycleOwner(), items ->{
                    RecyclerView list = (RecyclerView) mainView.findViewById(R.id.shopListContainer);
                    Context context = list.getContext();
                    list.setLayoutManager(new LinearLayoutManager(context));
                    list.setAdapter(new MyShopListRecyclerViewAdapter(items, this));

                    Float total = items.stream().map((v)-> v.pricePerUnit * v.units).reduce(0.f, Float::sum);
                    boolean plus = items.stream().map((v)-> v.pricePerUnit).anyMatch((v)-> v == 0);
                    TextView sliTotal = (TextView) mainView.findViewById(R.id.sliTotal);
                    sliTotal.setText("Total: " + total + (plus ? "+" : ""));
                }
        );

        RecipeBankDatabase.getInstance().ingredientDao().getAll().observe(
                getViewLifecycleOwner(), items ->{
                    AutoCompleteTextView sliName = mainView.findViewById(R.id.sliName);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(sliName.getContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            items.stream().map((v)->{return v.name;}).toList());
                    sliName.setAdapter(adapter);
                }
        );

        addSLIBtn = (Button) mainView.findViewById(R.id.addSLI);
        addSLIBtn.setOnClickListener(this);

        clearSLIBtn = (Button) mainView.findViewById(R.id.clearSLI);
        clearSLIBtn.setOnClickListener(this);

        EditText input = (EditText) mainView.findViewById(R.id.sliUnits);
        input.setText("1");

        return  mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopListViewModel.class);
    }

    @Override
    public void onListItemClick(Object item) {
        ShopListItem shopListItem = (ShopListItem) item;
        shopListItem.done = !shopListItem.done;
        RecipeBankDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                RecipeBankDatabase.getInstance().shopListDao().insert(shopListItem);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.addSLI) {
            ShopListItem shopListItem = new ShopListItem();
            EditText input = (EditText) getView().findViewById(R.id.sliName);
            shopListItem.name = input.getText().toString();
            input.setText("");
            input = (EditText) getView().findViewById(R.id.sliUnits);
            shopListItem.units = Integer.parseInt(input.getText().toString());
            input.setText("1");
            RecipeBankDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Ingredient ing = RecipeBankDatabase.getInstance().ingredientDao().getByName(shopListItem.name);
                    if(ing != null){
                        shopListItem.pricePerUnit = ing.pricePerPack;
                    }
                    RecipeBankDatabase.getInstance().shopListDao().insert(shopListItem);
                }
            });
        } else if(view.getId() == R.id.clearSLI){
            RecipeBankDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    RecipeBankDatabase.getInstance().shopListDao().clear();
                }
            });
        }
    }
}