package ro.raul_aon.meal_planner.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

@Entity
public class Recipe implements Serializable{
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public int servings;
    @ColumnInfo
    public String instructions;
    @ColumnInfo
    public boolean reheat;

    @Ignore
    public Recipe(){
    }
    public Recipe(int id, String name, int servings, String instructions, boolean reheat){
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.instructions = instructions;
        this.reheat = reheat;
    }

    @NonNull
    public String toString(){
        return (this.reheat ? "H " : "  ") + this.name;
    }
}
