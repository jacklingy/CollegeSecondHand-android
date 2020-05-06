package cn.edu.ncu.collegesecondhand.ui.my.modify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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

public class UpdateNameActivity extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);


        Toolbar toolbar = findViewById(R.id.update_name_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent=getIntent();
        final String name=intent.getStringExtra("name");
        final int id=intent.getIntExtra("id",0);

        final EditText editText=findViewById(R.id.update_name_name);
        Button button=findViewById(R.id.update_name_finish);

        if (name!=null){
            editText.setText(name);

        }
        showSoftInputFromWindow(editText);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String editedName=editText.getText().toString();

                //Check whether valid
                if (editedName.length()>20)
                {
                    Toasty.error(getBaseContext(),"名字太长，请重试！",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (editedName.equals(name)){
                        return;
                    }

                    //save to database
                    RequestQueue requestQueue= Volley.newRequestQueue(getBaseContext());
                    String url=getResources().getString(R.string.ip_address)+"/user/updateName?id="+id+
                            "&name="+editedName;
                    StringRequest request=new StringRequest(Request.Method.POST
                            , url
                            , new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("0")){
                                Intent data=new Intent();
                                data.putExtra("name",editedName);
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
        UpdateNameActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
