package cn.edu.ncu.collegesecondhand.ui.category;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.pool.GlideTrace;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.CategoryAdapter;
import cn.edu.ncu.collegesecondhand.adapter.GridLayoutAdapter;
import cn.edu.ncu.collegesecondhand.adapter.SubCategoryAdapter;
import cn.edu.ncu.collegesecondhand.adapter.interf.CategoryClickListener;
import cn.edu.ncu.collegesecondhand.entity.Category;
import cn.edu.ncu.collegesecondhand.entity.SubCategory;

public class CategoryFragment extends Fragment {

    private CategoryViewModel mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_sub;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.category_category);
        recyclerView_sub = rootView.findViewById(R.id.category_sub_category);

       /* final List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            categories.add(new Category(i, "目录" + i));
        }*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView_sub.setLayoutManager(gridLayoutManager);

        List<SubCategory> subCategories_1 = new ArrayList<>();



        //default load category 1
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://192.168.2.2:9001/category/getSubByCateId/1";
        StringRequest subCategoryRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<SubCategory>>() {
                }.getType();
                List<SubCategory> subCategories = gson.fromJson(response, type);
                SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(subCategories, getContext());
                recyclerView_sub.setAdapter(subCategoryAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(subCategoryRequest);




        //test sub recyclerview
        List<SubCategory> subCategories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            subCategories.add(new SubCategory("手机", "http://116.62.47.202/images/puma.jpeg",
                    1, 0));
        }


//        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(subCategories, getContext());

        // recyclerView_sub.setAdapter(subCategoryAdapter);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getAllCategories().observe(getActivity(), new Observer<List<Category>>() {

            @Override
            public void onChanged(final List<Category> categories) {
                CategoryAdapter adapter = new CategoryAdapter(categories, getContext());
                recyclerView.setAdapter(adapter);
                adapter.setListener(new CategoryClickListener() {
                    @Override
                    public void click(int id) {
                        mViewModel.getSubFromServer(id);
                    }
                });
                adapter.notifyDataSetChanged();
               /* if (adapter == null) {
                    adapter = new CategoryAdapter(categories, getContext());

                    *//**todo
                 * 点击监听接口
                 *//*
                    adapter.setListener(new CategoryClickListener() {
                        @Override
                        public void click(int id) {
                          *//*  try {
                                Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                //error
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            }*//*
                        }
                    });
                }// else {*/

             /*   adapter.setListener(new  {
                    @Override
                    public void click(int id) {
                        try {
                            Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            //error
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
                  /*  adapter.setListener(new CategoryClickListener() {
                        @Override
                        public void click(int id) {
                            mViewModel.getSubFromServer(id);

                           *//* try {
                                Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                //error
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            }*//*
                        }
                    });*/

                //}//
                mViewModel.getSubCategoriesByCategoryId().observe(getActivity(),
                        new Observer<List<SubCategory>>() {

                            @Override
                            public void onChanged(List<SubCategory> subCategories) {
                                SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(subCategories, getContext());
                                recyclerView_sub.setAdapter(subCategoryAdapter);
                                subCategoryAdapter.notifyDataSetChanged();


                            }
                        });
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
               /* recyclerView.setAdapter(new CommonAdapter<Category>(getContext(),
                        android.R.layout.simple_list_item_1, categories) {
                    @Override
                    protected void convert(ViewHolder holder, Category category, final int position) {
                        holder.setText(android.R.id.text1, categories.get(position).getName());
                        holder.setOnClickListener(android.R.id.text1, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, categories.get(position).getName(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onBindViewHolder(ViewHolder holder, int position) {
                        super.onBindViewHolder(holder, position);
                    }

                    @Override
                    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
                        super.setOnItemClickListener(onItemClickListener);

                    }
                });*/


            }
        });

    }

}
