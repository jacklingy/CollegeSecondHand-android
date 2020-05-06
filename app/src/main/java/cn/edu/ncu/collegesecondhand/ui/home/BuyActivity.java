package cn.edu.ncu.collegesecondhand.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.Address;
import cn.edu.ncu.collegesecondhand.entity.CartBean;
import cn.edu.ncu.collegesecondhand.entity.Order;
import cn.edu.ncu.collegesecondhand.entity.Product;
import cn.edu.ncu.collegesecondhand.entity.ProductDetail;
import es.dmoral.toasty.Toasty;

public class BuyActivity extends AppCompatActivity {
    public static final int ADD_ADDRESS = 20;
    public static final int SELECT_ADDRESS = 21;
    public static final int MODE_SINGLE = 0;
    public static final int MODE_MULTIPLE = 1;

    private Address finalAddress = null;


    SharedPreferences sharedPreferences;
    private final static int ADDRESS_REQUEST = 12;

    Order order = new Order();

    ProductDetail productDetail;
    List<Integer> ids = new ArrayList<>();
    List<Integer> productIds = new ArrayList<>();

    List<String> images = new ArrayList<>();


    List<Address> addressList = new ArrayList<>();
    Context context;
    LinearLayout layout_modifyAddress;
    LinearLayout layout_addAddress;
    LinearLayout layout_payment;
    Button button_commit;

    int payment = -1;
    int currentAddressId;
    int addressId;

    TextView textView_buyerName;//real name, got from buyer's address information;
    TextView textView_buyerPhone;
    TextView textView_buyerAddress;


    //seller
    ImageView imageView_sellerAvatar;
    TextView textView_sellerName;

    //product

    ImageView imageView_product;
    TextView textView_productName;
    TextView textView_productPrice;


    TextView textView_totalPrice;
    TextView textView_carryFee;
    TextView textView_sum;

    String currentAccount;

    //payment

    TextView textView_selectPayment;

    boolean isAddressSet = false;
    boolean isPaymentSet = false;

    //address add or modify


    LinearLayout layout_single;
    LinearLayout layout_multiple;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    TextView textView_total;
    TextView test;
    RequestQueue requestQueue;


    private void darkenBackground(Float backgroundColor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = backgroundColor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }


    @SuppressLint("SetTextI18n")
    @SuppressWarnings("unchecked")

    public void initComponents() {

        sharedPreferences = getSharedPreferences("userAccount", Context.MODE_PRIVATE);

        currentAccount = sharedPreferences.getString("account", "");

        textView_selectPayment = findViewById(R.id.order_select_payment);
        layout_payment = findViewById(R.id.order_payment);

        layout_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkenBackground(0.7f);
                showPaymentPop();


            }
        });


        layout_modifyAddress = findViewById(R.id.order_modify_address);
        layout_addAddress = findViewById(R.id.order_add_address);
        layout_payment = findViewById(R.id.order_payment);
        button_commit = findViewById(R.id.order_commit);//jump to pay;

        textView_buyerName = findViewById(R.id.buyer_name);// now replace the receiver name with its user name;
        textView_buyerPhone = findViewById(R.id.buyer_phone);
        textView_buyerAddress = findViewById(R.id.buyer_address);

        imageView_sellerAvatar = findViewById(R.id.order_seller_avatar);
        textView_sellerName = findViewById(R.id.order_seller_name);


        textView_sum = findViewById(R.id.order_sum);

        textView_totalPrice = findViewById(R.id.order_total_price);
        textView_carryFee = findViewById(R.id.order_carry_fee);


        imageView_product = findViewById(R.id.order_product_cover);
        textView_productName = findViewById(R.id.order_product_name);
        textView_productPrice = findViewById(R.id.order_product_price);

        imageView1 = findViewById(R.id.m_img1);
        imageView2 = findViewById(R.id.m_img2);
        imageView3 = findViewById(R.id.m_img3);
        imageView4 = findViewById(R.id.m_img4);

        textView_total = findViewById(R.id.m_total);

        layout_single = findViewById(R.id.single_product);
        layout_multiple = findViewById(R.id.multiple_products);

        test = findViewById(R.id.buy_test);


        context = getBaseContext();

        requestQueue = Volley.newRequestQueue(context);


        productDetail = (ProductDetail) getIntent().getSerializableExtra("product");

        if (productDetail != null) {
            String path = productDetail.getImages().substring(0, productDetail.getImages().indexOf(";"));

            Glide.with(context).load(path).into(imageView_product);
            textView_productName.setText(productDetail.getName());
            textView_productPrice.setText(productDetail.getPrice() + "");

            textView_totalPrice.setText(productDetail.getPrice() + "");
            textView_carryFee.setText(productDetail.getCarryFee() + "");
            textView_sum.setText((productDetail.getCarryFee().add(productDetail.getPrice())) + "");

            button_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //check payment
                    if (payment == -1) {

                        Toasty.info(context, "请选择支付方式！", Toasty.LENGTH_SHORT).show();
                        return;
                    }
                    if (finalAddress==null){
                        Toasty.info(context, "请选择地址！", Toasty.LENGTH_SHORT).show();
                        return;

                    }

                    int productId = productDetail.getId();
                    int sellerId = productDetail.getSellerId();
                    int addressId = finalAddress.getId();
                    String price = productDetail.getPrice().toString();
                    String carryFee = productDetail.getCarryFee().toString();
                    String totalPrice = productDetail.getPrice().add(productDetail.getCarryFee()).toString();
                    int status = 0;
                    String userAccount = currentAccount;
                    int myPayment = payment;

                    String res = "product id: " + productId + "\nsellerId: " + sellerId
                            + "\naddressId: " + addressId + "\nprice: " + price + "\ncarry: " + carryFee + "\ntotalPrice: " + totalPrice +
                            "\nstatus: " + status + "\nuserAccount: " + userAccount + "\npayment: " + myPayment;
                    test.setText(res);


                    String url = getResources().getString(R.string.ip_address)
                            + "/order/insert?productId=" + productId +
                            "&sellerId=" + sellerId +
                            "&addressId=" + addressId +
                            "&price=" + price +
                            "&carryFee=" + carryFee +
                            "&totalPrice=" + totalPrice +
                            "&status=" + status +
                            "&userAccount=" + userAccount +
                            "&payment=" + payment;

                    StringRequest insertRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.contains("0")) {
                                Toasty.success(context, "提交成功！\n请到订单详情里支付！", Toasty.LENGTH_SHORT).show();
                                Intent data = new Intent();
                                data.putExtra("status", "committed");
                                setResult(RESULT_OK, data);
                                finish();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(context, "检查网络！", Toasty.LENGTH_SHORT).show();

                        }
                    });

                    requestQueue.add(insertRequest);


                }
            });

        }

        /**/


        ids = (List<Integer>) getIntent().getSerializableExtra("ids");
        productIds=(List<Integer>)getIntent().getSerializableExtra("productIds");
        images = (List<String>) getIntent().getSerializableExtra("images");
        String totalPrice = getIntent().getStringExtra("totalPrice");
        String totalCarry = getIntent().getStringExtra("totalCarry");


        ImageView[] imageViews;
        imageViews = new ImageView[]{imageView1, imageView2, imageView3, imageView4};


        //images ！= null
        if (images != null) {
            final StringBuilder myIds = new StringBuilder();
            final StringBuilder myProductIds = new StringBuilder();
            for (int i : ids) {
                myIds.append(i).append("-");
            }
            for (int i:productIds){
                myProductIds.append(i).append("-");
            }


            button_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //check payment
                    if (payment == -1) {

                        Toasty.info(context, "请选择支付方式！", Toasty.LENGTH_SHORT).show();
                        return;
                    }


                /*    int productId = productDetail.getId();
                    int sellerId = productDetail.getSellerId();*/

                    if (finalAddress==null){
                        Toasty.info(context, "请选择收货地址！", Toasty.LENGTH_SHORT).show();
                        return;
                    }else {
                         addressId = finalAddress.getId();
                    }
                   /* String price = productDetail.getPrice().toString();
                    String carryFee = productDetail.getCarryFee().toString();
                    String totalPrice = productDetail.getPrice().add(productDetail.getCarryFee()).toString();
                    int status = 0;*/
                    String userAccount = currentAccount;
                    int myPayment = payment;
/*
                    String res = "product id: " + productId + "\nsellerId: " + sellerId
                            + "\naddressId: " + addressId + "\nprice: " + price + "\ncarry: " + carryFee + "\ntotalPrice: " + totalPrice +
                            "\nstatus: " + status + "\nuserAccount: " + userAccount + "\npayment: " + myPayment;*/
                    //test.setText(res);


                    String url = getResources().getString(R.string.ip_address)
                            + "/order/insertMultiple?ids=" + myIds +//product id
                            "&addressId=" + addressId +
                            "&productIds="+myProductIds+
                            "&userAccount=" + userAccount +
                            "&payment=" + myPayment;

                    StringRequest insertRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.contains("0")) {
                                Toasty.success(context, "提交成功！\n请到订单详情里支付！", Toasty.LENGTH_SHORT).show();
                                Intent data = new Intent();
                                data.putExtra("status", "committed");
                                setResult(RESULT_OK, data);
                                finish();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(context, "检查网络！\n"+error.toString(), Toasty.LENGTH_SHORT).show();

                        }
                    });

                    requestQueue.add(insertRequest);


                }
            });


            layout_single.setVisibility(View.GONE);
            if (totalPrice != null && totalCarry != null) {
                textView_totalPrice.setText(totalPrice);
                textView_carryFee.setText(totalCarry);

                textView_sum.setText(new BigDecimal(totalPrice).add(new BigDecimal(totalCarry)).toString());
            }

            if (images.size() <= 4) {
                for (int i = 0; i < images.size(); i++) {
                    imageViews[i].setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(images.get(i))
                            .into(imageViews[i]);
                }

                textView_total.setText("共" + images.size() + "件商品");
            } else {
                for (int i = 0; i < 4; i++) {
                    imageViews[i].setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(images.get(i))
                            .into(imageViews[i]);
                }

                textView_total.setText("···  共" + images.size() + "件商品");

            }
        } else {
            //images null

            layout_multiple.setVisibility(View.GONE);

        }


        //load product


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        initComponents();
        showAddress();


    }

    public void showPaymentPop() {


        //popup的view
        View contentView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.popup_choose_payment, null);


        ImageView image_close = contentView.findViewById(R.id.order_img_close_cate);


        // final EditText editText=contentView.findViewById(R.id.edit);

        //  Button button_commit=contentView.findViewById(R.id.button_commit);

        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        RadioGroup radioGroup = contentView.findViewById(R.id.order_radio_group);
        RadioButton radioButton_platform = contentView.findViewById(R.id.order_radio_platform);

        RadioButton radioButton_alipay = contentView.findViewById(R.id.order_radio_alipay);
        RadioButton radioButton_wechat = contentView.findViewById(R.id.order_radio_wechat);


        // radioButton_platform.setChecked(true);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.order_radio_alipay:
                        textView_selectPayment.setText("支付宝");
                        isPaymentSet = true;
                        payment = 1;
                        popupWindow.dismiss();
                        break;
                    case R.id.order_radio_wechat:
                        textView_selectPayment.setText("微信");
                        isPaymentSet = true;
                        payment = 2;
                        popupWindow.dismiss();
                        break;
                    case R.id.order_radio_platform:
                        textView_selectPayment.setText("平台积分");
                        isPaymentSet = true;
                        payment = 0;
                        popupWindow.dismiss();
                        break;

                }
            }
        });


        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        popupWindow.setContentView(contentView);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_background));

        View rootView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.activity_buy, null);

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_animation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                darkenBackground(1f);
            }
        });

        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    public void showAddress() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = getResources().getString(R.string.ip_address) + "/address/getAddressesByAccount/" + currentAccount;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


                Gson gson = new Gson();
                Type type = new TypeToken<List<Address>>() {
                }.getType();
                final List<Address> addresses = gson.fromJson(response, type);

                if (addresses.size() == 0) {
                    layout_addAddress.setVisibility(View.VISIBLE);
                    layout_modifyAddress.setVisibility(View.GONE);
                    layout_addAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BuyActivity.this, AddressActivity.class);
                            //  intent.putExtra("addressId", currentAddressId);
                            intent.putExtra("userAccount", currentAccount);

                            startActivityForResult(intent, ADD_ADDRESS);

                        }
                    });


                } else {
                    //address list not null
                    layout_addAddress.setVisibility(View.GONE);
                    layout_modifyAddress.setVisibility(View.VISIBLE);

                    boolean hasDefault = false;
                    for (Address address : addresses) {
                        if (address.getIsDefault() == 1) {
                            hasDefault = true;

                            //有默认地址

                            finalAddress = address;
                           /* currentAddressId = address.getId();
                            textView_buyerName.setText(address.getName());
                            textView_buyerPhone.setText(address.getPhone());
                            textView_buyerAddress.setText(address.getAddress());
                            layout_modifyAddress.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(BuyActivity.this, AddressActivity.class);
                                    intent.putExtra("currentAddressId", currentAddressId);
                                    //  Toast.makeText(context, "默认地址时 id 为："+currentAddressId, Toast.LENGTH_SHORT).show();

                                    intent.putExtra("userAccount", currentAccount);

                                    startActivityForResult(intent, SELECT_ADDRESS);
                                }
                            });*/


                        }
                        //
                    }
                    //没有默认地址，取第一个
                    if (!hasDefault) {
                        finalAddress = addresses.get(0);
                    }

                    textView_buyerName.setText(finalAddress.getName());
                    textView_buyerPhone.setText(finalAddress.getPhone());
                    textView_buyerAddress.setText(finalAddress.getAddress());
                    currentAddressId = finalAddress.getId();


                    layout_modifyAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BuyActivity.this, AddressActivity.class);
                            intent.putExtra("id", currentAddressId);
                            intent.putExtra("userAccount", currentAccount);

                            startActivityForResult(intent, SELECT_ADDRESS);
                        }
                    });


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_ADDRESS) {

                Address selectAddress = (Address) data.getSerializableExtra("selectedAddress");
                //update ui
                if (selectAddress != null) {
                    //  Toast.makeText(context,"not null",Toast.LENGTH_SHORT).show();


                    textView_buyerName.setText(selectAddress.getName());
                    textView_buyerAddress.setText(selectAddress.getAddress());
                    textView_buyerPhone.setText(selectAddress.getPhone());
                    finalAddress = selectAddress;

                } else {
                    Toast.makeText(context, "selected null", Toast.LENGTH_SHORT).show();
                    //todo
                    //re select address!
                }
            } else if (requestCode == ADD_ADDRESS) {
                Address selectAddress = (Address) data.getSerializableExtra("selectedAddress");

                if (selectAddress != null) {
                    layout_modifyAddress.setVisibility(View.VISIBLE);
                    layout_addAddress.setVisibility(View.GONE);
                    textView_buyerName.setText(selectAddress.getName());
                    textView_buyerAddress.setText(selectAddress.getAddress());
                    textView_buyerPhone.setText(selectAddress.getPhone());
                    finalAddress = selectAddress;

                }


            }
        }
    }
}
