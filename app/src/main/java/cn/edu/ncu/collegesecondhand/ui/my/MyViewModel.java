package cn.edu.ncu.collegesecondhand.ui.my;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.User;

public class MyViewModel extends AndroidViewModel {
    private Application application;
    private Context context;
    private MutableLiveData<User> userMutableLiveData;
    private RequestQueue queue;


    public MyViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        this.context=application.getBaseContext();
        queue= Volley.newRequestQueue(context);
        this.userMutableLiveData=new MutableLiveData<>();



    }
    // TODO: Implement the ViewModel


    public void getUserByAccount(String account){
        String url= context.getResources().getString(R.string.ip_address) + "/user/getUserByAccount?account="
                +account;
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                User user;
                Gson gson=new Gson();
                Type type=new TypeToken<User>(){}.getType();
                user=gson.fromJson(response,type);
                userMutableLiveData.setValue(user);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);


    }
    public LiveData<User> getUser(){
        return userMutableLiveData;
    }
}
