package edu.sjsu.izzymoriguchi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GroceriesActivity extends AppCompatActivity {
    private final String TAG = "GroceriesActivity";
    private ArrayList<GroceriesModel> groceriesData;
    private ArrayList<String> arrOfGroceriesList;
    private MyCustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);
//        final SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.grocery_container_list_view);

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

            arrOfGroceriesList = new ArrayList<>();
            groceriesData = new ArrayList<>();
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

            adapter = new MyCustomAdapter(arrOfGroceriesList, this);
            final ListView listView = findViewById(R.id.grocery_container_list_view);
            listView.setAdapter(adapter);

            listView.setOnTouchListener(new OnSwipeTouchListener(GroceriesActivity.this) {
                @Override
                public void onSwipeTop() {
                    Toast.makeText(GroceriesActivity.this, "top", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSwipeRight() {
                    Toast.makeText(GroceriesActivity.this, "right", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSwipeLeft(MotionEvent e1) {
                    // Whatever
                    final int pos = listView.pointToPosition((int)e1.getX(), (int)e1.getY());
                    View child = listView.getChildAt(pos);
                    if (child != null){
                        final ImageButton plus = (ImageButton) child.findViewById(R.id.add_btn);
                        final ImageButton minus = (ImageButton) child.findViewById(R.id.delete_btn);
                        if (plus != null) {
                            if (plus.getVisibility() == View.INVISIBLE) {
                                plus.setVisibility(View.VISIBLE);
                            } else {
                                plus.setVisibility(View.INVISIBLE);
                            }
                        }
                        if (minus != null) {
                            if (minus.getVisibility() == View.INVISIBLE) {
                                minus.setVisibility(View.VISIBLE);
                            } else {
                                minus.setVisibility(View.INVISIBLE);
                            }
                        }

                        plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                incrementCount(pos);
                                plus.setVisibility(View.INVISIBLE);
                                minus.setVisibility(View.INVISIBLE);
                            }
                        });

                        minus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                decrementCount(pos);
                                plus.setVisibility(View.INVISIBLE);
                                minus.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                    Toast.makeText(GroceriesActivity.this, "left", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSwipeBottom() {
                    Toast.makeText(GroceriesActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                }

            });


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void incrementCount(int pos) {
        GroceriesModel model = groceriesData.get(pos);
        final int i = arrOfGroceriesList.indexOf(model.toString());
        model.incrementQty();
        arrOfGroceriesList.set(i, model.toString());
        adapter.notifyDataSetChanged();
    }

    public void decrementCount(int pos) {
        GroceriesModel model = groceriesData.get(pos);
        final int i = arrOfGroceriesList.indexOf(model.toString());
        model.decrementQty();
        arrOfGroceriesList.set(i, model.toString());
        adapter.notifyDataSetChanged();
    }



}
