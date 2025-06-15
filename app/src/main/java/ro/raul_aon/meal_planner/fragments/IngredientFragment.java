package ro.raul_aon.meal_planner.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.utils.MyIngredientRecyclerViewAdapter;
import ro.raul_aon.meal_planner.R;

public class IngredientFragment extends Fragment implements ListClickHandler {

    public IngredientFragment() {
    }
    public static IngredientFragment newInstance() {
        return new IngredientFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        RecipeBankDatabase.getInstance().ingredientDao().getAll().observe(
                getViewLifecycleOwner(), items -> {
                    // Set the adapter
                    if (view instanceof RecyclerView) {
                        Context context = view.getContext();
                        RecyclerView recyclerView = (RecyclerView) view;
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(new MyIngredientRecyclerViewAdapter(items, this));
                    }
                }
        );
        return view;
    }

    @Override
    public void onListItemClick(Object item) {
        Ingredient ingredient = (Ingredient) item;
        Bundle data = new Bundle();
        data.putBoolean("isNew", false);
        data.putSerializable("ingredient", ingredient);

        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, IngredientEditFragment.class, data);
        ft.commit();
    }
}