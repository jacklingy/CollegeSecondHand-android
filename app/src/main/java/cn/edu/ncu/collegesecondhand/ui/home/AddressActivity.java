package cn.edu.ncu.collegesecondhand.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.AddressAdapter;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnAddressLayoutClickListener;
import cn.edu.ncu.collegesecondhand.entity.Address;

public class AddressActivity extends AppCompatActivity {
    private String currentAccount;
    private int currentAddressId;
    private Context context;
    private AddressAdapter addressAdapter;
    private RecyclerView recyclerView;
    private LinearLayout layout_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        context =getBaseContext();

        Intent intent = getIntent();
        currentAccount = intent.getStringExtra("userAccount");
        //useless
        currentAddressId = intent.getIntExtra("id", 0);

       // Toast.makeText(context, "current address id is: " + currentAddressId, Toast.LENGTH_SHORT).show();
        context = getApplicationContext();
        recyclerView = findViewById(R.id.address_recycler_view);
        layout_add=findViewById(R.id.address_add);

        showAddress();

        layout_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddressActivity.this,EditAddressActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        showAddress();


    }

    public void showAddress() {
        //Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = getResources().getString(R.string.ip_address) + "/address/getAddressesByAccount/" + currentAccount;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Gson gson = new Gson();
                Type type = new TypeToken<List<Address>>() {
                }.getType();
                final List<Address> addresses = gson.fromJson(response, type);

                if (addresses.size() == 0) {


                } else {
                    addressAdapter = new AddressAdapter(context, addresses);
                    //addressAdapter.setCurrentAddressId(currentAddressId);
                    addressAdapter.notifyDataSetChanged();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(addressAdapter);
                    addressAdapter.notifyDataSetChanged();
                    addressAdapter.setListener(new OnAddressLayoutClickListener() {
                        @Override
                        public void click(Address address) {

                            Intent intent = new Intent();
                            intent.putExtra("selectedAddress", address);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }
}
