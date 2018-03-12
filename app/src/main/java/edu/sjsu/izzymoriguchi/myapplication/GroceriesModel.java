package edu.sjsu.izzymoriguchi.myapplication;

/**
 * Created by izzymoriguchi on 3/11/18.
 */

public class GroceriesModel {
    private String name;
    private int qty;
    private String unit;

    public GroceriesModel(String name, int qty, String unit) {
        this.name = name;
        this.qty = qty;
        this.unit = unit;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append(" (");
        stringBuilder.append(qty);
        stringBuilder.append(" ");
        stringBuilder.append(unit);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public int getQty() {
        return qty;
    }

    public void incrementQty() {
        this.qty++;
    }

    public void decrementQty() {
        this.qty--;
    }
}
