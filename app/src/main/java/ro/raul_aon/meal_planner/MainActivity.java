package ro.raul_aon.meal_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;

import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.fragments.BankFragment;
import ro.raul_aon.meal_planner.fragments.IngredientsFragment;
import ro.raul_aon.meal_planner.fragments.RecipesFragment;
import ro.raul_aon.meal_planner.fragments.ShopListFragment;
import ro.raul_aon.meal_planner.models.Ingredient;
import ro.raul_aon.meal_planner.models.Recipe;
import ro.raul_aon.meal_planner.models.RecipeIngredient;
import ro.raul_aon.meal_planner.models.ShopListItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();

        Button bankBtn = findViewById(R.id.bank_btn);
        bankBtn.setOnClickListener(this);
        Button shopListBtn = findViewById(R.id.shplst_btn);
        shopListBtn.setOnClickListener(this);
        Button recipesBtn = findViewById(R.id.recipes_btn);
        recipesBtn.setOnClickListener(this);
        Button ingredientsBtn = findViewById(R.id.ings_btn);
        ingredientsBtn.setOnClickListener(this);

        Fragment f = BankFragment.newInstance();
        setView(f);
    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();
        Fragment f = getFragmentFromId(buttonId);
        setView(f);
    }

    private void setView(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @SuppressLint("NonConstantResourceId")
    private Fragment getFragmentFromId(int id){
        switch (id){
            case R.id.bank_btn: {
                return BankFragment.newInstance();
            }
            case R.id.shplst_btn: {
                return ShopListFragment.newInstance();
            }
            case R.id.recipes_btn: {
                return RecipesFragment.newInstance();
            }
            case R.id.ings_btn:{
                return IngredientsFragment.newInstance();
            }
        }
        return BankFragment.newInstance();
    }

    private void initDatabase(){
        RecipeBankDatabase.startDbInstance(getApplicationContext());
        RecipeBankDatabase.databaseWriteExecutor.execute(() -> {
            try {
                PopulateDatabase();
            } catch (Exception e){
                //System.out.println(e);
            }
        });
    }

    private void PopulateDatabase()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        Ingredient i1 = new Ingredient(1, "Fish fingers", 15, 20,
                10, 10, 10, 19.90f, new Date());
        Ingredient i2 = new Ingredient(2, "Frozen veggies", 450, 25,
                10, 50, 10, 8.45f, new Date());
        RecipeBankDatabase.getInstance().ingredientDao().insert(i1);
        RecipeBankDatabase.getInstance().ingredientDao().insert(i2);

        RecipeBankDatabase.getInstance().shopListDao().insert(new ShopListItem(2, "Pizza", 1, false, 15));

        Recipe r = new Recipe(1,"Fish and veg", 1, "Oven 15 minutes at 200C", false);
        RecipeBankDatabase.getInstance().recipeDao().insert(r);

        RecipeIngredient ri = new RecipeIngredient(1, r.id, i1.id, 5f);
        RecipeBankDatabase.getInstance().recipeDao().addIngredient(ri);
        RecipeIngredient ri2 = new RecipeIngredient(2, r.id, i2.id, 300f);
        RecipeBankDatabase.getInstance().recipeDao().addIngredient(ri2);
    }

}