package ro.raul_aon.meal_planner.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
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

/**
 * A fragment representing a list of Items.
 */
public class IngredientFragment extends Fragment implements ListClickHandler {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static IngredientFragment newInstance(int columnCount) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        RecipeBankDatabase.getInstance().ingredientDao().getAll().observe(
                getViewLifecycleOwner(), ings -> {
                    // Set the adapter
                    if (view instanceof RecyclerView) {
                        Context context = view.getContext();
                        RecyclerView recyclerView = (RecyclerView) view;
                        if (mColumnCount <= 1) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                        }
                        recyclerView.setAdapter(new MyIngredientRecyclerViewAdapter(ings, this));
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

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, IngredientEditFragment.class, data);
        ft.commit();
    }
}