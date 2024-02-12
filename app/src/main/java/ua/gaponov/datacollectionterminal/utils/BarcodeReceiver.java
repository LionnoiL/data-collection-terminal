package ua.gaponov.datacollectionterminal.utils;


import static ua.gaponov.datacollectionterminal.utils.Helpers.c1ShopId;
import static ua.gaponov.datacollectionterminal.utils.Helpers.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import ua.gaponov.datacollectionterminal.activity.PriceChekActivity;
import ua.gaponov.datacollectionterminal.entity.Product;
import ua.gaponov.datacollectionterminal.http.HttpUtil;

public class BarcodeReceiver extends BroadcastReceiver {

    private static final String BARCODE_PARAM = "barocode";
    private PriceChekActivity.BarcodeReceiverListener barcodeReceiverListener;

    public BarcodeReceiver(PriceChekActivity.BarcodeReceiverListener barcodeReceiverListener) {
        this.barcodeReceiverListener = barcodeReceiverListener;
    }

    public static String getBarcodeInfo(String barcode) {
        String url = "" + host + "?barcode=" + barcode + "&shop_id=" + c1ShopId;
        try {
            return HttpUtil.executeGet(url);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        barcodeReceiverListener.beforeGetBarcode();

        byte[] barcodeBytes = intent.getByteArrayExtra(BARCODE_PARAM);
        int lng = intent.getIntExtra("length", 0);

        String barcode = new String(barcodeBytes, 0, lng);
        GetHttpData getHttpData = new GetHttpData();
        getHttpData.barcode = barcode;
        getHttpData.setRequestListener(() -> {
            try {
                Product product = new Product(barcode);
                JSONObject json = new JSONObject(getHttpData.res);
                String product_name = json.getString("name");

                if (product_name.equals("")) {
                    product.setName("Товар не знайдений");
                } else {
                    product.setName(product_name);
                    product.setCode(json.getString("code"));
                    product.setPrice(json.getString("price") + " грн");
                    product.setStock(json.getString("stock"));
                }
                barcodeReceiverListener.getBarcode(product);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        getHttpData.execute();
        barcodeReceiverListener.afterGetBarcode();
    }

    public interface HttpEnd {
        void endRequest();
    }

    public static class GetHttpData extends AsyncTask<Void, Void, Boolean> {
        public String barcode;
        public String res;
        private HttpEnd listener;

        public GetHttpData setRequestListener(HttpEnd listener) {
            this.listener = listener;
            return this;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            listener.endRequest();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                res = getBarcodeInfo(barcode);
                cancel(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
