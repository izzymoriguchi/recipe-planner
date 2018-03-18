package edu.sjsu.izzymoriguchi.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class GroceriesActivity extends AppCompatActivity {
    private final String TAG = "GroceriesActivity";
    private ArrayList<GroceriesModel> groceriesData;
    private ArrayList<String> arrOfGroceriesList;
    private MyCustomAdapter adapter;
    private ListView listView;
    @SuppressLint("ClickableViewAccessibility")
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

            ArrayList<NewDishModel> meals = lstOfMeals.getListOfMeals();
            HashMap<String, GroceriesModel> map = new HashMap<>();

            arrOfGroceriesList = new ArrayList<>();
            groceriesData = new ArrayList<>();
            GroceriesModel data = null;
            for (int i = 0; i < meals.size(); i++) {
                NewDishModel currModel = meals.get(i);
                if (currModel.getSelectionCounter() > 0) {
                    for (int j = 0; j < currModel.getListOfItemName().length; j++) {
                        String nameOfItem = currModel.getListOfItemName()[j];
                        if (nameOfItem != null) {
                            double qty = Double.parseDouble(currModel.getListOfQty()[j]);
                            if (!map.containsKey(nameOfItem)) {
                                String unit = currModel.getListOfIUnit()[j];
                                data = new GroceriesModel(nameOfItem, qty, unit);
                                groceriesData.add(data);
                                arrOfGroceriesList.add(data.toString());
                                map.put(nameOfItem, data);
                            } else {
                                GroceriesModel existedData = map.get(nameOfItem);
                                String oldStr = existedData.toString();
                                existedData.setQty(existedData.getQty() + qty);
                                arrOfGroceriesList.set(arrOfGroceriesList.indexOf(oldStr), existedData.toString());
                            }
                        }
                    }
                }
            }

            adapter = new MyCustomAdapter(arrOfGroceriesList, this);
            listView = findViewById(R.id.grocery_container_list_view);
            listView.setAdapter(adapter);

            listView.setOnTouchListener(new OnSwipeTouchListener(GroceriesActivity.this) {
                @Override
                public void onSwipeRight(MotionEvent e1) {
                    final int pos = listView.pointToPosition((int)e1.getX(), (int)e1.getY());
                    View child = listView.getChildAt(pos);
                    if (child != null) {
                        removeElem(pos);
                    }
                }

                @Override
                public void onSwipeLeft(MotionEvent e1) {
                    final int pos = listView.pointToPosition((int)e1.getX(), (int)e1.getY());
                    View child = listView.getChildAt(pos);
                    if (child != null) {
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
                }
            });


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeElem(int pos) {
        GroceriesModel model = groceriesData.get(pos);
        int i = arrOfGroceriesList.indexOf(model.toString());
        arrOfGroceriesList.remove(i);
        groceriesData.remove(pos);
        adapter.notifyDataSetChanged();
    }

    public void incrementCount(int pos) {
        GroceriesModel model = groceriesData.get(pos);
        int i = arrOfGroceriesList.indexOf(model.toString());
        model.incrementQty();
        arrOfGroceriesList.set(i, model.toString());
        adapter.notifyDataSetChanged();
    }

    public void decrementCount(int pos) {
        GroceriesModel model = groceriesData.get(pos);
        if (model.getQty() > 0) {
            int i = arrOfGroceriesList.indexOf(model.toString());
            model.decrementQty();
            if (model.getQty() == 0) {
                View child = listView.getChildAt(pos);
                TextView item = (TextView) child.findViewById(R.id.list_item_string);
                item.setPaintFlags(item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            arrOfGroceriesList.set(i, model.toString());
            adapter.notifyDataSetChanged();
        }
    }
}
