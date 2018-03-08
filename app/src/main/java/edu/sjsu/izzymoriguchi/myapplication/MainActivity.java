package edu.sjsu.izzymoriguchi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToMealsActivity(View view) {
        Intent intent = new Intent(this, MealsActivity.class);
        startActivity(intent);
    }

    public void goToNewDishActivity(View view) {
        Intent intent = new Intent(this, NewDishActivity.class);
        startActivity(intent);
    }

    public void goToRecipesActivity(View view) {
        Intent intent = new Intent(this, RecipesActivity.class);
        startActivity(intent);
    }

    public void goToGroceriesActivity(View view) {
        Intent intent = new Intent(this, GroceriesActivity.class);
        startActivity(intent);
    }


    public void showPopupInfo(View view) {
        RelativeLayout layout = findViewById(R.id.relative_layout);
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup,null);

        Button closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

        final PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
