package cn.edu.ncu.collegesecondhand.ui.cart;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blcodes.views.refresh.BounceCallBack;
import com.blcodes.views.refresh.BounceLayout;
import com.blcodes.views.refresh.EventForwardingHelper;
import com.blcodes.views.refresh.NormalBounceHandler;
import com.blcodes.views.refresh.header.DefaultHeader;

import java.io.BufferedReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.CartAdapter;
import cn.edu.ncu.collegesecondhand.adapter.interf.CartCheckListener;
import cn.edu.ncu.collegesecondhand.entity.Cart;
import cn.edu.ncu.collegesecondhand.entity.CartBean;
import cn.edu.ncu.collegesecondhand.ui.home.BuyActivity;
import es.dmoral.toasty.Toasty;

public class CartFragment extends Fragment {
    private BounceLayout bounceLayout;
    private FrameLayout frameLayout;
    private RecyclerView recyclerView;
    private Context mContext;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private List<CartBean> checkedCartBeans = new ArrayList<>();
    private String account;

    private Button button_delete;
    private Button button_commit;
    private TextView textView_sum;

    private String sumMoney;
    private BigDecimal total = new BigDecimal("0");


    private CartViewModel mViewModel;

    private CartAdapter cartAdapter = null;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userAccount", Context.MODE_PRIVATE);
        account = sharedPreferences.getString("account", "");
        button_delete = view.findViewById(R.id.cart_button_delete);
        button_commit = view.findViewById(R.id.cart_button_commit);
        textView_sum = view.findViewById(R.id.cart_sum);




     /*   Toolbar toolbar = view.findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        mContext = getContext();
        frameLayout = view.findViewById(R.id.cart_frame_layout);
        bounceLayout = view.findViewById(R.id.cart_bounce_layout);
        recyclerView = view.findViewById(R.id.cart_recycler_view);
        checkBox = view.findViewById(R.id.cart_check_all);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);

   /*     List<CartBean> cartBeans = new ArrayList<>();
        cartBeans.add(new CartBean(1, "http://116.62.47.202/images/xiaomi8.jpg", "username",
                "http://116.62.47.202/images/xiaomi8.jpg;http://116.62.47.202/images/xiaomi8.jpg",
                "http://116.62.47.202/images/xiaomi8.jpg", "小米8小米8小米8小米8小米8小米8",
                new BigDecimal("1200.00"), new BigDecimal("12.00")));
        cartBeans.add(new CartBean(2, "http://116.62.47.202/images/xiaomi8.jpg", "username",
                "http://116.62.47.202/images/xiaomi8.jpg;http://116.62.47.202/images/xiaomi8.jpg",
                "http://116.62.47.202/images/xiaomi8.jpg", "小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8小米8",
                new BigDecimal("1200.00"), new BigDecimal("12.00")));
        cartBeans.add(new CartBean(3, "http://116.62.47.202/images/xiaomi8.jpg", "username",
                "http://116.62.47.202/images/xiaomi8.jpg;http://116.62.47.202/images/xiaomi8.jpg",
                "http://116.62.47.202/images/xiaomi8.jpg", "小米8小米8小米8小米8小米8小米8",
                new BigDecimal("1200.00"), new BigDecimal("12.00")));
        cartBeans.add(new CartBean(4, "http://116.62.47.202/images/xiaomi8.jpg", "username",
                "http://116.62.47.202/images/xiaomi8.jpg;http://116.62.47.202/images/xiaomi8.jpg",
                "http://116.62.47.202/images/xiaomi8.jpg", "小米8小米8小米8小米8小米8小米8",
                new BigDecimal("1200.00"), new BigDecimal("12.00")));
*/

        //init
        bounceLayout.setHeaderView(new DefaultHeader(mContext), frameLayout);
        bounceLayout.setBounceHandler(new NormalBounceHandler(), recyclerView);
        bounceLayout.setEventForwardingHelper(new EventForwardingHelper() {
            @Override
            public boolean notForwarding(float v, float v1, float v2, float v3) {
                return true;
            }
        });

//        bounceLayout.autoRefresh();
        bounceLayout.setBounceCallBack(new BounceCallBack() {
            @Override
            public void startRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //mViewModel.refresh();
                        Toasty.info(mContext, "已刷新!", Toast.LENGTH_SHORT).show();
                        mViewModel.getAll(account).observe(getActivity(), new Observer<List<CartBean>>() {
                            @Override
                            public void onChanged(List<CartBean> cartBeans) {
                                if (cartAdapter == null) {
                                    cartAdapter = new CartAdapter(mContext, cartBeans);

                                }
                                recyclerView.setAdapter(cartAdapter);
                                cartAdapter.notifyDataSetChanged();



                       /*         cartAdapter.setCartCheckListener(new CartCheckListener() {
                                    @Override
                                    public void isChecked(CartBean cartBean) {
                                        checkedCartBeans.add(cartBean);
                                        StringBuilder res = new StringBuilder();
                                        for (CartBean c : checkedCartBeans) {
                                            res.append(c.getId() + ",");
                                            total = total.add(c.getProductPrice());
                                        }
                                        textView_sum.setText(total.toString());

                                        //  Toasty.info(mContext, "current list is: " + res, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void isNotChecked(CartBean cartBean) {
                                        checkedCartBeans.remove(cartBean);
                                        StringBuilder res = new StringBuilder();
                                        for (CartBean c : checkedCartBeans) {
                                            res.append(c.getId() + ",");
                                            total = total.subtract(c.getProductPrice());

                                        }
                                        textView_sum.setText(total.toString());

                                        //  Toasty.info(mContext, "current list is: " + res, Toast.LENGTH_SHORT).show();

                                    }
                                });*/


                          /*      checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            cartAdapter.setAllChecked(true);
                                        } else {
                                            cartAdapter.setAllChecked(false);
                                        }
                                    }
                                });*/


                            }
                        });


                        bounceLayout.setRefreshCompleted();


                    }
                }, 300);
            }

            @Override
            public void startLoadingMore() {

            }


        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*
        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
*/
        // TODO: Use the ViewModel

        mViewModel.getAll(account).observe(getActivity(), new Observer<List<CartBean>>() {
            @Override
            public void onChanged(final List<CartBean> cartBeans) {
                cartAdapter = new CartAdapter(mContext, cartBeans);
                recyclerView.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
                cartAdapter.setCartCheckListener(new CartCheckListener() {
                    @Override
                    public void isChecked(CartBean cartBean) {
                        total = new BigDecimal("0");
                        checkedCartBeans.add(cartBean);

                        StringBuilder res = new StringBuilder();
                        for (CartBean c : checkedCartBeans) {
                            res.append(c.getId() + ",");
                            total = total.add(c.getProductPrice());
                        }
                        textView_sum.setText(total.toString());

                        // Toasty.info(mContext, "current list is: " + res, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void isNotChecked(CartBean cartBean) {


                        checkedCartBeans.remove(cartBean);
                        StringBuilder res = new StringBuilder();
                        total = new BigDecimal("0");
                        for (CartBean c : checkedCartBeans) {
                            res.append(c.getId() + ",");
                            total = total.add(c.getProductPrice());

                        }
                        textView_sum.setText(total.toString());

                        // Toasty.info(mContext, "current list is: " + res, Toast.LENGTH_SHORT).show();

                    }
                });
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            cartAdapter.setAllChecked(true);


                            total = new BigDecimal("0");
                            // checkedCartBeans.clear();
                            // checkedCartBeans=cartAdapter.getCartBeans();

                            for (CartBean c : cartAdapter.getCartBeans()) {
                                // res.append(c.getId() + ",");
                                total = total.add(c.getProductPrice());
                            }
                            //      Toast.makeText(mContext, total.toString(), Toast.LENGTH_SHORT).show();
                            textView_sum.setText(total.toString());

                        } else {
                            cartAdapter.setAllChecked(false);

                          /*  for (CartBean c : checkedCartBeans) {
                                //res.append(c.getId() + ",");
                                total = total.add(c.getProductPrice());

                            }
                            textView_sum.setText("0.00");*/

                        }
                    }
                });


            }
        });

        button_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkedCartBeans.size() == 0) {
                    Toasty.info(getContext(), "请添加商品后再结算！", Toasty.LENGTH_SHORT).show();
                    return;
                }
                List<Integer> ids = new ArrayList<>();
                List<String> images = new ArrayList<>();
                List<Integer> productIds = new ArrayList<>();
               /* String totalPrice="";
                String totalCarryFee="";*/
                BigDecimal totalPrice = new BigDecimal("0");
                BigDecimal totalCarry = new BigDecimal("0");

                StringBuilder res = new StringBuilder("");
                for (CartBean c : checkedCartBeans) {
                    res.append(c.getId());
                    ids.add(c.getId());
                    productIds.add(c.getProductId());
                    images.add(c.getProductCover());
                    totalPrice = totalPrice.add(c.getProductPrice());
                    totalCarry = totalCarry.add(c.getCarryFee());
                }


                Intent intent = new Intent(getActivity(), BuyActivity.class);
                intent.putExtra("ids", (Serializable) ids);
                intent.putExtra("productIds", (Serializable) productIds);

                intent.putExtra("images", (Serializable) images);
                intent.putExtra("totalPrice", totalPrice.toString());
                intent.putExtra("totalCarry", totalCarry.toString());
                startActivity(intent);


              //  Toast.makeText(mContext, "提交的商品：" + res, Toast.LENGTH_SHORT).show();


            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkedCartBeans.size() == 0) {
                    Toasty.info(getContext(), "请选中商品后再删除！", Toasty.LENGTH_SHORT).show();
                    return;
                }
                List<Integer> ids = new ArrayList<>();
                List<String> images = new ArrayList<>();
                List<Integer> productIds = new ArrayList<>();
               /* String totalPrice="";
                String totalCarryFee="";*/
                BigDecimal totalPrice = new BigDecimal("0");
                BigDecimal totalCarry = new BigDecimal("0");

                StringBuilder res = new StringBuilder("");
                for (CartBean c : checkedCartBeans) {
                    res.append(c.getId()).append("-");

                }

                mViewModel.delete(res);
                /**
                 * todo
                 * 删除后，添加后，结算后，及时刷新界面
                 */
                cartAdapter.notifyDataSetChanged();


                recyclerView.setAdapter(cartAdapter);



                Toast.makeText(mContext, "删除成功！" + res, Toast.LENGTH_SHORT).show();


            }
        });
    }

}
