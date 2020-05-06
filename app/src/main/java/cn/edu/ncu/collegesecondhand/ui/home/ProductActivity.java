package cn.edu.ncu.collegesecondhand.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.Cart;
import cn.edu.ncu.collegesecondhand.entity.ProductDetail;
import cn.edu.ncu.collegesecondhand.ui.my.manage.ReleaseActivity;
import es.dmoral.toasty.Toasty;


//get the product id and show its detail
public class ProductActivity extends AppCompatActivity {
    public static final int CODE_BUY = 8;
    String self;


    String currentAccount;
    SharedPreferences sharedPreferences;


    Context context;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageViews;
    ImageView imageView_userAvatar;
    TextView textView_userName;
    TextView textView_srcAddress;
    TextView textView_productName;
    TextView textView_description;
    TextView textView_price;
    TextView textView_carryFee;
    TextView textView_originalPrice;
    TextView textView_time;
    ProductDetail product;
    Button button_buy_right_now;
    Button button_add_to_cart;
    TextView textView_login;

    RequestQueue requestQueue;
    int id;

    public void initComponents() {
        context = getBaseContext();
        requestQueue = Volley.newRequestQueue(context);
        imageView1 = findViewById(R.id.buy_image1);
        imageView2 = findViewById(R.id.buy_image2);
        imageView3 = findViewById(R.id.buy_image3);
        imageView4 = findViewById(R.id.buy_image4);
        imageView5 = findViewById(R.id.buy_image5);
        imageView6 = findViewById(R.id.buy_image6);
        imageView7 = findViewById(R.id.buy_image7);
        imageView8 = findViewById(R.id.buy_image8);
        imageView9 = findViewById(R.id.buy_image9);
        imageViews = new ImageView[]{imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,
                imageView7, imageView8, imageView9};

        imageView_userAvatar = findViewById(R.id.buy_userAvatar);
        textView_userName = findViewById(R.id.buy_userName);
        textView_srcAddress = findViewById(R.id.buy_srcAddress);
        textView_productName = findViewById(R.id.buy_productName);
        textView_description = findViewById(R.id.buy_description);
        textView_carryFee = findViewById(R.id.buy_carryFee);
        textView_price = findViewById(R.id.buy_price);
        textView_originalPrice = findViewById(R.id.buy_original_price);
        textView_time = findViewById(R.id.buy_time);

        button_buy_right_now = findViewById(R.id.buy_right_now);
        button_add_to_cart = findViewById(R.id.add_to_cart);
        textView_login=findViewById(R.id.buy_after_login);


        //useless
        product = (ProductDetail) getIntent().getSerializableExtra("product");

        self = getIntent().getStringExtra("self");

        sharedPreferences = getSharedPreferences("userAccount", Context.MODE_PRIVATE);

        currentAccount = sharedPreferences.getString("account", "");


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initComponents();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        if (currentAccount.equals("")){
            button_add_to_cart.setVisibility(View.GONE);
            button_buy_right_now.setVisibility(View.GONE);
            textView_login.setVisibility(View.VISIBLE);

        }
        getProduct();





        //test
       // Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();


    }

    public void getProduct() {

        String url = getResources().getString(R.string.ip_address)
                + "/product/getProductDetailById/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<ProductDetail>() {
                        }.getType();
                        final ProductDetail productDetail = gson.fromJson(response, type);
                        String[] paths = productDetail.getImages().split(";");
                        for (int i = 0; i < paths.length; i++) {
                            imageViews[i].setVisibility(View.VISIBLE);
                            Glide.with(context)
                                    .load(paths[i])
                                    .centerCrop()
                                    .into(imageViews[i]);
                        }

                        //seller avatar
                        Glide.with(context)
                                .load(productDetail.getSellerAvatar())
                                .into(imageView_userAvatar);
                        //seller name
                        textView_userName.setText(productDetail.getSellerName());
                        //product
                        textView_time.setText(productDetail.getCreatedTime());

                        textView_price.setText(productDetail.getPrice().toString());
                        textView_originalPrice.setText(productDetail.getOriginalPrice().toString());
                        textView_carryFee.setText(productDetail.getCarryFee().toString());
                        textView_srcAddress.setText(productDetail.getSourceAddress());
                        textView_productName.setText(productDetail.getName());
                        textView_description.setText(productDetail.getDescription());


                        button_add_to_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Cart cart = new Cart();
                                cart.setProductId(productDetail.getId());
                                cart.setUserId(productDetail.getSellerId());
                                String addUrl = getResources().getString(R.string.ip_address) +
                                        "/cart/insert?account=" + currentAccount + "&productId=" +
                                        cart.getProductId();
                                StringRequest addToCartRequest = new StringRequest(Request.Method.POST,
                                        addUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.contains("0")) {
                                            Toasty.success(getBaseContext(),
                                                    "加入购物车成功！\n请到购物车查看", Toast.LENGTH_SHORT).show();
                                            button_add_to_cart.setVisibility(View.GONE);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {


                                    }
                                });
                                requestQueue.add(addToCartRequest);


                            }
                        });
                        button_buy_right_now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ProductActivity.this, BuyActivity.class);
                                intent.putExtra("product", productDetail);
                                startActivityForResult(intent, CODE_BUY);

                            }
                        });


                        if (self != null) {
                            if (self.equals("true")) {
                                button_buy_right_now.setVisibility(View.GONE);
                                button_add_to_cart.setText("返回");
                                button_add_to_cart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                     /*   Toast.makeText(context, "编辑商品", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(ProductActivity.this, ReleaseActivity.class);
                                        intent.putExtra("edit","true");
                                        intent.putExtra("productDetail",productDetail);
                                        startActivity(intent);*/

                                    }
                                });
                            }




                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(context, "加载商品详情失败，请检查网络后重试！", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CODE_BUY) {
                String status = data.getStringExtra("status");
                if (status.equals("committed")) {

                    button_buy_right_now.setClickable(false);
                    button_add_to_cart.setClickable(false);
                    button_add_to_cart.setVisibility(View.GONE);
                    button_buy_right_now.setText("已购买");
                }

            }
        }
    }
}
