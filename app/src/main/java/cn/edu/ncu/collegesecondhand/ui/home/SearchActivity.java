package cn.edu.ncu.collegesecondhand.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blcodes.views.refresh.BounceCallBack;
import com.blcodes.views.refresh.BounceLayout;
import com.blcodes.views.refresh.EventForwardingHelper;
import com.blcodes.views.refresh.NormalBounceHandler;
import com.blcodes.views.refresh.footer.DefaultFooter;
import com.blcodes.views.refresh.header.DefaultHeader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.ProductAdapter;
import cn.edu.ncu.collegesecondhand.entity.Product;
import cn.edu.ncu.collegesecondhand.entity.ProductBean;
import cn.edu.ncu.collegesecondhand.entity.ProductDetail;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    SearchView searchView;

    private BounceLayout bounceLayout;
    private RecyclerView recyclerView;

    RequestQueue searchQueue;

    ProductAdapter adapter;

    private String intentKeyword=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchQueue= Volley.newRequestQueue(getBaseContext());

        intentKeyword=getIntent().getStringExtra("keyword");
        //binding
        FrameLayout rootView = findViewById(R.id.search_frame_layout);
        bounceLayout = findViewById(R.id.search_bounce_layout);
        recyclerView = findViewById(R.id.search_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        List<ProductBean> productBeans=new ArrayList<>();
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
          //adapter=new ProductAdapter(this,productBeans);







        StaggeredGridLayoutManager staggeredGridLayoutManager=
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //recyclerView.setAdapter(adapter);




        //   bounceLayout.setHeaderView(new DefaultHeader(this),rootView);
        bounceLayout.setFooterView(new DefaultFooter(this), rootView);

        bounceLayout.setBounceHandler(new NormalBounceHandler(), recyclerView);
        bounceLayout.setEventForwardingHelper(new EventForwardingHelper() {
            @Override
            public boolean notForwarding(float downX, float downY, float moveX, float moveY) {
                return true;
            }
        });

        bounceLayout.setBounceCallBack(new BounceCallBack() {
            @Override
            public void startRefresh() {
                // Log.i(TAG, "run: 开始刷新");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> data = new ArrayList<>();
                        for (int i = 0; i < 16; i++) {
                            data.add("新文本" + i);
                        }
                        // mDatas.addAll(data);
                        // recyclerView.setAdapter(commonAdapter);

                        bounceLayout.setRefreshCompleted();

                        //    Log.i(TAG, "run: 结束刷新");
                    }
                }, 2000);
            }

            @Override
            public void startLoadingMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //load more

                        bounceLayout.setLoadingMoreCompleted();

                    }
                }, 1000);
            }
        });


        Toolbar toolbar = findViewById(R.id.search_activity_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("请输入以搜索");
        searchView.setTextDirection(SearchView.TEXT_DIRECTION_LOCALE);
        searchView.setIconified(false);//focused by default


        //listener
        if (intentKeyword!=null) {
            searchView.setQuery(intentKeyword,true);



        }



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //s是提交的搜索结果
                Log.e("CSDN_LQR", "TextSubmit : " + s);

                if (s.equals("")){

                }else {
                    String url=getResources().getString(R.string.ip_address)
                            +"/product/search?keyword="
                            +s;

                    StringRequest searchRequest=
                            new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson=new Gson();
                            Type type=new TypeToken<List<ProductBean>>(){}.getType();

                            List<ProductBean> productBeans=gson.fromJson(response,type);

                            adapter=new ProductAdapter(getBaseContext(),productBeans);
                            recyclerView.setAdapter(adapter);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) ;
                    searchQueue.add(searchRequest);

                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //s是每次变化后，框内的String
              //  Toast.makeText(SearchActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.e("CSDN_LQR", "TextChange --> " + s);
                return false;
            }
        });

       /* searchView.setIconifiedByDefault(false);
        searchView.setFocusedByDefault(true);*/

        /**
         * search view implementation detail at https://www.jianshu.com/p/7c1e78e91506
         */
        /**todo
         * 自动匹配功能之后再做；
         */
        //  searchView.setSuggestionsAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"name"}, new int[]{R.id.text1}));


        return super.onCreateOptionsMenu(menu);
    }

}
