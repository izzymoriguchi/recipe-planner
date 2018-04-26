package edu.sjsu.izzymoriguchi.myapplication;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PortraitFragment extends Fragment {

    private Bundle bundle;

    public PortraitFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_portrait, container, false);;
        FragmentManager fragmentManager = getFragmentManager();

        RetainedFragment retainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(RecipesActivity.TAG_RETAINED_FRAGMENT);
        if (retainedFragment == null) {
            Log.d("RetainedFragment " , "is Null in portrait view");
        } else {
            Log.d("RetainedFragment " , "is retained in portrait view");
            bundle = retainedFragment.getBundle();
            final MealList mealList = (MealList) bundle.getSerializable(RecipesActivity.MEAL_DATA_KEY);
            final ArrayList<NewDishModel> meals = mealList.getListOfMeals();
            String[] arr = new String[meals.size()];
            for (int i = 0; i < meals.size(); i++) {
                arr[i] = meals.get(i).getNameOfDish();
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(v.getContext(), R.layout.activity_listview_portrait, arr);
            ListView myListView = (ListView) v.findViewById(R.id.recipe_list_view);
            myListView.setAdapter(adapter);
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    meals.get(i).setSelectionCounter(meals.get(i).getSelectionCounter() + 1);
                    mealList.setListOfMeals(meals);
                    try {
                        FileOutputStream fileOutputStream = view.getContext().openFileOutput(NewDishActivity.filename, Context.MODE_PRIVATE);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(mealList);
                        objectOutputStream.close();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    NewDishModel recipeToBeEdited = meals.get(i);
                    Bundle dataToBeSent = new Bundle();
                    Intent intent = new Intent(getActivity(), EditMealActivity.class);
                    dataToBeSent.putInt("ITEM_INDEX", i);
                    dataToBeSent.putBundle("LIST_OF_RECIPES", bundle);
                    intent.putExtras(dataToBeSent);
                    startActivity(intent);
                    return true;
                }
            });
        }
        return v;
    }
}
