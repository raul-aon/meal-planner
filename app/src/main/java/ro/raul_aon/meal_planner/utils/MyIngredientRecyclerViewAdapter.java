package ro.raul_aon.meal_planner.utils;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ro.raul_aon.meal_planner.R;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.databinding.FragmentIngredientBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Ingredient}.
 */
public class MyIngredientRecyclerViewAdapter extends RecyclerView.Adapter<MyIngredientRecyclerViewAdapter.ViewHolder> {

    private final List<Ingredient> mValues;
    private ListClickHandler handler;

    public MyIngredientRecyclerViewAdapter(List<Ingredient> items, ListClickHandler handler) {
        mValues = items;
        this.handler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentIngredientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).toString());


        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -3);
        if(holder.mItem.lastPriceUpdate.compareTo(cal.getTime()) < 0) {
            holder.mContentView.setBackgroundColor(holder.mContentView.getResources().getColor(R.color.yellow));
        }
        cal.add(Calendar.MONTH, -9);
        if(holder.mItem.lastPriceUpdate.compareTo(cal.getTime()) < 0) {
            holder.mContentView.setBackgroundColor(holder.mContentView.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public Ingredient mItem;

        public ViewHolder(FragmentIngredientBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handler.onIngredientClick(mItem);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}