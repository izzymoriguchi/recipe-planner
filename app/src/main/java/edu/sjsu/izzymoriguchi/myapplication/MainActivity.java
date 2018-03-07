package edu.sjsu.izzymoriguchi.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToMealsActivity(View view) {
        Intent intent = new Intent(this, MealsActivity.class);
        startActivity(intent);
    }

    public void goToNewDishActivity(View view) {
        Intent intent = new Intent(this, NewDishActivity.class);
        startActivity(intent);
    }

    public void goToRecipesActivity(View view) {
        Intent intent = new Intent(this, RecipesActivity.class);
        startActivity(intent);
    }

    public void goToGroceriesActivity(View view) {
        Intent intent = new Intent(this, GroceriesActivity.class);
        startActivity(intent);
    }


}
