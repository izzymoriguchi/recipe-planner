package edu.sjsu.izzymoriguchi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class GroceriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);



        FileInputStream ifile = null;
        ObjectInputStream in = null;
        MealList lstOfMeals = null;
        try { // if previously saved, load it first
            ifile = new FileInputStream(getFilesDir() + File.separator + NewDishActivity.filename);
            in = new ObjectInputStream(ifile);
            lstOfMeals = (MealList) in.readObject();
            in.close();

//            final MealList mealList = (MealList) bundle.getSerializable(RecipesActivity.MEAL_DATA_KEY);
            ArrayList<NewDishModel> meals = lstOfMeals.getListOfMeals();
//            String[] arr = new String[meals.size()];
            HashMap<String, Integer> map = new HashMap<>();
            ArrayList<String> arrOfGroceriesList = new ArrayList<>();
            for (int i = 0; i < meals.size(); i++) {
                NewDishModel currModel = meals.get(i);
                if (currModel.getSelectionCounter() > 0) {
                    Log.d("currModelCounter: ", "" + currModel.getSelectionCounter());
                    Log.d("currModelNameDish: ", "" + currModel.getNameOfDish());
                    Log.d("currModelListOfItem: ", "" + currModel.getListOfItemName().length);
                    for (int j = 0; j < currModel.getListOfItemName().length; j++) {
                        String nameOfItem = currModel.getListOfItemName()[j];
                        if (nameOfItem != null) {
                            if (map.containsKey(nameOfItem)) {
                                map.put(nameOfItem, map.get(nameOfItem) + 1);
                            }
                            map.put(nameOfItem, 1);
                        }
                    }
                }
            }
            arrOfGroceriesList.addAll(map.keySet());
            for (int i = 0; i < arrOfGroceriesList.size(); i++) {
                for (int j = 0; j < meals.size(); j++) {
//                    if (arrOfGroceriesList.get(i).equals(meals.get(i).getNameOfDish()))
                }
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview_portrait, arrOfGroceriesList);
            ListView myListView = (ListView) findViewById(R.id.grocery_container_list_view);
            myListView.setAdapter(adapter);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
