package ro.raul_aon.meal_planner.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ShopListItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public float units = 1;
    @ColumnInfo
    public boolean done;
    @ColumnInfo
    public float pricePerUnit;

    @Ignore
    public ShopListItem(){
    }

    public ShopListItem(int id, String name, float units, boolean done, float pricePerUnit){
        this.id = id;
        this.name = name;
        this.units = units;
        this.done = done;
        this.pricePerUnit = pricePerUnit;
    }

    @NonNull
    public String toString() {
        return (this.done ? " ✓ " : " - ") + this.units + " x " + this.name + " @ " + this.pricePerUnit;
    }

}
