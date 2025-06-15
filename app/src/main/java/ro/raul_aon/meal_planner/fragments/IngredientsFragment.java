package ro.raul_aon.meal_planner.fragments;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ro.raul_aon.meal_planner.R;

public class IngredientsFragment extends Fragment implements View.OnClickListener {

    public static IngredientsFragment newInstance() {
        return new IngredientsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        Button AddButton = view.findViewById(R.id.newIngredientButton);
        AddButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ingredients_list, IngredientFragment.newInstance());
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        Bundle data = new Bundle();
        if(view.getId() == R.id.newIngredientButton) {
            data.putBoolean("isNew", true);

            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, IngredientEditFragment.class,data);
            ft.commit();
        }
    }
}