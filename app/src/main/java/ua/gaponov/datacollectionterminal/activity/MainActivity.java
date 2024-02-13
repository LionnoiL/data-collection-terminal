package ua.gaponov.datacollectionterminal.activity;

import static ua.gaponov.datacollectionterminal.utils.Helpers.getOptions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ua.gaponov.datacollectionterminal.R;
import ua.gaponov.datacollectionterminal.activity.inventory.InventoriesActivity;
import ua.gaponov.datacollectionterminal.utils.Helpers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helpers.context = getApplicationContext();
        getOptions();
        Helpers.dbConnect();
        setContentView(R.layout.activity_main);
    }

    public void onBtnPriceCheckerClick(View view) {
        Intent intent = new Intent(MainActivity.this, PriceChekActivity.class);
        startActivity(intent);
    }

    public void onBtnInventoryClick(View view) {
        Intent intent = new Intent(MainActivity.this, InventoriesActivity.class);
        startActivity(intent);
    }

    public void onBtnOptionsClick(View view) {
        Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
        startActivity(intent);
    }
}