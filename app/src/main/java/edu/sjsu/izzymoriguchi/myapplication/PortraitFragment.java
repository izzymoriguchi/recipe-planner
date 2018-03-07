package edu.sjsu.izzymoriguchi.myapplication;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PortraitFragment extends Fragment {
    // a bundle object we want to retain
    private Bundle bundle;
    private RetainedFragment retainedFragment;

    public PortraitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_portrait, container, false);;
        Log.d("PortraitFragment " , "onCreateView is called");
        FragmentManager fragmentManager = getFragmentManager();

        retainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(RecipesActivity.TAG_RETAINED_FRAGMENT);
        if (retainedFragment == null) {
            Log.d("RetainedFragment " , "is Null in portrait view");
        } else {
            Log.d("RetainedFragment " , "is retained in portrait view");
            bundle = retainedFragment.getBundle();
            MealList mealList = (MealList) bundle.getSerializable(RecipesActivity.MEAL_DATA_KEY);
            ArrayList<NewDishModel> meals = mealList.getListOfMeals();
            String[] arr = new String[meals.size()];
            for (int i = 0; i < meals.size(); i++) {
                arr[i] = meals.get(i).getNameOfDish();
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(v.getContext(), R.layout.activity_listview, arr);
            ListView myListView = (ListView) v.findViewById(R.id.recipe_list_view);
            myListView.setAdapter(adapter);
        }
        return v;
    }

}
