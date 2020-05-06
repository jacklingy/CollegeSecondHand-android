package cn.edu.ncu.collegesecondhand.ui.my.modify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cn.edu.ncu.collegesecondhand.R;
import es.dmoral.toasty.Toasty;

public class UpdateGenderActivity extends AppCompatActivity {
    String checkedGender = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gender);


        Toolbar toolbar = findViewById(R.id.update_gender_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String gender = intent.getStringExtra("gender");
        final int id = intent.getIntExtra("id", 0);


        final RadioGroup radioGroup = findViewById(R.id.radio_group);
        Button button = findViewById(R.id.update_gender_finish);

        final RadioButton radioButton_male = findViewById(R.id.radio_button_male);
        final RadioButton radioButton_female = findViewById(R.id.radio_button_female);
        final RadioButton radioButton_secret = findViewById(R.id.radio_button_secret);
        if (gender != null) {
            switch (gender) {
                case "男":
                    radioButton_male.setChecked(true);
                    break;
                case "女":
                    radioButton_female.setChecked(true);
                    break;
                case "保密":
                    radioButton_secret.setChecked(true);
                    break;
            }
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedGender != null) {

                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    String url = getResources().getString(R.string.ip_address) + "/user/updateGender?id=" + id +
                            "&gender=" + checkedGender;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.contains("0")) {
                                        Toasty.success(getBaseContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                                        Intent data = new Intent();
                                        data.putExtra("gender", checkedGender);
                                        setResult(RESULT_OK, data);
                                        finish();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    queue.add(stringRequest);
                }else {
                    Toasty.info(getBaseContext(),"请选择！",Toast.LENGTH_SHORT).show();
                }

                //update to server

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_male:
                        checkedGender = "男";
                        break;
                    case R.id.radio_button_female:
                        checkedGender = "女";
                        break;
                    case R.id.radio_button_secret:
                        checkedGender = "保密";
                        break;

                }

            }
        });


    }
}
