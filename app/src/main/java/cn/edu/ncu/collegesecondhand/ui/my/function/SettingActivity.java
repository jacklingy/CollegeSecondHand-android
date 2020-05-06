package cn.edu.ncu.collegesecondhand.ui.my.function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.Address;
import cn.edu.ncu.collegesecondhand.ui.home.AddressActivity;

public class SettingActivity extends AppCompatActivity {
    TextView textView_sign_out;
    SharedPreferences sharedPreferences;
    TextView textView_address;
    String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        textView_sign_out = findViewById(R.id.setting_sign_out);
        textView_address = findViewById(R.id.setting_address);
        sharedPreferences = super.getSharedPreferences("userAccount", MODE_PRIVATE);
        account=sharedPreferences.getString("account","");

        textView_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("account", null);
                editor.apply();
                finish();
            }
        });
        textView_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AddressActivity.class);
                intent.putExtra("userAccount",account);
                startActivity(intent);
            }
        });


    }
}
