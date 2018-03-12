package edu.sjsu.izzymoriguchi.myapplication;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GroceriesActivity extends AppCompatActivity {
    private final String TAG = "GroceriesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);
        final SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.grocery_container_list_view);
//        listView.add/
        FileInputStream ifile = null;
        ObjectInputStream in = null;
        MealList lstOfMeals = null;
        try { // if previously saved, load it first
            ifile = new FileInputStream(getFilesDir() + File.separator + NewDishActivity.filename);
            in = new ObjectInputStream(ifile);
            lstOfMeals = (MealList) in.readObject();
            in.close();

            ArrayList<NewDishModel> meals = lstOfMeals.getListOfMeals();
            HashMap<String, Integer> map = new HashMap<>();
            HashSet<String> set = new HashSet<>();
            final ArrayList<String> arrOfGroceriesList = new ArrayList<>();
            final ArrayList<GroceriesModel> groceriesData = new ArrayList<>();
            for (int i = 0; i < meals.size(); i++) {
                NewDishModel currModel = meals.get(i);
                if (currModel.getSelectionCounter() > 0) {
                    Log.d("currModelCounter: ", "" + currModel.getSelectionCounter());
                    Log.d("currModelNameDish: ", "" + currModel.getNameOfDish());
                    Log.d("currModelListOfItem: ", "" + currModel.getListOfItemName().length);
                    for (int j = 0; j < currModel.getListOfItemName().length; j++) {
                        String nameOfItem = currModel.getListOfItemName()[j];
                        if (nameOfItem != null) {

                            if (!set.contains(nameOfItem)) {
                                int qty = Integer.parseInt(currModel.getListOfQty()[j]);
                                String unit = currModel.getListOfIUnit()[j];
                                set.add(nameOfItem);
                                GroceriesModel data = new GroceriesModel(nameOfItem, qty, unit);
                                groceriesData.add(data);
                                arrOfGroceriesList.add(data.toString());
                            }
                        }
                    }
                }
            }


            final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrOfGroceriesList);
            listView.setAdapter(adapter);

            SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem plusItem = new SwipeMenuItem(getApplicationContext());
                    plusItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                    plusItem.setWidth(170);
                    plusItem.setIcon(R.drawable.ic_plus);
                    menu.addMenuItem(plusItem);

                    // create "delete" item
                    SwipeMenuItem minusItem = new SwipeMenuItem(getApplicationContext());
                    minusItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                    minusItem.setWidth(170);
                    minusItem.setIcon(R.drawable.ic_minus);
                    menu.addMenuItem(minusItem);
                }
            };
            listView.setMenuCreator(creator);

            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    Log.d(TAG, "position index: " + position);
                    GroceriesModel model = groceriesData.get(position);
                    int i = arrOfGroceriesList.indexOf(model.toString());
                    TextView item = (TextView)listView.getItemAtPosition(position);

                    item.setPaintFlags(item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    switch (index) {
                        case 0:
                            // increment
                            model.incrementQty();
                            arrOfGroceriesList.set(i, model.toString());
                            break;
                        case 1:
                            // decrement
                            model.decrementQty();
                            arrOfGroceriesList.set(i, model.toString());
                            break;
                    }
                    adapter.notifyDataSetChanged();
                    // false : close the menu; true : not close the menu
                    return false;
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
