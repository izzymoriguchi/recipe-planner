package edu.sjsu.izzymoriguchi.myapplication;


import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandscapeFragment extends Fragment {
    private Bundle bundle;
    private View v;
    private RelativeLayout recipeDetails;

    public LandscapeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_landscape, container, false);

        recipeDetails = (RelativeLayout) v.findViewById(R.id.recipe_information);
        recipeDetails.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = getFragmentManager();
        RetainedFragment retainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(RecipesActivity.TAG_RETAINED_FRAGMENT);
        if (retainedFragment == null) {
            Log.d("RetainedFragment " , "is Null in landscape view");
        } else {
            bundle = retainedFragment.getBundle();
            final MealList mealList = (MealList) bundle.getSerializable(RecipesActivity.MEAL_DATA_KEY);
            final ArrayList<NewDishModel> meals = mealList.getListOfMeals();
            String[] recipeNamesArray = new String[meals.size()];
            for (int i = 0; i < meals.size(); i++) {
                recipeNamesArray[i] = meals.get(i).getNameOfDish();
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(v.getContext(),
                    R.layout.activity_listview_landscape, recipeNamesArray);
            ListView myListView = (ListView) v.findViewById(R.id.recipe_list_view_landscape);
            myListView.setAdapter(arrayAdapter);

            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    NewDishModel selectedRecipe = meals.get(i);
                    TextView titleOfRecipe = (TextView) v.findViewById(R.id.dish_name);
                    titleOfRecipe.setText(selectedRecipe.getNameOfDish());

                    ImageView photoOfRecipe = (ImageView) v.findViewById(R.id.dish_image);
                    Uri uri = Uri.parse(selectedRecipe.getImageUri());
                    photoOfRecipe.setImageURI(uri);

                    TextView ingredientsOfRecipe = (TextView) v.findViewById(R.id.ingredients);
                    ingredientsOfRecipe.setText("Ingredients:");

                    int j = 0;
                    while (selectedRecipe.getListOfItemName()[j] != null) {
                        ingredientsOfRecipe.append("\n\u25CF "
                                + selectedRecipe.getListOfItemName()[j]
                                + " (" + selectedRecipe.getListOfQty()[j]
                                + " " + selectedRecipe.getListOfIUnit()[j] + ")");
                        j++;
                    }

                    TextView directionOfRecipe = (TextView) v.findViewById(R.id.recipe_direction);
                    directionOfRecipe.setText("Directions:");
                    directionOfRecipe.append("\n" + selectedRecipe.getDirection());

                    recipeDetails.setVisibility(View.VISIBLE);
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
