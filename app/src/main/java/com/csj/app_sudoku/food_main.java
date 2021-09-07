package com.csj.app_sudoku;


import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class food_main extends AppCompatActivity {
    TextView food_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_main);


        food_change = (TextView)findViewById(R.id.food_change);
        registerForContextMenu(food_change);

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case R.id.badfood:
                Intent bad_food = new Intent(this, bad_food.class);
                startActivity(bad_food);

            case R.id.BACK:
                finish();
        }
        return super.onContextItemSelected(item);
    }

}
