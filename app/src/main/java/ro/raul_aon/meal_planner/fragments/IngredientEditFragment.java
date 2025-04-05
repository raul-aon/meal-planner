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

import ro.raul_aon.meal_planner.databinding.FragmentIngredientBinding;
import ro.raul_aon.meal_planner.databinding.FragmentIngredientEditBinding;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.utils.MyIngredientRecyclerViewAdapter;
import ro.raul_aon.meal_planner.view_models.IngredientEditViewModel;
import ro.raul_aon.meal_planner.R;

public class IngredientEditFragment extends Fragment {

    private final IngredientEditViewModel mViewModel = new IngredientEditViewModel();

    public static IngredientEditFragment newInstance() {
        return new IngredientEditFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_edit, container, false);

        Bundle data = this.getArguments();
        if(data != null){
            mViewModel.isNew = data.getBoolean("isNew");
            if(mViewModel.isNew){
                mViewModel.ingredient = new Ingredient();
            } else {
                mViewModel.ingredient = data.getSerializable("ingredient", Ingredient.class);
            }
        }

        mViewModel.getCloseEvent().observe(
                getViewLifecycleOwner(), close -> {
                    if (close) {
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, IngredientsFragment.newInstance());
                        ft.commit();
                    }
        });

        FragmentIngredientEditBinding binding = FragmentIngredientEditBinding.bind(view);
        binding.setMViewModel(mViewModel);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}