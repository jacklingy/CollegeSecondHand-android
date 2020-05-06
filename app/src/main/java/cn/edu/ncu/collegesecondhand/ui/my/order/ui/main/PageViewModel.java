package cn.edu.ncu.collegesecondhand.ui.my.order.ui.main;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.Order;
import cn.edu.ncu.collegesecondhand.entity.OrderBean;

public class PageViewModel extends AndroidViewModel {
    private Application application;
    private Context mContext;
    RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private String account;


    public PageViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.mContext = application.getBaseContext();
        requestQueue = Volley.newRequestQueue(mContext);
        sharedPreferences = super.getApplication().getSharedPreferences("userAccount", Context.MODE_PRIVATE);
        account = sharedPreferences.getString("account", "");

    }


    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return String.valueOf(input);
        }
    });

    private MutableLiveData<List<OrderBean>> allOrderBeans = new MutableLiveData<>();


    //test method
    private LiveData<List<String>> mStrings = Transformations.map(mIndex, new Function<Integer, List<String>>() {
        @Override
        public List<String> apply(Integer input) {

            //此方法是不同的index返回不同的数据；
            //

            //input =1的售后返回全是1的item
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                strings.add("this is item " + input);
            }
            return strings;
        }
    });


    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<String>> getOrders() {
        return mStrings;

    }

    private void getAllFromServer(final int index) {
        String url = mContext.getResources().getString(R.string.ip_address) +
                "/order/getAllOrdersByAccount/" + account;
        StringRequest allRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<OrderBean> orderBeans;
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<OrderBean>>() {
                        }.getType();
                        orderBeans = gson.fromJson(response, type);


                        List<OrderBean> orderBeans0 = new ArrayList<>();
                        for (OrderBean orderBean : orderBeans) {
                            if (orderBean.getStatus() == 0) {
                                orderBeans0.add(orderBean);
                            }
                        }

                        List<OrderBean> orderBeans1 = new ArrayList<>();
                        for (OrderBean orderBean : orderBeans) {
                            if (orderBean.getStatus() == 1) {
                                orderBeans1.add(orderBean);
                            }
                        }

                        List<OrderBean> orderBeans2 = new ArrayList<>();
                        for (OrderBean orderBean : orderBeans) {
                            if (orderBean.getStatus() == 2) {
                                orderBeans2.add(orderBean);
                            }
                        }
                        List<OrderBean> orderBeans3 = new ArrayList<>();
                        for (OrderBean orderBean : orderBeans) {
                            if (orderBean.getStatus() == 3||orderBean.getStatus()==4) {
                                orderBeans3.add(orderBean);
                            }
                        }
                        switch (index) {
                            case 0:
                                allOrderBeans.setValue(orderBeans);
                                break;
                            case 1:
                                allOrderBeans.setValue(orderBeans0);
                                break;
                            case 2:
                                allOrderBeans.setValue(orderBeans1);
                                break;
                            case 3:
                                allOrderBeans.setValue(orderBeans2);
                                break;
                            case 4:
                                allOrderBeans.setValue(orderBeans3);

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(allRequest);
    }

    /*private LiveData<List<Order>> mAllOrders = Transformations.map(mIndex, new Function<Integer, Void>() {
        @Override
        public void apply(final Integer input) {
            String url = mContext.getResources().getString(R.string.ip_address) +
                    "/order/getAllOrdersByAccount/" + account;
            StringRequest allRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            switch (input) {
                                case 1:
                                    List<Order> orders;
                                    Gson gson = new Gson();
                                    Type type = new TypeToken<List<Order>>() {
                                    }.getType();
                                    orders = gson.fromJson(response, type);
                                    allOrders.setValue(orders);

                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(allRequest);


        }
    });*/

    public LiveData<List<OrderBean>> get_all(int index) {
        getAllFromServer(index);
        return allOrderBeans;

    }

}