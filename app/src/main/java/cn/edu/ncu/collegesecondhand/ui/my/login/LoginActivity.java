package cn.edu.ncu.collegesecondhand.ui.my.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.util.MD5Util;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private TextView textView_signIn;
    private EditText editText_account;
    private EditText editText_password;
    private Button button_login;
    private RequestQueue queue;
    private SharedPreferences sharedPreferences_userAccount;


    private void init() {
        textView_signIn = findViewById(R.id.newRegister);
        editText_account = findViewById(R.id.login_account);
        editText_password = findViewById(R.id.login_password);
        button_login = findViewById(R.id.login_button);


        textView_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        sharedPreferences_userAccount=super.getSharedPreferences("userAccount",MODE_PRIVATE);


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = editText_account.getText().toString();
                String password = editText_password.getText().toString();

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
                    Toasty.info(getBaseContext(), "账号包含中文字符，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //密码判空
                if (password.length() < 6) {
                    Toasty.error(getBaseContext(), "密码不能少于6位，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //非法字符判断
                if (isContainChinese(password)) {
                    Toasty.info(getBaseContext(), "密码包含中文字符，请重试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //将明文密码转换成MD5字符串；
                password = MD5Util.toMD5(password);

                String url = getResources().getString(R.string.ip_address) + "/user/login?account=" + account
                        + "&password=" + password;
                final String finalPassword = password;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                if (response.contains("0")) {
                                    //注册成功，返回登录界面，给出提示
                                    Toasty.success(getBaseContext(),
                                            "登录成功！", Toast.LENGTH_SHORT).show();


                                    SharedPreferences.Editor editor = sharedPreferences_userAccount.edit();
                                    editor.putString("account", account);
                                    editor.putString("password", finalPassword);
                                    editor.apply();
                                    //Note: commit() will write it to persistent storage immediately,
                                    //while apply() will do it background;

                                    finish();

                                } else if (response.contains("1")) {
                                    Toasty.error(getBaseContext(),
                                            "密码错误请重试！", Toast.LENGTH_SHORT).show();
                                } else if (response.contains("2")) {
                                    Toasty.error(getBaseContext(),
                                            "该用户未注册，\n请注册后再登录！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(getBaseContext(), "网络连接错误,请稍后重试！：\n" +
                                error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue = Volley.newRequestQueue(getBaseContext());
                queue.add(stringRequest);

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
