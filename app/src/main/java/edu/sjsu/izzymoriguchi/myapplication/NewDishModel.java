package edu.sjsu.izzymoriguchi.myapplication;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by izzymoriguchi on 2/27/18.
 */

public class NewDishModel implements Serializable {
    private static final int MAX_ITEM = 10;
    private int selectionCounter;
    private String nameOfDish;
    private String[] listOfItemName;
    private String imageUri;
    private String[] listOfIUnit;
    private String direction;
    private String[] listOfQty;
    private static final long serialVersionUID = -70602105400464481L;

    public NewDishModel() {
        listOfItemName = new String[MAX_ITEM];
        listOfIUnit = new String[MAX_ITEM];
        listOfQty = new String[MAX_ITEM];
        selectionCounter = 0;
    }

    public int getSelectionCounter() {
        return selectionCounter;
    }

    public void setSelectionCounter(int selectionCounter) {
        this.selectionCounter = selectionCounter;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getNameOfDish() {
        return nameOfDish;
    }

    public void setNameOfDish(String nameOfDish) {
        this.nameOfDish = nameOfDish;
    }

    public void setNameOfIngredientByIndex(int index, String nameOfIngredient) {
        listOfItemName[index] = nameOfIngredient;
    }

    public void setQtyOfIngredientByIndex(int index, String qty) {
        listOfQty[index] = qty;
    }

    public void setUnitOfIngredientByIndex(int index, String unit) {
        listOfIUnit[index] = unit;
    }

    public String[] getListOfItemName() {
        return listOfItemName;
    }

    public void setListOfItemName(String[] listOfItemName) {
        this.listOfItemName = listOfItemName;
    }

    public void setListOfIUnit(String[] listOfIUnit) {
        this.listOfIUnit = listOfIUnit;
    }

    public void setListOfQty(String[] listOfQty) {
        this.listOfQty = listOfQty;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getListOfQty() {
        return listOfQty;
    }

    public String[] getListOfIUnit() {
        return listOfIUnit;
    }
}