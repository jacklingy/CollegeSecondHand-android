package cn.edu.ncu.collegesecondhand.ui.my.manage.ui.main;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
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
import cn.edu.ncu.collegesecondhand.entity.Product;

public class PageViewModel extends AndroidViewModel {
    private Application application;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private String account;
    RequestQueue requestQueue;


    public PageViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.mContext = application.getBaseContext();
        sharedPreferences = super.getApplication().getSharedPreferences("userAccount", Context.MODE_PRIVATE);
        account = sharedPreferences.getString("account", "");
        requestQueue = Volley.newRequestQueue(mContext);


    }

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<Integer> mText = Transformations.map(mIndex, new Function<Integer, Integer>() {
        @Override
        public Integer apply(Integer input) {
            return input;
        }
    });
    private MutableLiveData<List<Product>> allProducts = new MutableLiveData<>();


    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<Integer> getText() {
        return mText;//用于显示当前是第几个tab
    }

    public void getReleasedProducts(int status) {
        String url = getApplication().getResources().getString(R.string.ip_address)
                + "/product/getProductsByAccount" + status + "/" + account;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Product>>() {
                        }.getType();
                        List<Product> myProducts = gson.fromJson(response, type);
                        allProducts.setValue(myProducts);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }

    public LiveData<List<Product>> getAllProducts(int status) {
        getReleasedProducts(status);
        return allProducts;
    }


    public void deleteById(int id) {
        String url = getApplication().getResources().getString(R.string.ip_address)
                + "/product/deleteById/" + id;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("0")) {
                    //delete success;
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