package edu.sjsu.izzymoriguchi.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.List;

public class NewItemActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM_NAME = "edu.sjsu.izzymoriguchi.ITEM_NAME";
    public static final String EXTRA_ITEM_QTY = "edu.sjsu.izzymoriguchi.ITEM_QTY";
    public static final String EXTRA_ITEM_UNIT = "edu.sjsu.izzymoriguchi.ITEM_UNIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

    }

    public void sendItemToNewDish(View view) {
        Intent intent = new Intent(this, NewDishActivity.class);
        String nameOfNewItem = ((EditText) findViewById(R.id.new_item_name)).getText().toString();
        String qtyOfNewItem = ((EditText) findViewById(R.id.new_item_qty)).getText().toString();
        String unitOfNewItem = ((EditText) findViewById(R.id.new_item_unit)).getText().toString();
        intent.putExtra(EXTRA_ITEM_NAME, nameOfNewItem);
        intent.putExtra(EXTRA_ITEM_QTY, qtyOfNewItem);
        intent.putExtra(EXTRA_ITEM_UNIT, unitOfNewItem);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
