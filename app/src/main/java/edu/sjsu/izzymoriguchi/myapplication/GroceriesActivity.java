package edu.sjsu.izzymoriguchi.myapplication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class GroceriesActivity extends AppCompatActivity {
    private final String TAG = "GroceriesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);
        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.grocery_container_list_view);



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

            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrOfGroceriesList);
            listView.setAdapter(adapter);

            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                    // set item background
                    openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                            0xCE)));
                    // set item width
                    openItem.setWidth(170);
                    // set item title
                    openItem.setTitle("Open");
                    // set item title fontsize
                    openItem.setTitleSize(18);
                    // set item title font color
                    openItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(openItem);

                    // create "delete" item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    // set item width
                    deleteItem.setWidth(170);
                    // set a icon
                    deleteItem.setIcon(R.drawable.ic_plus);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };
            listView.setMenuCreator(creator);

            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            // open
                            Log.d(TAG, "onMenuItemClick, and index: " + index);
                            break;
                        case 1:
                            // delete
                            Log.d(TAG, "onMenuItemClick, and index: " + index);
                            break;
                    }
                    // false : close the menu; true : not close the menu
                    return false;
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
