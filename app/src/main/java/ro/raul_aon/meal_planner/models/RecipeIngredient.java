package ro.raul_aon.meal_planner.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.io.Serializable;

@Entity
public class RecipeIngredient implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int recipeId;
    public int ingredientId;
    @ColumnInfo
    public float quantity;

    @Ignore
    public RecipeIngredient(){
    }

    public RecipeIngredient(int id, int recipeId, int ingredientId,  float quantity){
        this.id = id;
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }
}
