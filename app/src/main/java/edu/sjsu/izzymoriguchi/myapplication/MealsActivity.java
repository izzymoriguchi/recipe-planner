package edu.sjsu.izzymoriguchi.myapplication;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MealsActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private Spinner[] spinners;
    private HashMap<Integer, NewDishModel> map;
    private ArrayAdapter<String> dataAdapter;
    private ArrayList<NewDishModel> meals;
    private static final int NUM_WEEKLY_MEALS = 21;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        spinners = new Spinner[NUM_WEEKLY_MEALS];
        spinners[0] = (Spinner) findViewById(R.id.mon_breakfast_spinner);
        spinners[1] = (Spinner) findViewById(R.id.mon_lunch_spinner);
        spinners[2] = (Spinner) findViewById(R.id.mon_dinner_spinner);
        spinners[3] = (Spinner) findViewById(R.id.tue_breakfast_spinner);
        spinners[4] = (Spinner) findViewById(R.id.tue_lunch_spinner);
        spinners[5] = (Spinner) findViewById(R.id.tue_dinner_spinner);
        spinners[6] = (Spinner) findViewById(R.id.wed_breakfast_spinner);
        spinners[7] = (Spinner) findViewById(R.id.wed_lunch_spinner);
        spinners[8] = (Spinner) findViewById(R.id.wed_dinner_spinner);
        spinners[9] = (Spinner) findViewById(R.id.thur_breakfast_spinner);
        spinners[10] = (Spinner) findViewById(R.id.thur_lunch_spinner);
        spinners[11] = (Spinner) findViewById(R.id.thur_dinner_spinner);
        spinners[12] = (Spinner) findViewById(R.id.fri_breakfast_spinner);
        spinners[13] = (Spinner) findViewById(R.id.fri_lunch_spinner);
        spinners[14] = (Spinner) findViewById(R.id.fri_dinner_spinner);
        spinners[15] = (Spinner) findViewById(R.id.sat_breakfast_spinner);
        spinners[16] = (Spinner) findViewById(R.id.sat_lunch_spinner);
        spinners[17] = (Spinner) findViewById(R.id.sat_dinner_spinner);
        spinners[18] = (Spinner) findViewById(R.id.sun_breakfast_spinner);
        spinners[19] = (Spinner) findViewById(R.id.sun_lunch_spinner);
        spinners[20] = (Spinner) findViewById(R.id.sun_dinner_spinner);


        items = new ArrayList<String>();
        items.add("Eating out");
        MealList lstOfMeals = null;

        try { // if previously saved, load it first
            FileInputStream ifile
                    = new FileInputStream(this.getFilesDir() + File.separator + NewDishActivity.filename);
            ObjectInputStream in = new ObjectInputStream(ifile);
            lstOfMeals = (MealList) in.readObject();
            in.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        meals = lstOfMeals.getListOfMeals();
        for (int i = 0; i < meals.size(); i++) {
            NewDishModel meal = meals.get(i);
            if (meal.getSelectionCounter() > 0) {
                String listLabel = meal.getNameOfDish();
                items.add(listLabel);
            }
        }

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach data adapter with spinner
        for (int i = 0; i < spinners.length; i++) {
            spinners[i].setAdapter(dataAdapter);
            spinners[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String item = (String) adapterView.getItemAtPosition(i);
                    if (i > 0) {
                        NewDishModel model = searchDishModel(item);
                        if (model != null) {
                            if (model.getSelectionCounter() > 0) {
                                model.setSelectionCounter(model.getSelectionCounter() - 1);
                                if (model.getSelectionCounter() == 0) {
                                    items.remove(item);
                                }
                                Toast.makeText(getApplicationContext(),
                                        "The number of " + model.getNameOfDish()
                                                + " available: " + model.getSelectionCounter(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    public NewDishModel searchDishModel (String name) {
        NewDishModel outputModel = null;
        for (NewDishModel model : meals) {
            if (model.getNameOfDish().equals(name)) {
                outputModel = model;
                break;
            }
        }
        return outputModel;
    }

}
