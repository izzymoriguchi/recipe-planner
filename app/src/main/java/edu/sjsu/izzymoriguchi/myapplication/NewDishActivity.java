package edu.sjsu.izzymoriguchi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class NewDishActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static String filename = "recipes.ser";
    private ArrayList<String> items;
    private ArrayAdapter<String> dataAdapter;
    private Spinner[] spinners = new Spinner[10];
    static final String SPINNER_ITEMS_STATE = "spinnerItemState";
    private NewDishModel newDishData;
    private boolean hasDuplicate;
    private static final int PHOTO_PICKER_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish);

        spinners[0] = (Spinner) findViewById(R.id.item1_spinner);
        spinners[1] = (Spinner) findViewById(R.id.item2_spinner);
        spinners[2] = (Spinner) findViewById(R.id.item3_spinner);
        spinners[3] = (Spinner) findViewById(R.id.item4_spinner);
        spinners[4] = (Spinner) findViewById(R.id.item5_spinner);
        spinners[5] = (Spinner) findViewById(R.id.item6_spinner);
        spinners[6] = (Spinner) findViewById(R.id.item7_spinner);
        spinners[7] = (Spinner) findViewById(R.id.item8_spinner);
        spinners[8] = (Spinner) findViewById(R.id.item9_spinner);
        spinners[9] = (Spinner) findViewById(R.id.item10_spinner);


        // Spinner Drop down elements
        items = new ArrayList<String>();
        items.add("Select an item..");

        try { // if previously saved, load it first
            FileInputStream ifile = new FileInputStream(this.getFilesDir() + File.separator + filename);
            ObjectInputStream in = new ObjectInputStream(ifile);
            MealList lstOfMeals = (MealList) in.readObject();
            ArrayList<NewDishModel> meals = lstOfMeals.getListOfMeals();
            for (int i = 0; i < meals.size(); i++) {
                for (String name : meals.get(i).getListOfItemName()) {
                    items.add(name);
                }
            }
            in.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        newDishData = new NewDishModel();

        EditText recipeName = (EditText) findViewById(R.id.recipe_name);
        recipeName.addTextChangedListener(new GenericTextWatcher(recipeName));

        EditText qty1 = (EditText) findViewById(R.id.item1_spinner_qty);
        EditText qty2 = (EditText) findViewById(R.id.item2_spinner_qty);
        EditText qty3 = (EditText) findViewById(R.id.item3_spinner_qty);
        EditText qty4 = (EditText) findViewById(R.id.item4_spinner_qty);
        EditText qty5 = (EditText) findViewById(R.id.item5_spinner_qty);
        EditText qty6 = (EditText) findViewById(R.id.item6_spinner_qty);
        EditText qty7 = (EditText) findViewById(R.id.item7_spinner_qty);
        EditText qty8 = (EditText) findViewById(R.id.item8_spinner_qty);
        EditText qty9 = (EditText) findViewById(R.id.item9_spinner_qty);
        EditText qty10 = (EditText) findViewById(R.id.item10_spinner_qty);

        qty1.addTextChangedListener(new GenericTextWatcher(qty1));
        qty2.addTextChangedListener(new GenericTextWatcher(qty2));
        qty3.addTextChangedListener(new GenericTextWatcher(qty3));
        qty4.addTextChangedListener(new GenericTextWatcher(qty4));
        qty5.addTextChangedListener(new GenericTextWatcher(qty5));
        qty6.addTextChangedListener(new GenericTextWatcher(qty6));
        qty7.addTextChangedListener(new GenericTextWatcher(qty7));
        qty8.addTextChangedListener(new GenericTextWatcher(qty8));
        qty9.addTextChangedListener(new GenericTextWatcher(qty9));
        qty10.addTextChangedListener(new GenericTextWatcher(qty10));

        EditText unit1 = (EditText) findViewById(R.id.item1_spinner_unit);
        EditText unit2 = (EditText) findViewById(R.id.item2_spinner_unit);
        EditText unit3 = (EditText) findViewById(R.id.item3_spinner_unit);
        EditText unit4 = (EditText) findViewById(R.id.item4_spinner_unit);
        EditText unit5 = (EditText) findViewById(R.id.item5_spinner_unit);
        EditText unit6 = (EditText) findViewById(R.id.item6_spinner_unit);
        EditText unit7 = (EditText) findViewById(R.id.item7_spinner_unit);
        EditText unit8 = (EditText) findViewById(R.id.item8_spinner_unit);
        EditText unit9 = (EditText) findViewById(R.id.item9_spinner_unit);
        EditText unit10 = (EditText) findViewById(R.id.item10_spinner_unit);

        unit1.addTextChangedListener(new GenericTextWatcher(unit1));
        unit1.addTextChangedListener(new GenericTextWatcher(unit2));
        unit1.addTextChangedListener(new GenericTextWatcher(unit3));
        unit1.addTextChangedListener(new GenericTextWatcher(unit4));
        unit1.addTextChangedListener(new GenericTextWatcher(unit5));
        unit1.addTextChangedListener(new GenericTextWatcher(unit6));
        unit1.addTextChangedListener(new GenericTextWatcher(unit7));
        unit1.addTextChangedListener(new GenericTextWatcher(unit8));
        unit1.addTextChangedListener(new GenericTextWatcher(unit9));
        unit1.addTextChangedListener(new GenericTextWatcher(unit10));

        EditText directionOfRecipe = (EditText) findViewById(R.id.direction);
        directionOfRecipe.addTextChangedListener(new GenericTextWatcher(directionOfRecipe));

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach data adapter with spinner
        for (int i = 0; i < spinners.length; i++) {
            spinners[i].setAdapter(dataAdapter);
            spinners[i].setOnItemSelectedListener(this);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),
                "Selected position : " + i, Toast.LENGTH_SHORT).show();
        if (i > 0) {
            newDishData.setNameOfIngredientByIndex(i - 1, item);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void inputNewItem(View view) {
        String newIngredient = ((EditText) findViewById(R.id.add_new_item_input)).getText().toString();
        items.add(newIngredient);
    }

    public void saveNewDish(View view) {
        FileInputStream ifile = null;
        ObjectInputStream in = null;
        ArrayList<NewDishModel> meals;
        MealList lstOfMeals;

        Toast.makeText(this, "Trying to save" + newDishData.getNameOfDish() , Toast.LENGTH_SHORT).show();
        if (hasDuplicate) {
            Toast.makeText(this, "Can't save since" + newDishData.getNameOfDish() + " already exists.", Toast.LENGTH_SHORT).show();
            return;
        }

        /* Use this line to delete file ------------------------------------------------
        this.deleteFile(filename);

        -------------------------------------------------------------------------------*/


        try { // if previously saved, load it first
            ifile = new FileInputStream(this.getFilesDir() + File.separator + filename);
            Log.d("File", "Exist ");
            in = new ObjectInputStream(ifile);
            lstOfMeals = (MealList) in.readObject();
            meals = lstOfMeals.getListOfMeals();
            meals.add(newDishData);
            in.close();
            try {
                FileOutputStream fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(lstOfMeals);
                for (int i = 0; i < meals.size(); i++) {
                    Log.d("ITEM", "Meal item: " + meals.get(i).getNameOfDish());
                }
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) { // adding a recipe for the first time
            lstOfMeals = new MealList();
            meals = new ArrayList<>();
            meals.add(newDishData);
            lstOfMeals.setListOfMeals(meals);
            try {
                FileOutputStream fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(lstOfMeals);
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException ex) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Toast.makeText(this, "Added " + newDishData.getNameOfDish(), Toast.LENGTH_SHORT).show();
        finish(); // close the activity

    }

    public void setRecipeIcon(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PHOTO_PICKER_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_PICKER_ID) {
            ImageView recipeIcon = findViewById(R.id.recipe_icon);
            recipeIcon.setImageURI(data.getData());
        }
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch(view.getId()) {
                case R.id.recipe_name:
                    if (!doRecipeNameExist(text)) {
                        newDishData.setNameOfDish(text);
                        hasDuplicate = false;
                    } else {
                        EditText recipeName = (EditText) view;
                        recipeName.setError(text + " already exists");
                        hasDuplicate = true;
                    }
                    break;
                case R.id.item1_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(0, text);
                    break;
                case R.id.item2_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(1, text);
                    break;
                case R.id.item3_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(2, text);
                    break;
                case R.id.item4_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(3, text);
                    break;
                case R.id.item5_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(4, text);
                    break;
                case R.id.item6_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(5, text);
                    break;
                case R.id.item7_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(6, text);
                    break;
                case R.id.item8_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(7, text);
                    break;
                case R.id.item9_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(8, text);
                    break;
                case R.id.item10_spinner_qty:
                    newDishData.setQtyOfIngredientByIndex(9, text);
                    break;
                case R.id.item1_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(0, text);
                    break;
                case R.id.item2_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(1, text);
                    break;
                case R.id.item3_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(2, text);
                    break;
                case R.id.item4_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(3, text);
                    break;
                case R.id.item5_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(4, text);
                    break;
                case R.id.item6_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(5, text);
                    break;
                case R.id.item7_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(6, text);
                    break;
                case R.id.item8_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(7, text);
                    break;
                case R.id.item9_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(8, text);
                    break;
                case R.id.item10_spinner_unit:
                    newDishData.setUnitOfIngredientByIndex(9, text);
                    break;
                case R.id.direction:
                    newDishData.setDirection(text);
                    break;
            }
        }


    }
    public boolean doRecipeNameExist(String recipeName) {
        try {
            FileInputStream ifile = new FileInputStream(getFilesDir() + File.separator + filename);
            ObjectInputStream in = new ObjectInputStream(ifile);
            MealList lstOfMeals = (MealList) in.readObject();
            ArrayList<NewDishModel> meals = lstOfMeals.getListOfMeals();
            for (int i = 0; i < meals.size(); i++) {
                if (meals.get(i).getNameOfDish().equals(recipeName)) {
                    return true;
                }
            }
            in.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
