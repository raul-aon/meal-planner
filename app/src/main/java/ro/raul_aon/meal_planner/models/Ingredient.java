package ro.raul_aon.meal_planner.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingredient {
    @PrimaryKey
    public int id;
    @ColumnInfo
    public String name;

    public Ingredient() {
    }
    public Ingredient(int id, String name){
        this.id = id;
        this.name = name;
    }
}
