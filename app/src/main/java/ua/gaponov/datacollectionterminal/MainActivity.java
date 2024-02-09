package ua.gaponov.datacollectionterminal;

import static ua.gaponov.datacollectionterminal.Helpers.getOptions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helpers.context = getApplicationContext();
        getOptions();
        setContentView(R.layout.activity_main);
    }

    public void onBtnPriceCheckerClick(View view) {
        Intent intent = new Intent(MainActivity.this, PriceChekActivity.class);
        startActivity(intent);
    }

    public void onBtnInventoryClick(View view) {

    }

    public void onBtnOptionsClick(View view) {
        Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
        startActivity(intent);
    }
}