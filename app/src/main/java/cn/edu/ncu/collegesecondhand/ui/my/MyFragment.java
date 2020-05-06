package cn.edu.ncu.collegesecondhand.ui.my;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.blcodes.views.refresh.BounceCallBack;
import com.blcodes.views.refresh.BounceLayout;
import com.blcodes.views.refresh.EventForwardingHelper;
import com.blcodes.views.refresh.NormalBounceHandler;
import com.blcodes.views.refresh.footer.DefaultFooter;
import com.blcodes.views.refresh.header.DefaultHeader;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.Transformer;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.BaseDelegateAdapter;
import cn.edu.ncu.collegesecondhand.adapter.BaseViewHolder;
import cn.edu.ncu.collegesecondhand.adapter.GridLayoutAdapter;
import cn.edu.ncu.collegesecondhand.adapter.GridLayoutAdapterAction;
import cn.edu.ncu.collegesecondhand.adapter.ImageAdapter;
import cn.edu.ncu.collegesecondhand.adapter.StaggeredGridLayoutAdapter;
import cn.edu.ncu.collegesecondhand.adapter.TestImageAdapter;
import cn.edu.ncu.collegesecondhand.entity.HeaderIcon;
import cn.edu.ncu.collegesecondhand.entity.MyBanner;
import cn.edu.ncu.collegesecondhand.entity.TestImage;
import cn.edu.ncu.collegesecondhand.entity.User;
import cn.edu.ncu.collegesecondhand.ui.my.function.AboutActivity;
import cn.edu.ncu.collegesecondhand.ui.my.function.SettingActivity;
import cn.edu.ncu.collegesecondhand.ui.my.login.LoginActivity;
import cn.edu.ncu.collegesecondhand.ui.my.login.SignInActivity;
import cn.edu.ncu.collegesecondhand.ui.my.manage.ReleaseActivity;
import cn.edu.ncu.collegesecondhand.ui.my.manage.ReleasedManagementActivity;
import cn.edu.ncu.collegesecondhand.ui.my.manage.ServiceActivity;
import cn.edu.ncu.collegesecondhand.ui.my.manage.WalletActivity;
import cn.edu.ncu.collegesecondhand.ui.my.order.OrderActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class MyFragment extends Fragment {

    private static final String TAG = "MyFragment";
    private MyViewModel mViewModel;
    private RelativeLayout relativeLayout_header;
    private LinearLayout linearLayout_header2;
    private SharedPreferences sharedPreferences_userAccount;
    private CircleImageView imageView_userAvatar;
    private TextView textView_userName;
    private TextView textView_userAccount;

    private LinearLayout layout_setting;
    private LinearLayout layout_about;

    //all order
    private LinearLayout layout_allOrders;
    private LinearLayout layout_pay;
    private LinearLayout layout_receive;
    private LinearLayout layout_finish;
    private LinearLayout layout_refund;

    private LinearLayout layout_wallet;
    private LinearLayout layout_release;
    private LinearLayout layout_released;

    private RelativeLayout layout_order;
    private LinearLayout layout_service;

    //user account
    private String userAccount;

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getUserByAccount(userAccount);

        //登录后回到界面，检查是否有用户登录，据此显示header
        sharedPreferences_userAccount = getActivity().getSharedPreferences("userAccount",
                Context.MODE_PRIVATE);
        userAccount = sharedPreferences_userAccount.getString("account", null);

        Log.d(TAG, "onResume: onResume: onResume: onResume: ");
        if (userAccount != null) {
            relativeLayout_header.setVisibility(View.VISIBLE);
            linearLayout_header2.setVisibility(View.GONE);
            mViewModel.getUserByAccount(userAccount);

            // Toasty.info(getContext(),"user not null"+userAccount,Toast.LENGTH_SHORT).show();
        } else {
            relativeLayout_header.setVisibility(View.GONE);
            linearLayout_header2.setVisibility(View.VISIBLE);
            //  Toasty.info(getContext(),"user null",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_fragment, container, false);
        relativeLayout_header = view.findViewById(R.id.my_header_layout);
        linearLayout_header2 = view.findViewById(R.id.my_header_layout2);
        imageView_userAvatar = view.findViewById(R.id.my_user_avatar);
        textView_userName = view.findViewById(R.id.my_user_name);
        textView_userAccount = view.findViewById(R.id.my_user_account);
        layout_setting = view.findViewById(R.id.layout_setting);
        layout_about = view.findViewById(R.id.layout_about);
        layout_allOrders = view.findViewById(R.id.my_order_click);
        layout_pay = view.findViewById(R.id.order_pay);
        layout_receive = view.findViewById(R.id.order_receive);
        layout_refund = view.findViewById(R.id.order_refund);
        layout_finish = view.findViewById(R.id.order_finished);
        layout_wallet = view.findViewById(R.id.product_wallet);
        layout_release = view.findViewById(R.id.product_release);
        layout_released = view.findViewById(R.id.product_released);

        layout_order = view.findViewById(R.id.my_order_layout);
        layout_service=view.findViewById(R.id.product_service);


        //header user information

        //check if user is login in;


        sharedPreferences_userAccount = getActivity().getSharedPreferences("userAccount",
                Context.MODE_PRIVATE);

        userAccount = sharedPreferences_userAccount.getString("account", null);
        if (userAccount != null) {
            relativeLayout_header.setVisibility(View.VISIBLE);
            linearLayout_header2.setVisibility(View.GONE);
            // Toasty.info(getContext(),"user not null"+userAccount,Toast.LENGTH_SHORT).show();
        } else {
            relativeLayout_header.setVisibility(View.GONE);
            linearLayout_header2.setVisibility(View.VISIBLE);
            //  Toasty.info(getContext(),"user null",Toast.LENGTH_SHORT).show();
           /* layout_order.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            layout_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "555", Toast.LENGTH_SHORT).show();
                }
            });*/

        }

        layout_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ServiceActivity.class);
                startActivity(intent);
            }
        });

        layout_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (userAccount == null) {
                    Toasty.info(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
        layout_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        layout_allOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userAccount == null) {
                    Toasty.info(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    startActivity(intent);
                }
            }
        });
        layout_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAccount == null) {
                    Toasty.info(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("index", 0);
                    startActivity(intent);
                }
            }
        });
        layout_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAccount == null) {
                    Toasty.info(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("index", 1);
                    startActivity(intent);
                }
            }
        });

        layout_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAccount == null) {
                    Toasty.info(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("index", 2);
                    startActivity(intent);

                }
            }
        });

        layout_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAccount == null) {
                    Toasty.info(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("index", 3);
                    startActivity(intent);
                }
            }
        });

        layout_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAccount == null) {
                    Toasty.info(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), WalletActivity.class);
                    intent.putExtra("userAccount", userAccount);
                    startActivity(intent);
                }

            }
        });
        layout_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAccount == null) {
                    Toasty.info(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), ReleaseActivity.class);
                    intent.putExtra("userAccount", userAccount);
                    startActivity(intent);
                }
            }
        });
        layout_released.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAccount == null) {
                    Toasty.info(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), ReleasedManagementActivity.class);
                    intent.putExtra("userAccount", userAccount);
                    startActivity(intent);
                }
            }
        });








        /*relativeLayout_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UserInformationActivity.class);
                startActivity(intent);
            }
        });*/
        //login pane
        linearLayout_header2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        mViewModel.getUser().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(final User user) {
                //  Toast.makeText(getContext(), user.getNickName(), Toast.LENGTH_SHORT).show();


                relativeLayout_header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), UserInformationActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);//no need to user startActivityForResult
                    }
                });

                try {

                    Glide.with(getContext())
                            .load(user.getAvatar())
                            .into(imageView_userAvatar);


                    textView_userName.setText(user.getNickName());
                    textView_userAccount.setText(user.getAccount());
                } catch (NullPointerException e) {

                }


            }
        });


        // TODO: Use the ViewModel
    }

}
