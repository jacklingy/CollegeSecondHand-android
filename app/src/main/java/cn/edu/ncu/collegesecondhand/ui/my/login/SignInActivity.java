package cn.edu.ncu.collegesecondhand.ui.my.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.util.MD5Util;
import es.dmoral.toasty.Toasty;

public class SignInActivity extends AppCompatActivity {

    private EditText editText_account;
    private EditText editText_password;
    private EditText getEditText_password_confirm;
    private Button button_signIn;

    private void init() {
        editText_account = findViewById(R.id.register_account);
        editText_password = findViewById(R.id.register_password);
        button_signIn = findViewById(R.id.register_button);
        getEditText_password_confirm = findViewById(R.id.register_password_confirm);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        final RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());


        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editText_account.getText().toString();
                String originPassword = editText_password.getText().toString();
                String originPasswordConfirm = getEditText_password_confirm.getText().toString();
                String password;

                //账号密码判空
                if (account.length() < 6) {
                    Toasty.error(getBaseContext(), "账号不能少于6位，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (account.length() > 20) {
                    Toasty.error(getBaseContext(), "账号不能超过20位，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //非法字符判断
                if (isContainChinese(account)) {
                    Toasty.info(getBaseContext(), "包含中文字符，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //密码判空
                if (originPassword.length() < 6 && originPasswordConfirm.length() < 6) {
                    Toasty.error(getBaseContext(), "密码不能少于6位，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!originPassword.equals(originPasswordConfirm)) {
                    Toasty.error(getBaseContext(), "两次输入密码不一致，请重试！", Toast.LENGTH_SHORT).show();
                    return;

                }
                //非法字符判断
                if (isContainChinese(originPassword)) {
                    Toasty.info(getBaseContext(), "密码包含中文字符，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //将明文密码转换成MD5字符串；
                password = MD5Util.toMD5(originPassword);


                String url = getResources().getString(R.string.ip_address) + "/user/signIn?account=" + account
                        + "&password=" + password;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                if (response.contains("0")) {
                                    //注册成功，返回登录界面，给出提示
                                    Toasty.success(getBaseContext(),
                                            "恭喜注册成功！", Toast.LENGTH_LONG).show();
                                    // Log.d("register:    ", "success");
                                    finish();


                                } else if (response.contains("1")) {
                                    Toasty.error(getBaseContext(),
                                            "该用户未已存在，\n请直接登录或者重新注册新用户！", Toast.LENGTH_SHORT).show();
                                    // Log.d("register:    ", "Already existed!");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(getBaseContext(), "网络连接错误,请稍后重试！：\n" +
                                error.toString(), Toast.LENGTH_SHORT).show();
                        // Log.d("register:   ", "网络连接错误：" + error.toString());
                    }
                });
                requestQueue.add(stringRequest);
            }
        });

    }


    //Chinese chars;
    public static boolean isContainChinese(String str) {


        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
