package ro.raul_aon.meal_planner.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.view_models.ShopListViewModel;

public class ShopListFragment extends Fragment {

    private ShopListViewModel mViewModel;

    public static ShopListFragment newInstance() {
        return new ShopListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopListViewModel.class);
        // TODO: Use the ViewModel
    }

}