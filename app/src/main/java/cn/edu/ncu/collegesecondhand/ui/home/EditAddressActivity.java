package cn.edu.ncu.collegesecondhand.ui.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.Address;
import es.dmoral.toasty.Toasty;

public class EditAddressActivity extends AppCompatActivity {
    EditText editText_name;
    EditText editText_phone;
    EditText editText_address;
    Button button_save;
    Button button_delete;
    Address address;
    SharedPreferences sharedPreferences;
    String currentAccount;
    Context mContext;
    TextView test;

    String name;
    String phone;
    String mAddress;
    RequestQueue requestQueue;
    private AlertDialog.Builder builder;


    private void init() {
        requestQueue = Volley.newRequestQueue(getBaseContext());

        mContext = getBaseContext();
        editText_name = findViewById(R.id.editor_name);
        editText_phone = findViewById(R.id.editor_phone);
        editText_address = findViewById(R.id.editor_address);
        button_save = findViewById(R.id.editor_save);
        button_delete = findViewById(R.id.editor_delete);

        test = findViewById(R.id.edit_test);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        init();
        sharedPreferences = getSharedPreferences("userAccount", MODE_PRIVATE);
        currentAccount = sharedPreferences.getString("account", "");
        Intent intent = getIntent();


        address = (Address) intent.getSerializableExtra("address");
        if (address != null) {
            editText_name.setText(address.getName());
            editText_phone.setText(address.getPhone());
            editText_address.setText(address.getAddress());

            button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText_name.getText().toString().equals("")) {
                        Toasty.error(mContext, "请输入收货人姓名！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (editText_phone.getText().toString().equals("")) {
                        Toasty.error(mContext, "请输入收货人电话！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (editText_address.getText().toString().equals("")) {
                        Toasty.error(mContext, "请输入收货人地址！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    name = editText_name.getText().toString();
                    mAddress = editText_address.getText().toString();
                    phone = editText_phone.getText().toString();


                    String url = getResources().getString(R.string.ip_address) + "/address/update?id=" +
                            address.getId() + "&name=" + name + "&phone=" + phone + "&address=" + mAddress;
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("0")) {
                                Toasty.success(mContext, "修改成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(mContext, "请检查网络后重试！\n" + error.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });
                    requestQueue.add(request);

/*
test
                    String str = "name: " + name + "\naddress: " + mAddress + "\nphone: " + phone + "\naccount" + currentAccount;
                    test.setText(str);*/

                }
            });
            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //confirm

                    builder = new AlertDialog.Builder(EditAddressActivity.this).setIcon(R.drawable.ic_error_outline_black_24dp).setTitle("删除警告")
                            .setMessage("是否删除该地址？").setPositiveButton("删除该地址", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    String url = getResources().getString(R.string.ip_address) +
                                            "/address/delete/" +
                                            address.getId();
                                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.contains("0")) {
                                                Toasty.success(mContext, "删除成功！", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toasty.error(mContext, "请检查网络后重试！\n" + error.toString(), Toast.LENGTH_SHORT).show();


                                        }
                                    });
                                    requestQueue.add(request);


                                    //ToDo: 你想做的事情
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    dialogInterface.dismiss();


                                }
                            });
                    builder.create().show();


                }
            });

        } else {
            button_delete.setVisibility(View.INVISIBLE);
            button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    name = editText_name.getText().toString();
                    mAddress = editText_address.getText().toString();
                    phone = editText_phone.getText().toString();


                    String url = getResources().getString(R.string.ip_address) + "/address/insert?account=" +
                            currentAccount + "&name=" + name + "&phone=" + phone + "&address=" + mAddress;
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("0")) {
                                Toasty.success(mContext, "添加成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(mContext, "请检查网络后重试！\n" + error.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });
                    requestQueue.add(request);


                }
            });
        }


        //    Toast.makeText(this, address.getAddress(), Toast.LENGTH_SHORT).show();


    }

}
