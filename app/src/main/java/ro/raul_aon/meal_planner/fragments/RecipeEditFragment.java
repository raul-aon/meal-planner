package ro.raul_aon.meal_planner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.databinding.FragmentRecipeEditBinding;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.models.Recipe;
import ro.raul_aon.meal_planner.models.RecipeIngredient;
import ro.raul_aon.meal_planner.utils.MyRecipeIngredientsRecyclerViewAdapter;
import ro.raul_aon.meal_planner.view_models.RecipeEditViewModel;

public class RecipeEditFragment extends Fragment implements ListClickHandler {
    private final RecipeEditViewModel mViewModel = new RecipeEditViewModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_edit, container, false);

        Bundle data = this.getArguments();
        if(data != null){
            mViewModel.isNew = data.getBoolean("isNew");
            if(mViewModel.isNew){
                mViewModel.recipe = new Recipe();
            } else {
                mViewModel.recipe = data.getSerializable("recipe", Recipe.class);
            }
        }

        mViewModel.getCloseEvent().observe(
                getViewLifecycleOwner(), close -> {
                    if (close) {
                        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, RecipesFragment.newInstance());
                        ft.commit();
                    }
                });

        FragmentRecipeEditBinding binding = FragmentRecipeEditBinding.bind(view);
        binding.setMViewModel(mViewModel);

        RecipeBankDatabase.getInstance().recipeDao().getRecipeIngredients(mViewModel.recipe.id).observe(
                getViewLifecycleOwner(), ingredients-> {
                    RecyclerView rv = view.findViewById(R.id.recipeIngredientsContainer);
                    Context context = rv.getContext();
                    rv.setLayoutManager(new LinearLayoutManager(context));
                    rv.setAdapter(new MyRecipeIngredientsRecyclerViewAdapter(ingredients, this));

                    RecipeBankDatabase.databaseWriteExecutor.execute(() -> {
                        float total = ingredients.stream().map(ri -> {
                            Ingredient ing = RecipeBankDatabase.getInstance().ingredientDao().getById(ri.ingredientId);
                            return ri.quantity / ing.unitsPerPack * ing.pricePerPack;
                        }).reduce(0f, Float::sum);
                        mViewModel.setRecipeTotals("Total: " + total);
                    });
                }
        );

        RecipeBankDatabase.getInstance().ingredientDao().getAll().observe(
                getViewLifecycleOwner(), items ->{
                    AutoCompleteTextView sliName = view.findViewById(R.id.newIngName);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(sliName.getContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            items.stream().map((v)-> v.name).toList());
                    sliName.setAdapter(adapter);
                }
        );

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(Object item) {
        RecipeIngredient ri = (RecipeIngredient) item;
        RecipeBankDatabase.databaseWriteExecutor.execute(() -> RecipeBankDatabase.getInstance().recipeDao().deleteIngredient(ri));
    }
}
