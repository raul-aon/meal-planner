package ro.raul_aon.meal_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import ro.raul_aon.meal_planner.data_access.RecipeBankDatabase;
import ro.raul_aon.meal_planner.fragments.BankFragment;
import ro.raul_aon.meal_planner.fragments.IngredientFragment;
import ro.raul_aon.meal_planner.fragments.IngredientsFragment;
import ro.raul_aon.meal_planner.fragments.RecipesFragment;
import ro.raul_aon.meal_planner.fragments.ShopListFragment;
import ro.raul_aon.meal_planner.models.Ingredient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button BankBtn, ShopListBtn, RecipesBtn, IngredientsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();

        BankBtn = (Button) findViewById(R.id.bank_btn);
        BankBtn.setOnClickListener(this);
        ShopListBtn = (Button) findViewById(R.id.shplst_btn);
        ShopListBtn.setOnClickListener(this);
        RecipesBtn = (Button) findViewById(R.id.recipes_btn);
        RecipesBtn.setOnClickListener(this);
        IngredientsBtn = (Button) findViewById(R.id.ings_btn);
        IngredientsBtn.setOnClickListener(this);

        Fragment f = BankFragment.newInstance();
        setView(f);
    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();
        switch (buttonId){
            case R.id.bank_btn: {
                Fragment f = BankFragment.newInstance();
                setView(f);
                break;
            }
            case R.id.shplst_btn: {
                Fragment f = ShopListFragment.newInstance();
                setView(f);
                break;
            }
            case R.id.recipes_btn: {
                Fragment f = RecipesFragment.newInstance();
                setView(f);
                break;
            }
            case R.id.ings_btn:{
                Fragment f = IngredientsFragment.newInstance();
                setView(f);
                break;
            }
        }
    }

    private void setView(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    private void initDatabase(){
        RecipeBankDatabase.startDbInstance(getApplicationContext());
        RecipeBankDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                try {
                } catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        });
    }

}