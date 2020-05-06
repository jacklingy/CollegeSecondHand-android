package cn.edu.ncu.collegesecondhand.ui.my.order.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.OrderAdapter;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnOrderClickListener;
import cn.edu.ncu.collegesecondhand.entity.Order;
import cn.edu.ncu.collegesecondhand.entity.OrderBean;
import cn.edu.ncu.collegesecondhand.ui.my.order.OrderDetailActivity;

import static android.app.Activity.RESULT_OK;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    public static final int ORDER_CODE=13;
    private RecyclerView recyclerView;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        //  final ListView listView=root.findViewById(R.id.list_view);

        recyclerView = root.findViewById(R.id.order_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("this is item " + i);
        }

        pageViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                switch (s) {
                    //s是页面
                    case "1":
                        pageViewModel.get_all(0).observe(getActivity(), new Observer<List<OrderBean>>() {
                            @Override
                            public void onChanged(List<OrderBean> orderBeans) {
                                //  Toast.makeText(getContext(), (orderBeans==null)+""+orderBeans.size(), Toast.LENGTH_SHORT).show();

                                OrderAdapter orderAdapter = new OrderAdapter(getContext(), orderBeans);
                           /*     orderAdapter.setListener(new OnOrderClickListener() {
                                    @Override
                                    public void click(OrderBean orderBean) {

                                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                                        intent.putExtra("orderBean", orderBean);
                                        startActivityForResult(intent,ORDER_CODE);

                                    }
                                });*/
                                recyclerView.setAdapter(orderAdapter);
                                orderAdapter.notifyDataSetChanged();


                            }
                        });


                        break;
                    case "2":
                        pageViewModel.get_all(1).observe(getActivity(), new Observer<List<OrderBean>>() {
                            @Override
                            public void onChanged(List<OrderBean> orderBeans) {
                                //  Toast.makeText(getContext(), (orderBeans==null)+""+orderBeans.size(), Toast.LENGTH_SHORT).show();

                                OrderAdapter orderAdapter = new OrderAdapter(getContext(), orderBeans);
                                recyclerView.setAdapter(orderAdapter);
                                orderAdapter.notifyDataSetChanged();


                            }
                        });


                        break;
                    case "3":
                        pageViewModel.get_all(2).observe(getActivity(), new Observer<List<OrderBean>>() {
                            @Override
                            public void onChanged(List<OrderBean> orderBeans) {
                                //  Toast.makeText(getContext(), (orderBeans==null)+""+orderBeans.size(), Toast.LENGTH_SHORT).show();

                                OrderAdapter orderAdapter = new OrderAdapter(getContext(), orderBeans);
                                recyclerView.setAdapter(orderAdapter);
                                orderAdapter.notifyDataSetChanged();


                            }
                        });


                        break;
                    case "4":
                        pageViewModel.get_all(3).observe(getActivity(), new Observer<List<OrderBean>>() {
                            @Override
                            public void onChanged(List<OrderBean> orderBeans) {
                                //  Toast.makeText(getContext(), (orderBeans==null)+""+orderBeans.size(), Toast.LENGTH_SHORT).show();

                                OrderAdapter orderAdapter = new OrderAdapter(getContext(), orderBeans);
                                recyclerView.setAdapter(orderAdapter);
                                orderAdapter.notifyDataSetChanged();


                            }
                        });


                        break;
                    case "5":
                        pageViewModel.get_all(4).observe(getActivity(), new Observer<List<OrderBean>>() {
                            @Override
                            public void onChanged(List<OrderBean> orderBeans) {
                                //  Toast.makeText(getContext(), (orderBeans==null)+""+orderBeans.size(), Toast.LENGTH_SHORT).show();

                                OrderAdapter orderAdapter = new OrderAdapter(getContext(), orderBeans);
                                recyclerView.setAdapter(orderAdapter);
                                orderAdapter.notifyDataSetChanged();


                            }
                        });


                        break;

                }
            }
        });


        //test method  useless deletable
        pageViewModel.getOrders().observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),
                        android.R.layout.simple_list_item_1, strings);
                //  listView.setAdapter(arrayAdapter);

            }
        });
        return root;
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ORDER_CODE){
            if (resultCode==RESULT_OK){

                Toast.makeText(getContext(), "receive data", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}