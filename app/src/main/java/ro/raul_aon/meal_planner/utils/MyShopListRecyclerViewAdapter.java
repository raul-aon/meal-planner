package ro.raul_aon.meal_planner.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.raul_aon.meal_planner.databinding.FragmentIngredientBinding;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.ShopListItem;

public class MyShopListRecyclerViewAdapter extends RecyclerView.Adapter<MyShopListRecyclerViewAdapter.ViewHolder> {

    private final List<ShopListItem> mValues;
    private final ListClickHandler handler;

    public MyShopListRecyclerViewAdapter(List<ShopListItem> items, ListClickHandler handler) {
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
        holder.mContentView.setText(mValues.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public ShopListItem mItem;

        public ViewHolder(FragmentIngredientBinding binding) {
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