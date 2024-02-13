package ua.gaponov.datacollectionterminal.activity.inventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ua.gaponov.datacollectionterminal.R;
import ua.gaponov.datacollectionterminal.inventory.Inventory;
import ua.gaponov.datacollectionterminal.inventory.InventoryService;
import ua.gaponov.datacollectionterminal.utils.DateUtils;

public class InventoriesActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> mStartForResult;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventories);

        listView = findViewById(R.id.listview_inventories);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            HashMap hashMap = (HashMap) adapterView.getAdapter().getItem(i);
            Inventory inventory = (Inventory) hashMap.get("doc");

            Intent intent = new Intent();
            intent.setClass(InventoriesActivity.this, InventoryDetailActivity.class);
            intent.putExtra("doc", inventory);

            mStartForResult.launch(intent);
        });

        mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        boolean up = intent.getBooleanExtra("ACCESS_MESSAGE", false);
                        if (up) {
                            show();
                        }
                    }
                });

        show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBtnNewInventoryClick(View view) {
        Inventory inventory = new Inventory();
        inventory.setDate(DateUtils.getCurrentDate());
        Intent intent = new Intent();
        intent.setClass(InventoriesActivity.this, InventoryDetailActivity.class);
        intent.putExtra("doc", inventory);
        startActivity(intent);
    }

    public void show() {
        List<Inventory> docs = InventoryService.getInventories();
        int dl = R.layout.item_inventory;

        List<HashMap<String, Object>> data = new ArrayList<>();
        for (Inventory doc : docs) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("doc", doc);
            data.add(item);
        }

        SimpleAdapter adapter = new InventoryListAdapter(getApplicationContext(), data, dl,
                new String[]{"inventoryDate", "inventoryComment", "textViewId"},
                new int[]{R.id.inventoryDate, R.id.inventoryComment, R.id.textViewRowNumber});
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
