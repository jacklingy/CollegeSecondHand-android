package cn.edu.ncu.collegesecondhand.ui.cart;

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
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.Cart;
import cn.edu.ncu.collegesecondhand.entity.CartBean;

public class CartViewModel extends AndroidViewModel {
    private Application application;
    private Context mContext;
    private MutableLiveData<List<CartBean>> allCartBeans=new MutableLiveData<>();

    public CartViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        this.mContext=application.getBaseContext();
    }
    // TODO: Implement the ViewModel

    private void getByAccount(String account){
        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        String url=getApplication().getResources().getString(R.string.ip_address)+"/cart/getAllCartBeans/"+account;
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson=new Gson();
                Type type=new TypeToken<List<CartBean>>(){}.getType();
                List<CartBean> carts=gson.fromJson(response,type);
                allCartBeans.setValue(carts);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
    public LiveData<List<CartBean>> getAll(String account){
        getByAccount(account);
        return allCartBeans;
    }

    public void delete(StringBuilder ids){

        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        String url=getApplication().getResources().getString(R.string.ip_address)
                +"/cart/delete/"+ids;
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("0")){
                    //delete success
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
      //  notifyAll();



    }

}
