package ua.gaponov.datacollectionterminal.activity.inventory;

import static ua.gaponov.datacollectionterminal.utils.Constants.ACCESS_MESSAGE;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ua.gaponov.datacollectionterminal.R;
import ua.gaponov.datacollectionterminal.inventory.Inventory;
import ua.gaponov.datacollectionterminal.inventory.InventoryItem;
import ua.gaponov.datacollectionterminal.inventory.InventoryService;
import ua.gaponov.datacollectionterminal.product.Product;
import ua.gaponov.datacollectionterminal.utils.BarcodeReceiver;
import ua.gaponov.datacollectionterminal.utils.DateUtils;
import ua.gaponov.datacollectionterminal.utils.Helpers;
import ua.gaponov.datacollectionterminal.utils.OnGetBarcode;

public class InventoryDetailActivity extends AppCompatActivity {

    private BarcodeReceiver barcodeReceiver;
    private BarcodeReceiverListener barcodeReceiverListener = new BarcodeReceiverListener();
    private Inventory inventory;
    private TextView editTextComment;
    private ListView listView = null;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);

        editTextComment = findViewById(R.id.editTextComment);
        listView = findViewById(R.id.listViewInventoryDetailItems);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(ProgressBar.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            inventory = new Inventory();
            inventory.setDate(DateUtils.getCurrentDate());
        } else {
            inventory = (Inventory) extras.getSerializable("doc");
        }

        show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeReceiver = new BarcodeReceiver(barcodeReceiverListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("scan.rcv.message");
        registerReceiver(barcodeReceiver, intentFilter);
    }

    public void onBtnSendDocClick(View view) {
        inventory.setComment(editTextComment.getText().toString());
        InventoryService.save(inventory);

        Intent data = new Intent();
        data.putExtra(ACCESS_MESSAGE, true);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onBtnCloseClick(View view) {
        finish();
    }

    public void show() {
        editTextComment.setText(inventory.getComment());

        int dl = R.layout.item_inventory_detail;
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (InventoryItem inventoryItem : inventory.getItems()) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("item", inventoryItem);
            data.add(item);
        }

        SimpleAdapter adapter = new InventoryItemsListAdapter(getApplicationContext(), data, dl,
                new String[]{"textViewRowNumber", "productName", "productQty", "productPrice"},
                new int[]{R.id.textViewRowNumber, R.id.productName, R.id.productQty, R.id.productPrice});
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private InventoryItem findItemByCode(String code) {
        InventoryItem inventoryItem = inventory.getItems()
                .stream()
                .filter(i -> code.equals(i.getCode()))
                .findFirst()
                .orElseGet(() ->
                {
                    InventoryItem item = new InventoryItem();
                    inventory.getItems().add(item);
                    return item;
                });

        return inventoryItem;
    }

    public class BarcodeReceiverListener implements OnGetBarcode {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void getBarcode(Product product) {
            spinner.setVisibility(ProgressBar.INVISIBLE);
            if (product.getCode() != null && !product.getCode().isEmpty()) {
                InventoryItem inventoryItem = findItemByCode(product.getCode());
                inventoryItem.setCode(product.getCode());
                inventoryItem.setProductName(product.getName());
                inventoryItem.setPrice(product.getPrice());
                inventoryItem.setQuantity(inventoryItem.getQuantity() + 1);
                inventoryItem.setDocId(inventory.getDocId());
                show();
            } else {
                Toast.makeText(getApplicationContext(),
                        Helpers.getString(R.string.product_not_found),Toast.LENGTH_SHORT
                ).show();
            }
        }

        @Override
        public void beforeGetBarcode() {
            spinner.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        public void afterGetBarcode() {

        }
    }
}
