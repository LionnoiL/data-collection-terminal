package ua.gaponov.datacollectionterminal;


import static ua.gaponov.datacollectionterminal.Helpers.c1ShopId;
import static ua.gaponov.datacollectionterminal.Helpers.c1UserName;
import static ua.gaponov.datacollectionterminal.Helpers.c1UserPassword;
import static ua.gaponov.datacollectionterminal.Helpers.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BarcodeReceiver extends BroadcastReceiver {

    private static final String BARCODE_PARAM = "barocode";
    private PriceChekActivity priceChekActivity;

    public BarcodeReceiver(PriceChekActivity priceChekActivity) {
        this.priceChekActivity = priceChekActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        byte[] barcodeBytes = intent.getByteArrayExtra(BARCODE_PARAM);
        int lng = intent.getIntExtra("length", 0);

        String barcode = new String(barcodeBytes, 0, lng);
        priceChekActivity.textViewBarCode.setText(barcode);

        GetHttpData getHttpData = new GetHttpData();
        getHttpData.barcode = barcode;
        getHttpData.setRequestListener(() -> {
            try {
                JSONObject json = new JSONObject(getHttpData.res);
                String product_name = json.getString("name");
                if (product_name.equals("")){
                    priceChekActivity.textViewProductName.setText("Товар не знайдений");
                    priceChekActivity.textViewPrice.setText("");
                    priceChekActivity.textViewStock.setText("");
                } else {
                    priceChekActivity.textViewProductName.setText(product_name);
                    priceChekActivity.textViewPrice.setText(json.getString("price") + " грн");
                    priceChekActivity.textViewStock.setText(json.getString("stock"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        getHttpData.execute();
    }

    public interface HttpEnd {
        void endRequest();
    }

    public static class GetHttpData extends AsyncTask<Void, Void, Boolean>{
        private HttpEnd listener;
        public String barcode;
        public String res;

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

    public static String getBarcodeInfo(String barcode) throws Exception {

        String url = "";
        url = host+"?barcode="+barcode+"&shop_id="+c1ShopId;

        URL obj = new URL(url);
        int responseCode=200;

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setConnectTimeout(20000);

        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        String user_pass = c1UserName +":"+ c1UserPassword;
        String encoded = Base64.encodeToString(user_pass.getBytes("UTF-8"), Base64.DEFAULT);
        con.setRequestProperty("Authorization", "Basic "+encoded);
        String strRes = "";

        try {
            InputStream in = new BufferedInputStream(con.getInputStream());
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            strRes = "";

            while((bytesRead = in.read(contents)) != -1) {
                strRes += new String(contents, 0, bytesRead);
            }

            System.out.print(strRes);
            return strRes;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            con.disconnect();
        }
        return strRes;
    }
}
