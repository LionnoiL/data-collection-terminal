package ua.gaponov.datacollectionterminal;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PriceChekActivity extends AppCompatActivity {

    private BarcodeReceiver barcodeReceiver;
    public TextView textViewProductName;
    public TextView textViewBarCode;
    public TextView textViewPrice;
    public TextView textViewStock;

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
        barcodeReceiver = new BarcodeReceiver(PriceChekActivity.this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("scan.rcv.message");
        registerReceiver(barcodeReceiver, intentFilter);
    }
}
