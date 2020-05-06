package cn.edu.ncu.collegesecondhand.ui.my.modify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cn.edu.ncu.collegesecondhand.R;
import es.dmoral.toasty.Toasty;

public class UpdatePhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);

        Toolbar toolbar = findViewById(R.id.update_phone_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent=getIntent();
        final String phone=intent.getStringExtra("phone");
        final int id=intent.getIntExtra("id",0);

        final EditText editText=findViewById(R.id.update_phone_phone);
        Button button=findViewById(R.id.update_phone_finish);

        if (phone!=null){
            editText.setText(phone);
        }
        showSoftInputFromWindow(editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String editedPhone=editText.getText().toString();

                //Check whether valid
                if (editedPhone.length()<11||!editedPhone.startsWith("1"))
                {
                    Toasty.error(getBaseContext(),"请输入正确的手机号!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (editedPhone.equals(phone)){
                        return;
                    }

                    //save to database
                    RequestQueue requestQueue= Volley.newRequestQueue(getBaseContext());
                    String url=getResources().getString(R.string.ip_address)+"/user/updatePhone?id="+id+
                            "&phone="+editedPhone;
                    StringRequest request=new StringRequest(Request.Method.POST
                            , url
                            , new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("0")){
                                Intent data=new Intent();
                                data.putExtra("phone",editedPhone);
                                Toasty.success(getBaseContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK,data);
                                finish();
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
        });




    }
    public void showSoftInputFromWindow(EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        UpdatePhoneActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
