package edu.sjsu.izzymoriguchi.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by izzymoriguchi on 3/2/18.
 */

public class MealList implements Serializable {
    private ArrayList<NewDishModel> listOfMeals;

    public ArrayList<NewDishModel> getListOfMeals() {
        return listOfMeals;
    }

    public void setListOfMeals(ArrayList<NewDishModel> listOfMeals) {
        this.listOfMeals = listOfMeals;
    }
}
