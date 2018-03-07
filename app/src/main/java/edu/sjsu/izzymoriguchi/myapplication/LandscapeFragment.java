package edu.sjsu.izzymoriguchi.myapplication;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandscapeFragment extends Fragment {
    private Bundle bundle;
    private RetainedFragment retainedFragment;

    public LandscapeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LandscapeFragment " , "onCreateView is called");
        View v = inflater.inflate(R.layout.fragment_landscape, container, false);

        FragmentManager fragmentManager = getFragmentManager();
        retainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(RecipesActivity.TAG_RETAINED_FRAGMENT);
        if (retainedFragment == null) {
            Log.d("RetainedFragment " , "is Null in landscape view");
        } else {
            Log.d("RetainedFragment " , "is retained in portrait view");
            bundle = retainedFragment.getBundle();
            MealList mealList = (MealList) bundle.getSerializable(RecipesActivity.MEAL_DATA_KEY);
            final ArrayList<NewDishModel> meals = mealList.getListOfMeals();
            String[] recipeNamesArray = new String[meals.size()];
            for (int i = 0; i < meals.size(); i++) {
                recipeNamesArray[i] = meals.get(i).getNameOfDish();
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(v.getContext(),
                    R.layout.activity_listview, recipeNamesArray);
            ListView myListView = (ListView) v.findViewById(R.id.recipe_list_view_landscape);
            myListView.setAdapter(arrayAdapter);

            final LinearLayout layoutForIngredients = (LinearLayout) v.findViewById(R.id.ingredients_names);
//            final RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.recipe_information);
            final RelativeLayout layoutForRecipeTitle = (RelativeLayout) v.findViewById(R.id.dish_name);
            final RelativeLayout layoutForRecipeDirection = (RelativeLayout) v.findViewById(R.id.dish_direction);
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    layoutForIngredients.removeAllViews();
                    layoutForRecipeDirection.removeAllViews();
                    NewDishModel selectedRecipe = meals.get(i);

                    // programmatically add fields
                    RelativeLayout.LayoutParams paramsForRecipeTitle = (RelativeLayout.LayoutParams) layoutForRecipeTitle.getLayoutParams();
                    TextView recipeTitle = new TextView(view.getContext());
                    recipeTitle.setId(View.generateViewId());
                    recipeTitle.setText("Name of recipe");
                    layoutForRecipeTitle.addView(recipeTitle, paramsForRecipeTitle);
//                    relativeLayout.addView(tv1, layoutparams);


                    RelativeLayout.LayoutParams paramsForIngredients = (RelativeLayout.LayoutParams) layoutForIngredients.getLayoutParams();
                    ArrayList<String> recipeIngredients = selectedRecipe.getListOfItemName();
                    for (int j = 0; j < recipeIngredients.size(); j++) {
                        TextView textView = new TextView(view.getContext());
                        textView.setText(recipeIngredients.get(j));
                        layoutForIngredients.addView(textView, paramsForIngredients);
                    }


                    RelativeLayout.LayoutParams paramsForRecipeDirection = (RelativeLayout.LayoutParams) layoutForRecipeDirection.getLayoutParams();
                    TextView recipeDirection = new TextView(view.getContext());
                    recipeDirection.setId(View.generateViewId());
                    recipeDirection.setText(selectedRecipe.getDirection());
                    layoutForRecipeDirection.addView(recipeDirection, paramsForRecipeDirection);

                }
            });

        }
        return v;
    }

}
