package cn.edu.ncu.collegesecondhand.ui.home;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.Product;
import cn.edu.ncu.collegesecondhand.entity.ProductBean;
import cn.edu.ncu.collegesecondhand.entity.User;

public class HomeViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private static final String TAG = "HomeViewModel";
    private MutableLiveData<List<ProductBean>> allProductBeans;
    private Context context;
    private List<ProductBean> productBeans;
    private Application myapplication;
    private int currentCount = 0;//current visible product amount


    public HomeViewModel(Application application) {
        super(application);
        this.myapplication = application;
        this.context = application.getBaseContext();

        getAllProductsFromServer();
        allProductBeans = new MutableLiveData<>();
    }


    public void getAllProductsFromServer() {
        //get top 30 objects;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = context.getResources().getString(R.string.ip_address) + "/product/getAll";
        StringRequest productRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);
                try {

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ProductBean>>() {
                    }.getType();
                    productBeans = gson.fromJson(response, type);
                    allProductBeans.setValue(productBeans);
                    currentCount += 30;

                    Log.d(TAG, "onResponse: product beans string:" + productBeans.toString());
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: json: " + e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: error");
            }
        });
        requestQueue.add(productRequest);
    }

    public void addMore(int currentCount) { //skip currentCount products and get 30 more

        //get 30 more objects;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = context.getResources().getString(R.string.ip_address) + "/product/getMore/" + currentCount;
        StringRequest productRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);
                try {

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ProductBean>>() {
                    }.getType();
                    List<ProductBean> products;
                    products = gson.fromJson(response, type);
                    productBeans.addAll(products);
                    allProductBeans.setValue(productBeans);
                    //currentCount+=30;

                    Log.d(TAG, "onResponse: product beans string:" + productBeans.toString());
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: json: " + e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: error");
            }
        });
        requestQueue.add(productRequest);
    }

    public void refresh() {
        //request 30 random products;

        productBeans.clear();
        ProductBean productBean1 = new ProductBean(90, "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg;http://116.62.47.202/images/189b9e47e6484c1b97c36189e3bd7dad.jpg",
                "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg", "商品名", new BigDecimal("2799"),
                "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg", "用户名", 0, 0, 0, 0);

        ProductBean productBean2 = new ProductBean(90, "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg;http://116.62.47.202/images/189b9e47e6484c1b97c36189e3bd7dad.jpg",
                "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg", "商品名", new BigDecimal("2799"),
                "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg", "用户名", 0, 0, 0, 0);

        ProductBean productBean3 = new ProductBean(90, "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg;http://116.62.47.202/images/189b9e47e6484c1b97c36189e3bd7dad.jpg",
                "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg", "商品名", new BigDecimal("99"),
                "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg", "用户名", 1, 1, 0, 0);

        ProductBean productBean4 = new ProductBean(90, "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg;http://116.62.47.202/images/189b9e47e6484c1b97c36189e3bd7dad.jpg",
                "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg", "商品名", new BigDecimal("2799"),
                "http://116.62.47.202/images/9b730ab259ec4730ad4d922051c91eba.jpg", "用户名", 0, 0, 0, 0);


        productBeans.add(productBean1);
        productBeans.add(productBean2);
        productBeans.add(productBean3);
        productBeans.add(productBean4);
        this.allProductBeans.setValue(productBeans);


    }

    public LiveData<List<ProductBean>> getAllProducts() {
        return allProductBeans;

    }
}
