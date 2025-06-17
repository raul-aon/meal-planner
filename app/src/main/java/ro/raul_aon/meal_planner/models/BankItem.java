package ro.raul_aon.meal_planner.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class BankItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public int mealsLeft;

    @Ignore
    public BankItem(){
    }

    public BankItem(int id, String name, int mealsLeft){
        this.id = id;
        this.name = name;
        this.mealsLeft = mealsLeft;
    }

    @NonNull
    @Override
    public String toString() {
        return name + ":" + mealsLeft + " left";
    }
}
