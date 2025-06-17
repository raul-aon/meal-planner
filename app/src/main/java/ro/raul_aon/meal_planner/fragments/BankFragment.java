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

import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.databinding.FragmentBankBinding;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.BankItem;
import ro.raul_aon.meal_planner.utils.MyBankRecyclerViewAdapter;
import ro.raul_aon.meal_planner.view_models.BankViewModel;
import ro.raul_aon.meal_planner.R;

public class BankFragment extends Fragment implements ListClickHandler {

    private BankViewModel mViewModel;

    public static BankFragment newInstance() {
        return new BankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bank, container, false);

        RecipeBankDatabase.getInstance().bankDao().getAll().observe(
                getViewLifecycleOwner(), items ->{
                    RecyclerView list = root.findViewById(R.id.mealBankList);
                    Context context = list.getContext();
                    list.setLayoutManager(new LinearLayoutManager(context));
                    list.setAdapter(new MyBankRecyclerViewAdapter(items, this));
                }
        );

        RecipeBankDatabase.getInstance().recipeDao().getAll().observe(
                getViewLifecycleOwner(), items ->{
                    AutoCompleteTextView sliName = root.findViewById(R.id.bankMealName);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(sliName.getContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            items.stream().map((v)-> v.name).toList());
                    sliName.setAdapter(adapter);
                }
        );

        FragmentBankBinding binding = FragmentBankBinding.bind(root);
        binding.setMViewModel(mViewModel);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BankViewModel.class);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onListItemClick(Object item) {
        BankItem bankItem = (BankItem) item;
        bankItem.mealsLeft--;
        RecipeBankDatabase.databaseWriteExecutor.execute(() -> {
            RecipeBankDatabase.getInstance().bankDao().insert(bankItem);
            RecipeBankDatabase.getInstance().bankDao().clear();
        });
    }
}