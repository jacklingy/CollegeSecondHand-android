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

public class UpdateCollegeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_college);

        Toolbar toolbar = findViewById(R.id.update_college_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        final String college = intent.getStringExtra("college");
        final int id = intent.getIntExtra("id", 0);

        final EditText editText = findViewById(R.id.update_college_college);
        Button button = findViewById(R.id.update_college_finish);

        if (college != null) {
            editText.setText(college);

        }
        showSoftInputFromWindow(editText);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String editedCollege = editText.getText().toString();

                //Check whether valid

                if (editedCollege.equals(college)) {
                    return;
                }
                else {

                    //save to database
                    RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                    String url = getResources().getString(R.string.ip_address) + "/user/updateCollege?id=" + id +
                            "&college=" + editedCollege;
                    StringRequest request = new StringRequest(Request.Method.POST
                            , url
                            , new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("0")) {
                                Intent data = new Intent();
                                data.putExtra("college", editedCollege);
                                Toasty.success(getBaseContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK, data);
                                finish();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(getBaseContext(), "请检查网络！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(request);


                }
            }
        });
    }


    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        UpdateCollegeActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}