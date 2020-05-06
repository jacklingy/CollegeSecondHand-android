package cn.edu.ncu.collegesecondhand.ui.my.order;

import android.Manifest;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.OrderBean;
import cn.edu.ncu.collegesecondhand.entity.User;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.view.View.GONE;

public class OrderDetailActivity extends AppCompatActivity {
    private static final String TAG = "OrderDetailActivity";


    //receiver
    private TextView textView_userName;
    private TextView textView_userPhone;
    private TextView textView_userAddress;

    //seller
    private CircleImageView sellerAvatar;
    private TextView sellerName;
    //product
    private ImageView productCover;
    private TextView productName;
    private TextView productPrice;
    //order price
    private TextView textView_orderPrice;
    private TextView textView_orderCarryFee;
    private TextView textView_orderTotalPrice;
    //order information
    private TextView textView_orderNumber;
    private TextView textView_orderPayment;
    private TextView textView_orderCreatedTime;
    private TextView textView_orderPayTime;
    private TextView textView_orderFinishTime;
    //buttons
    private Button button_cancel;
    private Button button_pay;
    private Button button_confirm;
    private Button button_refund;

    RequestQueue requestQueue;
    String paymentStr = "";
    SharedPreferences sharedPreferences;
    String userAccount;

    private LinearLayout layout_phone;
    private TextView text_phone;

    OrderBean orderBean;

    public void init() {

        textView_userName = findViewById(R.id.order_detail_user_name);
        textView_userPhone = findViewById(R.id.order_detail_user_phone);
        textView_userAddress = findViewById(R.id.order_detail_user_address);

        sellerAvatar = findViewById(R.id.order_detail_seller_avatar);
        sellerName = findViewById(R.id.order_detail_seller_name);

        productCover = findViewById(R.id.order_detail_product_cover);
        productName = findViewById(R.id.order_detail_product_name);
        productPrice = findViewById(R.id.order_detail_product_price);

        textView_orderPrice = findViewById(R.id.order_detail_order_price);
        textView_orderCarryFee = findViewById(R.id.order_detail_order_carry_fee);
        textView_orderTotalPrice = findViewById(R.id.order_detail_order_total_price);

        textView_orderNumber = findViewById(R.id.order_detail_number);
        textView_orderPayment = findViewById(R.id.order_detail_payment);
        textView_orderCreatedTime = findViewById(R.id.order_detail_created_time);
        textView_orderPayTime = findViewById(R.id.order_detail_pay_time);
        textView_orderFinishTime = findViewById(R.id.order_detail_finish_time);

        button_cancel = findViewById(R.id.order_detail_button_cancel);
        button_pay = findViewById(R.id.order_detail_button_pay);
        button_confirm = findViewById(R.id.order_detail_button_confirm);
        button_refund = findViewById(R.id.order_detail_button_refund);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sharedPreferences = getSharedPreferences("userAccount", MODE_PRIVATE);
        userAccount = sharedPreferences.getString("account", "");

        layout_phone = findViewById(R.id.layout_phone);
        text_phone = findViewById(R.id.text_phone);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //order status


        String title = null;

        Intent intent = getIntent();
        orderBean = (OrderBean) intent.getSerializableExtra("orderBean");

        Log.e(TAG, "onCreate: " + JSON.toJSONString(orderBean));
       // Toast.makeText(this, JSON.toJSONString(orderBean), Toast.LENGTH_SHORT).show();

        switch (orderBean.getStatus()) {
            case 0:
                title = "待付款！";
                break;
            case 1:
                title = "待收货!";
                break;
            case 2:
                title = "已完成！";
                break;
            case 3:
                title = "申请退款！";
                break;
            case 4:
                title = "退款完成！";
                break;
        }
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);


        //set text and images
        //address
        if (orderBean.getAddressName() != null) {
            textView_userName.setText(orderBean.getAddressName());
        }

        if (orderBean.getAddressPhone() != null) {
            textView_userPhone.setText(orderBean.getAddressPhone());
        }

        if (orderBean.getAddressAddress() != null) {
            textView_userAddress.setText(orderBean.getAddressAddress());
        }
        //seller
        if (orderBean.getSellerAvatar() != null) {
            Glide.with(this)
                    .load(orderBean.getSellerAvatar())
                    .into(sellerAvatar);
        }
        if (orderBean.getSellerName() != null) {
            sellerName.setText(orderBean.getSellerName());
        }

        //product
        if (orderBean.getProductName() != null) {
            productName.setText(orderBean.getProductName());
        }
        if (orderBean.getProductCover() != null) {
            Glide.with(this).load(orderBean.getProductCover().substring(0,
                    orderBean.getProductCover().indexOf(";"))).into(productCover);
        }
        if (orderBean.getProductPrice() != null) {
            productPrice.setText(orderBean.getProductPrice() + "");
            textView_orderPrice.setText(orderBean.getProductPrice() + "");
        }
        if (orderBean.getProductCarryFee() != null) {
            textView_orderCarryFee.setText(orderBean.getProductCarryFee() + "");
        }
        if (orderBean.getProductPrice() != null && orderBean.getProductCarryFee() != null) {
            textView_orderTotalPrice.setText(orderBean.getProductPrice().add(orderBean.getProductCarryFee()) + "");
        }
        if (orderBean.getOrderNumber() != null) {
            textView_orderNumber.setText(orderBean.getOrderNumber());
        }

        //payment
        try {
            String payment1 = "平台积分";
            String payment2 = "支付宝";
            String payment3 = "微信";
            switch (orderBean.getPayment()) {
                case 0:
                    paymentStr = "平台积分";
                    textView_orderPayment.setText(payment1);
                    break;
                case 1:
                    paymentStr = "支付宝";
                    textView_orderPayment.setText(payment2);
                    break;
                case 2:
                    paymentStr = "微信";
                    textView_orderPayment.setText(payment3);
                    break;
            }
        } catch (Exception e) {
            textView_orderPayment.setText("获取支付方式错误！" + e.toString());
        }
        if (orderBean.getCreatedTime() != null) {
            textView_orderCreatedTime.setText(orderBean.getCreatedTime());
        }
        if (orderBean.getPayTime() != null) {
            textView_orderPayTime.setText(orderBean.getPayTime());
        }
        if (orderBean.getFinishTime() != null) {
            textView_orderFinishTime.setText(orderBean.getFinishTime());
        }

        //buttons

        switch (orderBean.getStatus()) {
            case 0://提交 未付款，显示 pay,cancel
                button_confirm.setVisibility(GONE);
                button_refund.setVisibility(GONE);
                button_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(OrderDetailActivity.this).setIcon(R.drawable.ic_error_outline_black_24dp).setTitle("支付")
                                .setMessage("将使用" + paymentStr + "支付\n￥" + orderBean.getProductPrice().add(orderBean.getProductCarryFee()) +
                                        ",是否确认？")
                                .setPositiveButton("确认支付", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String url = getResources().getString(R.string.ip_address)
                                                + "/order/pay2?orderId=" + orderBean.getId();
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                                url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.contains("0")) {

                                                            Toasty.info(getBaseContext(), "支付成功！", Toast.LENGTH_SHORT).show();


                                                            Intent data = new Intent();
                                                            data.putExtra("id", orderBean.getId());
                                                            setResult(RESULT_OK, data);
                                                            finish();

                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toasty.error(getBaseContext(), "网络错误" + error.toString(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                        requestQueue.add(stringRequest);


                                        //ToDo: 你想做的事情
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //ToDo: 你想做的事情
                                        dialogInterface.dismiss();

                                    }
                                });
                        builder.create().show();

                        //        Toasty.info(getBaseContext(), "button pay clicked!", Toast.LENGTH_SHORT).show();


                    }
                });
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(OrderDetailActivity.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp).setTitle("取消订单")
                                .setMessage("取消将删除此订单，是否取消该订单？").setPositiveButton("取消该订单", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String url = getResources().getString(R.string.ip_address)
                                                + "/order/delete/" + orderBean.getId();
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                                url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.contains("0")) {

                                                            Toasty.info(getBaseContext(), "取消订单成功！", Toast.LENGTH_SHORT).show();
                                                            Intent data = new Intent();
                                                            data.putExtra("id", orderBean.getId());
                                                            setResult(RESULT_OK, data);
                                                            finish();

                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        });
                                        requestQueue.add(stringRequest);


                                        //ToDo: 你想做的事情
                                    }
                                }).setNegativeButton("不取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //ToDo: 你想做的事情
                                        dialogInterface.dismiss();

                                    }
                                });
                        builder.create().show();
                    }
                });
                break;
            case 1://已付款，等待收货
                button_pay.setVisibility(GONE);
                button_refund.setVisibility(GONE);
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toasty.info(getBaseContext(), "button cancel clicked!", Toast.LENGTH_SHORT).show();


                    }
                });
                button_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toasty.info(getBaseContext(), "button confirm clicked!", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(OrderDetailActivity.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("确认收货")
                                .setMessage("如果您已收到货物，请确认收货")
                                .setPositiveButton("确认收货", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        String url = getResources().getString(R.string.ip_address)
                                                + "/order/pay?orderId=" + orderBean.getId() +
                                                "&userAccount=" + userAccount +
                                                "&sellerId=" + orderBean.getSellerId() +
                                                "&money=" + orderBean.getProductPrice().add(orderBean.getProductCarryFee());
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                                url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.contains("0")) {

                                                            Toasty.info(getBaseContext(), "确认成功！\n卖家已收到您的货款", Toast.LENGTH_SHORT).show();
                                                            Intent data = new Intent();
                                                            data.putExtra("id", orderBean.getId());
                                                            setResult(RESULT_OK, data);
                                                            finish();

                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        });
                                        requestQueue.add(stringRequest);


                                        //ToDo: 你想做的事情
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //ToDo: 你想做的事情
                                        dialogInterface.dismiss();

                                    }
                                });
                        builder.create().show();


                    }
                });

                button_refund.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(OrderDetailActivity.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp).setTitle("取消订单")
                                .setMessage("取消将删除此订单，是否取消该订单？").setPositiveButton("取消该订单", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String url = getResources().getString(R.string.ip_address)
                                                + "/order/delete/" + orderBean.getId();
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                                url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.contains("0")) {

                                                            Toasty.info(getBaseContext(), "取消订单成功！", Toast.LENGTH_SHORT).show();
                                                            Intent data = new Intent();
                                                            data.putExtra("id", orderBean.getId());
                                                            setResult(RESULT_OK, data);
                                                            finish();

                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        });
                                        requestQueue.add(stringRequest);


                                        //ToDo: 你想做的事情
                                    }
                                }).setNegativeButton("不取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //ToDo: 你想做的事情
                                        dialogInterface.dismiss();

                                    }
                                });
                        builder.create().show();


                    }
                });

                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(OrderDetailActivity.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("取消订单")
                                .setMessage("卖家可能已经发货，请与卖家进行沟通后再取消订单")
                                .setPositiveButton("与卖家沟通", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        layout_phone.setVisibility(View.VISIBLE);
                                        button_refund.setVisibility(View.VISIBLE);
                                        button_refund.setText("确认取消");


                                        String url = getResources().getString(R.string.ip_address)
                                                + "/user/getUserById?id=" + orderBean.getSellerId();
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                                url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Gson gson = new Gson();
                                                        Type type = new TypeToken<User>() {
                                                        }.getType();

                                                        User seller = gson.fromJson(response, type);

                                                        if (seller.getPhone() == null || seller.getPhone().equals("")) {
                                                            text_phone.setText("卖家未设置电话！请自行取消订单！");

                                                        } else {
                                                            text_phone.setText(seller.getPhone());
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                Toasty.error(getBaseContext(), error.toString(), Toasty.LENGTH_SHORT).show();

                                            }
                                        });
                                        requestQueue.add(stringRequest);


                                        //ToDo: 你想做的事情
                                    }
                                }).setNegativeButton("不取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //ToDo: 你想做的事情
                                        dialogInterface.dismiss();

                                    }
                                });
                        builder.create().show();

                    }
                });

                break;
            case 2://已完成，不可取消，可以申请退货

                //退货：生成一个退货item 包括订单号和退货理由
                //卖家查询order表，包含自己sellerId,状态退货状态的订单id，并由此查询refund表，查看退货理由
                //可以退就修改订单状态，并退款；

                button_cancel.setVisibility(GONE);
                button_pay.setVisibility(GONE);
                button_confirm.setVisibility(GONE);
                button_refund.setText("申请退货");
                button_refund.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAddressPop();


                        //        Toasty.info(getBaseContext(), "button refund clicked!", Toast.LENGTH_SHORT).show();


                    }
                });

                /*todo
                 *  退货要单独开一个activity
                 * */
                break;
            case 3://申请退货中；
                button_pay.setVisibility(GONE);
                button_confirm.setVisibility(GONE);
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //delete the refund item
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(OrderDetailActivity.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("取消退款")
                                .setMessage("确定取消退款吗？")
                                .setPositiveButton("确定取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                        String url = getResources().getString(R.string.ip_address)
                                                + "/refund/delete?orderId=" + orderBean.getId();
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                                url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                       if (response.contains("0")){
                                                           Toasty.success(getBaseContext(),"取消成功！", Toasty.LENGTH_SHORT).show();

                                                           //finish();
                                                       }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                Toasty.error(getBaseContext(), error.toString(), Toasty.LENGTH_SHORT).show();

                                            }
                                        });
                                        requestQueue.add(stringRequest);


                                        //ToDo: 你想做的事情
                                    }
                                }).setNegativeButton("不取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //ToDo: 你想做的事情
                                        dialogInterface.dismiss();

                                    }
                                });
                        builder.create().show();





                        Toasty.info(getBaseContext(), "cancel refund!", Toast.LENGTH_SHORT).show();
                    }
                });
                button_refund.setText("退货完成");
                button_refund.setVisibility(GONE);
               /* button_refund.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Toasty.info(getBaseContext(), "确认退货", Toast.LENGTH_SHORT).show();

                    }
                });*/
                break;

            case 4://退货完成；

                button_pay.setVisibility(GONE);
                button_refund.setVisibility(GONE);
                button_cancel.setVisibility(GONE);
                button_confirm.setText("已完成");
                button_confirm.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;


        }


        // TextView textView=findViewById(R.id.order_detail_text);
        //  textView.setText("9999");



     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    private void darkenBackground(Float backgroundColor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = backgroundColor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    //手动填写发货地popup
    public void showAddressPop() {
        darkenBackground(0.7f);


        View contentView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.popup_refund_reason, null);


        ImageView image_close = contentView.findViewById(R.id.refund_img_close);
        // Button button_useLocation = contentView.findViewById(R.id.button_use_location);
        final EditText editText = contentView.findViewById(R.id.refund_reason);

        Button button_commit = contentView.findViewById(R.id.refund_button_commit);

        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        popupWindow.setContentView(contentView);

        //   popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_background));

        View rootView = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.activity_order_detail, null);

        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.pop_animation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

//
//        Glide.with(getBaseContext())
//                .load(new File("/storage/emulated/0/DCIM/Camera/IMG_20200208_142217.jpg"))
//                .into(image_close);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //  popupWindow.setFocusable(true);


        button_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    Toasty.info(getBaseContext(), "请输入退货理由！", Toasty.LENGTH_SHORT).show();
                    return;
                }


              //  Toast.makeText(OrderDetailActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();

               // popupWindow.dismiss();

                String url = getResources().getString(R.string.ip_address)
                        + "/refund/insert?orderId=" + orderBean.getId() + "&reason=" + editText.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("0")) {
                                    Toasty.success(getBaseContext(), "申请成功！", Toasty.LENGTH_SHORT).show();
                               popupWindow.dismiss();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(getBaseContext(), "网络错误", Toasty.LENGTH_SHORT).show();


                    }
                });
                requestQueue.add(stringRequest);
                //
            }
        });

        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

    }


}
