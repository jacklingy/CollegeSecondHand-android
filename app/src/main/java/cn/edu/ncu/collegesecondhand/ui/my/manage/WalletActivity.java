package cn.edu.ncu.collegesecondhand.ui.my.manage;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cn.edu.ncu.collegesecondhand.R;
import es.dmoral.toasty.Toasty;

public class WalletActivity extends AppCompatActivity {

    RequestQueue requestQueue ;;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Intent intent = getIntent();
        String account = intent.getStringExtra("userAccount");


        final TextView textView_money = findViewById(R.id.wallet_money);
        Button button_charge = findViewById(R.id.button_charge);
        Button button_withdraw = findViewById(R.id.button_withdraw);

        String url = getResources().getString(R.string.ip_address) + "/user/getCreditByAccount/" + account;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textView_money.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView_money.setText("ERROR：XXXX");
            }
        });
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        button_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent=new Intent(Intent.ACTION_MAIN);
                    ComponentName componentName=new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(componentName);
                    startActivity(intent);
                }catch (Exception e){
                    Toasty.info(getBaseContext(),"该手机未安装微信", Toast.LENGTH_SHORT).show();
                }

            }
        });

        button_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent=new Intent(Intent.ACTION_MAIN);
                    ComponentName componentName=new ComponentName("com.eg.android.AlipayGphone","com.alipay.mobile.quinox.splash.ShareDispenseActivity");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(componentName);
                    startActivity(intent);
                }catch (Exception e){
                    Toasty.info(getBaseContext(),"该手机未安装支付宝", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
