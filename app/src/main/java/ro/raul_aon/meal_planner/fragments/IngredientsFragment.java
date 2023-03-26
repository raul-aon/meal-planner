package ro.raul_aon.meal_planner.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.view_models.IngredientsViewModel;
import ro.raul_aon.meal_planner.R;

public class IngredientsFragment extends Fragment {

    private IngredientsViewModel mViewModel;

    public static IngredientsFragment newInstance() {
        return new IngredientsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(IngredientsViewModel.class);
        mViewModel.getIngredients().observe(this, ings -> {
            for ( Ingredient ing: ings) {
                TextView item = new TextView(getContext());
                item.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                item.setText(ing.name);
                ((LinearLayout)this.getView().findViewById(R.id.ingredients_list))
                        .addView(item);
            }
        });
    }

}