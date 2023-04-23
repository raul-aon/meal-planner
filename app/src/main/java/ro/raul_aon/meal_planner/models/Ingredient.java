package ro.raul_aon.meal_planner.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import ro.raul_aon.meal_planner.utils.DateConverter;


@Entity
@TypeConverters(DateConverter.class)
public class Ingredient implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public float unitsPerPack;
    @ColumnInfo
    public float proteinPerUnit;
    @ColumnInfo
    public float carbsPerUnit;
    @ColumnInfo
    public float fatsPerUnit;
    @ColumnInfo
    public float pricePerPack;
    @ColumnInfo
    public float caloriesPerUnit;
    @ColumnInfo
    public Date lastPriceUpdate;

    public Ingredient() {
    }

    public Ingredient(int id, String name, float unitsPerPack, float caloriesPerUnit,
                      float proteinPerUnit, float carbsPerUnit, float fatsPerUnit,
                      float pricePerPack, Date lastPriceUpdate) {
        this.id = id;
        this.name = name;
        this.unitsPerPack = unitsPerPack;
        this.proteinPerUnit = proteinPerUnit;
        this.carbsPerUnit = carbsPerUnit;
        this.fatsPerUnit = fatsPerUnit;
        this.pricePerPack = pricePerPack;
        this.caloriesPerUnit = caloriesPerUnit;
        this.lastPriceUpdate = lastPriceUpdate;
    }

    public String toString() {
        return "* " + this.name + " at " + this.pricePerPack + " for " + this.unitsPerPack;
    }
}
