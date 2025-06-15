package ro.raul_aon.meal_planner.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.models.Recipe;
import ro.raul_aon.meal_planner.utils.MyRecipeRecyclerViewAdapter;

public class RecipesFragment extends Fragment implements View.OnClickListener, ListClickHandler {

    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        Button addButton = view.findViewById(R.id.addRecipeButton);
        addButton.setOnClickListener(this);

        RecipeBankDatabase.getInstance().recipeDao().getAll().observe(
                getViewLifecycleOwner(), recipes-> {
                    RecyclerView rv = view.findViewById(R.id.recipesListContainer);
                    Context context = rv.getContext();
                    rv.setLayoutManager(new LinearLayoutManager(context));
                    rv.setAdapter(new MyRecipeRecyclerViewAdapter(recipes, this));
                }
        );

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Bundle data = new Bundle();
        if(v.getId() == R.id.addRecipeButton) {
            data.putBoolean("isNew", true);

            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, RecipeEditFragment.class,data);
            ft.commit();
        }
    }

    @Override
    public void onListItemClick(Object item) {
        Recipe recipe = (Recipe) item;
        Bundle data = new Bundle();
        data.putBoolean("isNew", false);
        data.putSerializable("recipe", recipe);

        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, RecipeEditFragment.class, data);
        ft.commit();
    }
}