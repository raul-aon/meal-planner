package ro.raul_aon.meal_planner.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.databinding.FragmentIngredientBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyIngredientRecyclerViewAdapter extends RecyclerView.Adapter<MyIngredientRecyclerViewAdapter.ViewHolder> {

    private final List<Ingredient> mValues;
    private final ListClickHandler handler;

    public MyIngredientRecyclerViewAdapter(List<Ingredient> items, ListClickHandler handler) {
        this.mValues = items;
        this.handler = handler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentIngredientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(holder.mItem.toString());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -3);

        Resources.Theme theme = holder.mContentView.getContext().getTheme();
        if(holder.mItem.lastPriceUpdate.compareTo(cal.getTime()) < 0) {
            holder.mContentView.setBackgroundColor(holder.mContentView.getResources().getColor(R.color.yellow, theme));
        }
        cal.add(Calendar.MONTH, -9);
        if(holder.mItem.lastPriceUpdate.compareTo(cal.getTime()) < 0) {
            holder.mContentView.setBackgroundColor(holder.mContentView.getResources().getColor(R.color.red, theme));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public Ingredient mItem;

        public ViewHolder(@NonNull FragmentIngredientBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;

            binding.getRoot().setOnClickListener(view -> handler.onListItemClick(mItem));
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}