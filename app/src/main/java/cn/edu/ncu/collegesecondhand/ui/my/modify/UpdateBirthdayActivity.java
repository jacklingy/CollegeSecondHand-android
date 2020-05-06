package cn.edu.ncu.collegesecondhand.ui.my.modify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

public class UpdateBirthdayActivity extends AppCompatActivity {
    String pickedDate = null;
    String pickedDate2 = null;

    DatePicker datePicker;
    Button button;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_birthday);

        Toolbar toolbar = findViewById(R.id.update_birthday_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        datePicker = findViewById(R.id.update_birthday_date_picker);
        button = findViewById(R.id.update_birthday_finish);
        textView = findViewById(R.id.update_birthday_text);



        Intent intent=getIntent();
        final int id=intent.getIntExtra("id",0);
        final String birthday=intent.getStringExtra("birthday");

        if (birthday!=null) {
            String[] birthdays = birthday.split("-");
            int[] births = new int[birthdays.length];


            for (int i = 0; i < birthdays.length; i++) {
                births[i] = Integer.parseInt(birthdays[i]);
            }
            String text=births[0]+"年"+(births[1])+"月"+births[2]+"日";
            textView.setText(text);
            datePicker.updateDate(births[0],births[1]-1,births[2]);



        }else
        {
            textView.setText("");
        }




        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日";
                pickedDate2=date;

                pickedDate=year+"-"+(monthOfYear+1)+"-"+(dayOfMonth);
                textView.setText(date);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toasty.info(getBaseContext(), pickedDate, Toast.LENGTH_SHORT).show();

                if (pickedDate!=null){

                    RequestQueue requestQueue= Volley.newRequestQueue(getBaseContext());
                    String url = getResources().getString(R.string.ip_address) + "/user/updateBirthday?id="
                            + id +
                            "&birthday=" + pickedDate;
                   // Toasty.info(getBaseContext(),pickedDate,Toast.LENGTH_SHORT).show();
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("0")) {
                                Intent data = new Intent();

                                data.putExtra("birthday", pickedDate2);
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
}
