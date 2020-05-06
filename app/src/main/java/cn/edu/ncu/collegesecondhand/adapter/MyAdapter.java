package cn.edu.ncu.collegesecondhand.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;

/**
 * Created by ren lingyun on 2020/4/18 0:12
 */
public class MyAdapter extends VirtualLayoutAdapter<MyAdapter.MyViewHolder> {

    public MyAdapter(@NonNull VirtualLayoutManager layoutManager) {
        super(layoutManager);

    }

    @Override
    public void setLayoutHelpers(List<LayoutHelper> helpers) {
        super.setLayoutHelpers(helpers);
    }

    @NonNull
    @Override
    public List<LayoutHelper> getLayoutHelpers() {
        return super.getLayoutHelpers();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_image_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
            textView=itemView.findViewById(R.id.text_view);
        }
    }
}
