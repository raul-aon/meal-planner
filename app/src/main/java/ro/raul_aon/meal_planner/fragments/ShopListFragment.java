package ro.raul_aon.meal_planner.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.databinding.FragmentShopListBinding;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.ShopListItem;
import ro.raul_aon.meal_planner.utils.MyShopListRecyclerViewAdapter;
import ro.raul_aon.meal_planner.view_models.ShopListViewModel;

public class ShopListFragment extends Fragment implements ListClickHandler {

    private ShopListViewModel mViewModel;

    public static ShopListFragment newInstance() {
        return new ShopListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_shop_list, container, false);

        RecipeBankDatabase.getInstance().shopListDao().getAll().observe(
                getViewLifecycleOwner(), items ->{
                    RecyclerView list = mainView.findViewById(R.id.shopListContainer);
                    Context context = list.getContext();
                    list.setLayoutManager(new LinearLayoutManager(context));
                    list.setAdapter(new MyShopListRecyclerViewAdapter(items, this));

                    Float total = items.stream().map((v)-> v.pricePerUnit * v.units).reduce(0.f, Float::sum);
                    boolean plus = items.stream().map((v)-> v.pricePerUnit).anyMatch((v)-> v == 0);
                    mViewModel.setSlTotal(total, plus);
                }
        );

        RecipeBankDatabase.getInstance().ingredientDao().getAll().observe(
                getViewLifecycleOwner(), items ->{
                    AutoCompleteTextView sliName = mainView.findViewById(R.id.sliName);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(sliName.getContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            items.stream().map((v)-> v.name).toList());
                    sliName.setAdapter(adapter);
                }
        );

        FragmentShopListBinding binding = FragmentShopListBinding.bind(mainView);
        binding.setMViewModel(mViewModel);

        return mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopListViewModel.class);
        mViewModel.getSlTotal().observe(this, total ->{
            TextView tv =  requireActivity().findViewById(R.id.sliTotal);
            tv.setText(total);
        });
    }

    @Override
    public void onListItemClick(Object item) {
        ShopListItem shopListItem = (ShopListItem) item;
        shopListItem.done = !shopListItem.done;
        RecipeBankDatabase.databaseWriteExecutor.execute(() -> RecipeBankDatabase.getInstance().shopListDao().insert(shopListItem));
    }
}