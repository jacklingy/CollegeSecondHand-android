package cn.edu.ncu.collegesecondhand.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.blcodes.views.refresh.BounceCallBack;
import com.blcodes.views.refresh.BounceHandler;
import com.blcodes.views.refresh.BounceLayout;
import com.blcodes.views.refresh.EventForwardingHelper;
import com.blcodes.views.refresh.NormalBounceHandler;
import com.blcodes.views.refresh.footer.DefaultFooter;
import com.blcodes.views.refresh.header.DefaultHeader;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.BaseViewHolder;
import cn.edu.ncu.collegesecondhand.adapter.GridLayoutAdapter;
import cn.edu.ncu.collegesecondhand.adapter.GridLayoutAdapterAction;
import cn.edu.ncu.collegesecondhand.adapter.ImageAdapter;
import cn.edu.ncu.collegesecondhand.adapter.ProductAdapter;
import cn.edu.ncu.collegesecondhand.adapter.StaggeredGridLayoutAdapter;
import cn.edu.ncu.collegesecondhand.entity.HeaderIcon;
import cn.edu.ncu.collegesecondhand.entity.MyBanner;
import cn.edu.ncu.collegesecondhand.entity.Product;
import cn.edu.ncu.collegesecondhand.entity.ProductBean;
import cn.edu.ncu.collegesecondhand.entity.TestImage;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private HomeViewModel mViewModel;
    private Context mContext;
    private RecyclerView recyclerView;
    private int currentCount = 30;

    private GridLayoutHelper gridLayoutHelper_header = new GridLayoutHelper(5);//每行5个
    private GridLayoutHelper gridLayoutHelper_action = new GridLayoutHelper(2);//每行2个
    private final StaggeredGridLayoutHelper staggeredGridLayoutHelper =
            new StaggeredGridLayoutHelper(2, 10);
    private DelegateAdapter delegateAdapter;
    private List<DelegateAdapter.Adapter> adapters = new LinkedList<>();


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mContext = getContext();
        View root = inflater.inflate(R.layout.home_fragment, container, false);
       /* ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_search_background));*/

        //init components

  /*      Toolbar toolbar=root.findViewById(R.id.home_tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);*/
        FrameLayout frameLayout = root.findViewById(R.id.home_frame_layout);
        //LightRefresh-bounce layout
        recyclerView = root.findViewById(R.id.home_recycler_view);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        //adapters and helpers
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, false);
        recyclerView.setLayoutManager(virtualLayoutManager);


        /***/
        gridLayoutHelper_header.setItemCount(5);//5个item
        gridLayoutHelper_header.setAutoExpand(true);
        gridLayoutHelper_header.setGap(20);
        gridLayoutHelper_header.setMarginLeft(20);
        gridLayoutHelper_header.setMarginRight(20);
        //initiate header here, cause it does not change, it is constant,including
        //the image,the name, and the action

        List<HeaderIcon> headerIcons_header = new ArrayList<>();
        headerIcons_header.add(new HeaderIcon("url", "二手手机", "keyword",
                R.drawable.phone));
        headerIcons_header.add(new HeaderIcon("url", "二手电脑", "keyword",
                R.drawable.laptop));
        headerIcons_header.add(new HeaderIcon("url", "二手书籍", "keyword",
                R.drawable.note));
        headerIcons_header.add(new HeaderIcon("url", "二手出行", "keyword",
                R.drawable.bicycle));
        headerIcons_header.add(new HeaderIcon("url", "全部分类", "keyword",
                R.drawable.category));
        GridLayoutAdapter gridLayoutAdapter = new GridLayoutAdapter(mContext, headerIcons_header
                , gridLayoutHelper_header, 5);
        adapters.add(gridLayoutAdapter);


/***/
        // gridLayoutHelper_action.setItemCount(4);
        // gridLayoutHelper_action.setAutoExpand(false);
        gridLayoutHelper_action.setMarginTop(15);
        gridLayoutHelper_action.setHGap(8);
        gridLayoutHelper_action.setVGap(8);
        gridLayoutHelper_action.setMarginLeft(10);
        gridLayoutHelper_action.setMarginRight(10);
        gridLayoutHelper_action.setMarginBottom(25);


        List<HeaderIcon> headerIcons_action = new ArrayList<>();
        headerIcons_action.add(new HeaderIcon("url", "南昌大学专区",
                "keyword", R.drawable.latest2));
        headerIcons_action.add(new HeaderIcon("url", "推荐",
                "keyword", R.drawable.recommand));
        /*headerIcons_action.add(new HeaderIcon("url", "南昌大学专区",
                "keyword", R.drawable.ncuzone));
        headerIcons_action.add(new HeaderIcon("url", "南昌大学专区",
                "keyword", R.drawable.ncuzone));*/

        List<HeaderIcon> headerIcons_action_ncu=new ArrayList<>();
        headerIcons_action_ncu.add(new HeaderIcon("url","南昌大学专区","keyword",
                R.drawable.ncu2));

        GridLayoutHelper gridLayoutHelper_action_ncu=new GridLayoutHelper(1,1);
        gridLayoutHelper_action_ncu.setMarginLeft(10);
        gridLayoutHelper_action_ncu.setMarginRight(10);

        GridLayoutAdapterAction gridLayoutAdapterAction_ncu=new GridLayoutAdapterAction(
                mContext,headerIcons_action_ncu,gridLayoutHelper_action_ncu,1);


        GridLayoutAdapterAction gridLayoutAdapterAction = new GridLayoutAdapterAction(
                mContext, headerIcons_action, gridLayoutHelper_action, 2);
        adapters.add(gridLayoutAdapterAction_ncu);

        adapters.add(gridLayoutAdapterAction);


        /***/

        staggeredGridLayoutHelper.setHGap(10);
        staggeredGridLayoutHelper.setVGap(10);

        /***/


        final BounceLayout bounceLayout = root.findViewById(R.id.home_bounce_layout);
        bounceLayout.setHeaderView(new DefaultHeader(mContext), frameLayout);
        bounceLayout.setFooterView(new DefaultFooter(mContext), frameLayout);
        bounceLayout.setBounceHandler(new NormalBounceHandler(), recyclerView);
//        bounceLayout.setDisallowBounce(true);
        bounceLayout.setEventForwardingHelper(new EventForwardingHelper() {
            @Override
            public boolean notForwarding(float v, float v1, float v2, float v3) {
                return true;
            }
        });
        bounceLayout.setBounceCallBack(new BounceCallBack() {
            @Override
            public void startRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewModel.refresh();
                        bounceLayout.setRefreshCompleted();



                    }
                }, 500);

            }

            @Override
            public void startLoadingMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewModel.addMore(currentCount);
                        currentCount += 30;
                        bounceLayout.setLoadingMoreCompleted();

                    }
                }, 500);
            }
        });
//        bounceLayout.setBounceCallBack(new BounceCallBack() {
//            @Override
//            public void startRefresh() {
//                Log.i(TAG, "run: 开始刷新");
//                Toast.makeText(mContext, "Refreshing", Toast.LENGTH_SHORT).show();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // ArrayList<String> data = new ArrayList<>();
//                        for (int i = 0; i < 16; i++) {
//                            //data.add("新文本"+i);
//                        }
//                        //  adapter.setData(data);
//                        bounceLayout.setRefreshCompleted();
//                        Log.i(TAG, "run: 结束刷新");
//                    }
//                }, 2000);
//            }
//
//            @Override
//            public void startLoadingMore() {
//                Log.i(TAG, "Start loading more...");
//                Toast.makeText(mContext, "currentCount is "+currentCount, Toast.LENGTH_SHORT).show();
//
//                Toast.makeText(mContext, "Loading more", Toast.LENGTH_SHORT).show();
//                mViewModel.addMore(currentCount);
//                currentCount += 30;
//
//
//                Toast.makeText(mContext, "setLoadingMoreCompleted() currentCount is "+currentCount, Toast.LENGTH_SHORT).show();
//
//
//
//            }
//        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(mContext, "currentCount is " + currentCount, Toast.LENGTH_SHORT).show();
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

      /*          final StaggeredGridLayoutAdapter staggeredGridLayoutAdapter = new StaggeredGridLayoutAdapter(
                        mContext, staggeredGridLayoutHelper, 30);
        adapters.add(staggeredGridLayoutAdapter);
*/

        mViewModel.getAllProducts().observe(getActivity(), new Observer<List<ProductBean>>() {
            StaggeredGridLayoutAdapter staggeredGridLayoutAdapter = null;

            @Override
            public void onChanged(List<ProductBean> productBeans) {

                if (staggeredGridLayoutAdapter == null) {

                    staggeredGridLayoutAdapter = new StaggeredGridLayoutAdapter(
                            mContext, productBeans, new StaggeredGridLayoutHelper(2), 30);
                    adapters.add(staggeredGridLayoutAdapter);

                    delegateAdapter.setAdapters(adapters);
                    recyclerView.setAdapter(delegateAdapter);
                    staggeredGridLayoutAdapter.notifyDataSetChanged();

                } else {
                    staggeredGridLayoutAdapter.setData(productBeans);
                    staggeredGridLayoutAdapter.notifyDataSetChanged();
                }
                //  staggeredGridLayoutAdapter.setData(productBeans);
                // staggeredGridLayoutAdapter.setData(productBeans);


                //delegateAdapter.setAdapters(adapters);

         /*      delegateAdapter.addAdapter(staggeredGridLayoutAdapter);
                staggeredGridLayoutAdapter.notifyDataSetChanged();*/


                // Toast.makeText(getActivity(), "" + productBeans.toString(), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(mContext, productBeans.toString(), Toast.LENGTH_SHORT).show();
             /*   ProductAdapter productAdapter = new ProductAdapter(getContext());
                productAdapter.setData(productBeans);

                //layout manager
                StaggeredGridLayoutManager productManager = new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(productManager);
                recyclerView.setAdapter(productAdapter);*/
            }
        });

     /*   recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Toast.makeText(mContext, "scrolling!", Toast.LENGTH_SHORT).show();
                if (isSlideToBottom(recyclerView)) {
                    Toast.makeText(mContext, "scrolling to bottom!", Toast.LENGTH_SHORT).show();


                }
            }
        });
*/

        // TODO: Use the ViewModel

       /* List<ProductBean> productBeans=new ArrayList<>();
        ProductBean productBean=new ProductBean(4,
                "http://116.62.47.202/images/newbalance.jpeg","new balance",
                new BigDecimal("299.00"),"http://116.62.47.202/images/newbalance.jpeg",
                "prayerling",false,true,true,true);
        ProductBean productBean2=new ProductBean(5,
                "http://116.62.47.202/images/6fea63da748142c8b697ccc069390075.jpg",
                "苹果 Apple iPad Pro 64G 银色 九成新 官网购买 正品保障",
                new BigDecimal("4299.00"),"http://116.62.47.202/images/snooy.png",
                "Jack",true,true,true,true);
        ProductBean productBean3=new ProductBean(6,
                "http://116.62.47.202/images/newbalance.jpeg","new balance",
                new BigDecimal("299.00"),"http://116.62.47.202/images/newbalance.jpeg",
                "prayerling",true,false,false,true);
        productBeans.add(productBean);
        productBeans.add(productBean2);
        productBeans.add(productBean3);*/
        //adapter


       /* ProductAdapter productAdapter=new ProductAdapter(getContext(),productBeans);

        //layout manager
        StaggeredGridLayoutManager productManager=new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(productManager);
        recyclerView.setAdapter(productAdapter);*/



       /* VirtualLayoutManager virtualLayoutManager=new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);


        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();


        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapters(adapters);
     //   delegateAdapter.addAdapter(bannerAdapter);
       // recyclerView.setAdapter(delegateAdapter);



        List<TestImage> images=new ArrayList<>();




        //add data
        images.add(new TestImage("sacjkbadckibadjcbadchbaodcbacsdvevdavdavadvavadvadvad",
                "https://s1.ax1x.com/2020/04/15/JCFOh9.th.jpg"));
        for (int i = 0; i < 10; i++) {
            images.add(new TestImage("baidu" + i, "https://s1.ax1x.com/2020/04/15/JCFOh9.th.jpg"));
        }
        StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper(2,
                10);
        staggeredGridLayoutHelper.setHGap(20);
        staggeredGridLayoutHelper.setVGap(20);

        DelegateAdapter adapter = new DelegateAdapter(virtualLayoutManager, true);
        List<MyBanner> myBanners=new ArrayList<>();
        myBanners.add(new MyBanner("https://www.baidu.com/img/bd_logo1.png",1));
        myBanners.add(new MyBanner("https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/a4aa0cbfad7de34618c4bebdbdeee4e1.jpg",2));
        myBanners.add(new MyBanner("https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/e52c3e98602b90f198ec316dce253cba.jpg",3));


        //adapter.addAdapter(new StaggeredGridLayoutAdapter(getContext(), images, staggeredGridLayoutHelper));



        recyclerView.setAdapter(delegateAdapter);

*/
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

}
