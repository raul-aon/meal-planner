package ro.raul_aon.meal_planner.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ro.raul_aon.meal_planner.view_models.BankViewModel;
import ro.raul_aon.meal_planner.R;

public class BankFragment extends Fragment {

    private BankViewModel mViewModel;

    public static BankFragment newInstance() {
        return new BankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bank, container, false);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BankViewModel.class);
        mViewModel.getTitle().observe(this, title -> {
            ((TextView)this.getView().findViewById(R.id.bank_title)).setText(title);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewModel.updateTitle();
    }
}