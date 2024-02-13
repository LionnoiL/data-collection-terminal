package ua.gaponov.datacollectionterminal.utils;

import static ua.gaponov.datacollectionterminal.utils.Helpers.c1UserName;
import static ua.gaponov.datacollectionterminal.utils.Helpers.c1UserPassword;

import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String executeGet(String urlString) throws Exception{
        URL url = new URL(urlString);
        HttpURLConnection con = getHttpURLConnection(url);
        return getResponseString(con);
    }

    @NonNull
    private static String getResponseString(HttpURLConnection con) {
        String strRes = "";
        try {
            InputStream in = new BufferedInputStream(con.getInputStream());
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            strRes = "";

            while ((bytesRead = in.read(contents)) != -1) {
                strRes += new String(contents, 0, bytesRead);
            }
            return strRes;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            con.disconnect();
        }
        return strRes;
    }

    @NonNull
    private static HttpURLConnection getHttpURLConnection(URL obj) throws IOException {
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setConnectTimeout(20000);

        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        String user_pass = c1UserName + ":" + c1UserPassword;
        String encoded = Base64.encodeToString(user_pass.getBytes("UTF-8"), Base64.DEFAULT);
        con.setRequestProperty("Authorization", "Basic " + encoded);
        return con;
    }
}
