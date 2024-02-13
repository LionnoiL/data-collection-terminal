package ua.gaponov.datacollectionterminal.activity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static ua.gaponov.datacollectionterminal.utils.Helpers.getOptions;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ua.gaponov.datacollectionterminal.R;

public class OptionsActivity extends AppCompatActivity {

    EditText editTextIp;
    EditText editTextUser;
    EditText editTextPass;
    EditText editTextShopId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        SharedPreferences mSharedPref = getDefaultSharedPreferences(this);

        editTextIp = findViewById(R.id.editTextIp);
        editTextUser = findViewById(R.id.editTextUser);
        editTextPass = findViewById(R.id.editTextPass);
        editTextShopId = findViewById(R.id.editTextComment);

        String savedHost = mSharedPref.getString("HOST_NAME", "");
        String savedUser = mSharedPref.getString("USER_NAME", "");
        String savedPass = mSharedPref.getString("USER_PASS", "");
        String savedShopId = mSharedPref.getString("SHOP_ID", "1");

        editTextIp.setText(savedHost);
        editTextUser.setText(savedUser);
        editTextPass.setText(savedPass);
        editTextShopId.setText(savedShopId);
    }

    public void onbtnSaveClick(View view) {
        SharedPreferences mSharedPref = getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEditor = mSharedPref.edit();

        mEditor.putString("HOST_NAME", editTextIp.getText().toString());
        mEditor.putString("USER_NAME", editTextUser.getText().toString());
        mEditor.putString("USER_PASS", editTextPass.getText().toString());
        mEditor.putString("SHOP_ID", editTextShopId.getText().toString());
        mEditor.commit();

        getOptions();
        finish();
    }

    public void onbtnChancelClick(View view) {
        finish();
    }
}
