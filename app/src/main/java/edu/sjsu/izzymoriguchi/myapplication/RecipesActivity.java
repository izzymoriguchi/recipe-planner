package edu.sjsu.izzymoriguchi.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {
    public static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";
    public static final String MEAL_DATA_KEY = "SerializableMeal";
    private RetainedFragment retainedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // step 1: get configuration object from it  we can know what orientation we have
        Configuration config = getResources().getConfiguration(); // retrieve the configuration of phone anytime

        // step2.1 get a FragmentManager object
        FragmentManager fragmentManager = getFragmentManager();

        //step 2.2 get a FragmentTransaction from FragmentManager object
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // find the retained fragment on activity restarts
        retainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(TAG_RETAINED_FRAGMENT);
        Log.d("RetainedFragment " , "retainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(TAG_RETAINED_FRAGMENT) is called");

        /*-------------------Use this code to reset the retainedFragment------------------------

        retainedFragment = null;

        ----------------------------------------------------------------------------------------*/


        if (retainedFragment == null) {
            Log.d("RetainedFragment " , "retainedFragment == null");
            // add the fragment
            retainedFragment = new RetainedFragment();
            fragmentTransaction.add(retainedFragment, TAG_RETAINED_FRAGMENT);
            // load bundle from a data source or perform any calculation
            retainedFragment.setBundle(loadData());
        }

        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LandscapeFragment landscapeFragment = new LandscapeFragment();
            Log.d("Orientation :", "Landscape");
            fragmentTransaction.replace(android.R.id.content, landscapeFragment);
        } else { // means config is portrait
            PortraitFragment portraitFragment = new PortraitFragment();
            Log.d("Orientation :", "Portrait");
            fragmentTransaction.replace(android.R.id.content, portraitFragment);
        }
        fragmentTransaction.commit();
        setContentView(R.layout.activity_recipes);
    }

    public Bundle loadData () {
        FileInputStream ifile = null;
        ObjectInputStream in = null;
        MealList lstOfMeals = null;
        try { // if previously saved, load it first
            ifile = new FileInputStream(getFilesDir() + File.separator + NewDishActivity.filename);
            in = new ObjectInputStream(ifile);
            lstOfMeals = (MealList) in.readObject();
            in.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(MEAL_DATA_KEY, lstOfMeals);
        return bundle;
    }
}
