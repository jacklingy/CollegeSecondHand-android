package cn.edu.ncu.collegesecondhand.ui.my.manage.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.ReleasedProductAdapter;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnReleasedProductDeleteListener;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnReleasedProductEditListener;
import cn.edu.ncu.collegesecondhand.entity.Product;
import cn.edu.ncu.collegesecondhand.ui.my.manage.ReleaseActivity;
import cn.edu.ncu.collegesecondhand.ui.my.order.OrderDetailActivity;
import es.dmoral.toasty.Toasty;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

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
            @NonNull final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_released_management, container, false);
        final TextView textView = root.findViewById(R.id.released_section_label);
        final RecyclerView recyclerView =root.findViewById(R.id.released_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        pageViewModel.getText().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                switch (s){
                    case 1:
                        textView.setText("f"+1);
                        textView.setVisibility(View.GONE);
                        pageViewModel.getAllProducts(1).observe(getActivity(), new Observer<List<Product>>() {
                            @Override
                            public void onChanged(List<Product> products) {
                                ReleasedProductAdapter adapter=new ReleasedProductAdapter(getContext(),products);
                                adapter.setDeleteListener(new OnReleasedProductDeleteListener() {
                                    @Override
                                    public void delete(final Product product) {
                                       // Toast.makeText(getContext(), JSON.toJSONString(product), Toast.LENGTH_SHORT).show();

                                        AlertDialog.Builder builder;
                                        builder = new AlertDialog.Builder(getActivity())
                                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                                .setTitle("删除商品")
                                                .setMessage("确定删除商品吗？")
                                                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        pageViewModel.deleteById(product.getId());
                                                        Toasty.success(getContext(),"删除成功！",Toasty.LENGTH_SHORT).show();

                                                        //todo  optional
                                                        //
                                                        getActivity().finish();


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
                                adapter.setEditListener(new OnReleasedProductEditListener() {
                                    @Override
                                    public void edit(Product product) {
                                        Intent intent=new Intent(getContext(), ReleaseActivity.class);
                                        intent.putExtra("intentCode",1);
                                        intent.putExtra("editProduct",product);
                                        startActivity(intent);

                                      //  Toast.makeText(getContext(), JSON.toJSONString(product), Toast.LENGTH_SHORT).show();

                                    }
                                });
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        break;
                    case 2:
                        textView.setText("f"+2);
                        textView.setVisibility(View.GONE);

                        pageViewModel.getAllProducts(0).observe(getActivity(), new Observer<List<Product>>() {
                            @Override
                            public void onChanged(List<Product> products) {
                                ReleasedProductAdapter adapter=new ReleasedProductAdapter(getContext(),products);
                                recyclerView.setAdapter(adapter);
                                adapter.setDeleteListener(new OnReleasedProductDeleteListener() {
                                    @Override
                                    public void delete(final Product product) {
                                       // Toast.makeText(getContext(), JSON.toJSONString(product), Toast.LENGTH_SHORT).show();


                                        AlertDialog.Builder builder;
                                        builder = new AlertDialog.Builder(getActivity())
                                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                                .setTitle("删除商品")
                                                .setMessage("确定删除商品吗？")
                                                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        pageViewModel.deleteById(product.getId());
                                                        Toasty.success(getContext(),"删除成功！",Toasty.LENGTH_SHORT).show();

                                                        //todo  optional
                                                        //
                                                        getActivity().finish();


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
                                adapter.notifyDataSetChanged();

                            }
                        });

                        break;

                }
            }
        });


        return root;
    }
}