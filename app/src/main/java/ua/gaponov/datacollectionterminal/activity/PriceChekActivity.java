package ua.gaponov.datacollectionterminal.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ua.gaponov.datacollectionterminal.entity.Product;
import ua.gaponov.datacollectionterminal.utils.BarcodeReceiver;
import ua.gaponov.datacollectionterminal.R;
import ua.gaponov.datacollectionterminal.utils.OnGetBarcode;

public class PriceChekActivity extends AppCompatActivity {

    private BarcodeReceiver barcodeReceiver;
    private TextView textViewProductName;
    private TextView textViewBarCode;
    private TextView textViewPrice;
    private TextView textViewStock;
    private BarcodeReceiverListener barcodeReceiverListener = new BarcodeReceiverListener();
    private Runnable runnable = () -> clearText();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_check);

        textViewProductName = findViewById(R.id.textViewProductName);
        textViewBarCode = findViewById(R.id.textViewBarCode);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewStock = findViewById(R.id.textViewStock);
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeReceiver = new BarcodeReceiver(barcodeReceiverListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("scan.rcv.message");
        registerReceiver(barcodeReceiver, intentFilter);
    }

    private void clearText() {
        textViewProductName.setText("Відскануйте товар");
        textViewBarCode.setText("");
        textViewPrice.setText("");
        textViewStock.setText("");
    }

    public class BarcodeReceiverListener implements OnGetBarcode {

        @Override
        public void getBarcode(Product product) {
            textViewBarCode.setText(product.getBarcode());
            textViewProductName.setText(product.getName());
            textViewPrice.setText(product.getPrice());
            textViewStock.setText(product.getStock());
        }

        @Override
        public void beforeGetBarcode() {
            handler.removeCallbacks(runnable);
        }
        @Override
        public void afterGetBarcode() {
            handler.postDelayed(runnable, 5000);
        }
    }
}