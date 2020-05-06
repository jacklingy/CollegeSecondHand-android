package cn.edu.ncu.collegesecondhand.ui.category;

import android.app.Application;
import android.content.Context;
import android.text.GetChars;

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
import com.bumptech.glide.util.pool.GlideTrace;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import cn.edu.ncu.collegesecondhand.entity.Category;
import cn.edu.ncu.collegesecondhand.entity.SubCategory;

public class CategoryViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private Context context;
    private MutableLiveData<List<Category>> allCategories;
    private MutableLiveData<List<SubCategory>> allSubCategories;
    private Application myapplication;
    private RequestQueue requestQueue;


    public CategoryViewModel(Application application) {
        super(application);
        this.myapplication = application;

        this.context = application.getBaseContext();
        requestQueue = Volley.newRequestQueue(context);
        allCategories = new MutableLiveData<>();
        allSubCategories=new MutableLiveData<>();
        getAllCategoriesFromServer();

    }


    private void getAllCategoriesFromServer() {
        String url = "http://192.168.2.2:9001/category/getAll";
        StringRequest categoryRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Category>>() {
                }.getType();
                List<Category> categories = gson.fromJson(response, type);
                allCategories.setValue(categories);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(categoryRequest);

    }

    public LiveData<List<Category>> getAllCategories() {
        return this.allCategories;
    }

    public void getSubFromServer(int categoryId) {
        String url = "http://192.168.2.2:9001/category/getSubByCateId/" + categoryId;
        StringRequest subCategoryRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<SubCategory>>() {
                }.getType();
                List<SubCategory> subCategories = gson.fromJson(response, type);
                allSubCategories.setValue(subCategories);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(subCategoryRequest);

    }

    public LiveData<List<SubCategory>> getSubCategoriesByCategoryId() {
        return this.allSubCategories;
    }
}
