package ro.raul_aon.meal_planner.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.databinding.FragmentIngredientBinding;
import ro.raul_aon.meal_planner.handlers.ListClickHandler;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.models.RecipeIngredient;

public class MyRecipeIngredientsRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeIngredientsRecyclerViewAdapter.ViewHolder> {
    private final List<RecipeIngredient> mValues;
    private final ListClickHandler handler;

    public MyRecipeIngredientsRecyclerViewAdapter(List<RecipeIngredient> mValues, ListClickHandler handler){
        this.mValues = mValues;
        this.handler = handler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentIngredientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeIngredientsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = this.mValues.get(position);

        RecipeBankDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Ingredient ing = RecipeBankDatabase.getInstance().ingredientDao().getById(holder.mItem.ingredientId);
                String show = ing.name + " : " + holder.mItem.quantity;
                holder.mContentView.setText(show);
            } catch (Exception e){
                //System.out.println(e);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView mContentView;
        public RecipeIngredient mItem;
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
