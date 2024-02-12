package ua.gaponov.datacollectionterminal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class InventoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventories);
    }

    public void onBtnNewInventoryClick(View view) {
        Intent intent = new Intent();
        intent.setClass(InventoriesActivity.this, InventoryDetailActivity.class);
        intent.putExtra("guid", "");
        startActivity(intent);
    }
}
