package ro.raul_aon.meal_planner.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.raul_aon.meal_planner.databinding.FragmentIngredientBinding;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.Recipe;

public class MyRecipeRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeRecyclerViewAdapter.ViewHolder> {
    private final List<Recipe> mValues;
    private final ListClickHandler handler;

    public MyRecipeRecyclerViewAdapter(List<Recipe> mValues, ListClickHandler handler){
        this.mValues = mValues;
        this.handler = handler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentIngredientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = this.mValues.get(position);
        holder.mContentView.setText(this.mValues.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView mContentView;
        public Recipe mItem;
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
