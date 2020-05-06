package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.HeaderIcon;
import cn.edu.ncu.collegesecondhand.entity.TestImage;

/**
 * Created by ren lingyun on 2020/4/15 19:57
 */
public class GridLayoutAdapter extends DelegateAdapter.Adapter<GridLayoutAdapter.GridViewHolder> {


    private Context mContext;
    private LayoutHelper mHelper;
    private List<HeaderIcon> headerIcons;
    private int count = 0;

    public GridLayoutAdapter(Context context, List<HeaderIcon> headerIcons, LayoutHelper helper, int count) {
        this.mContext = context;
        this.headerIcons =headerIcons ;
        this.mHelper = helper;
        this.count = count;


    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mHelper;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new GridViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header_icon_item
                , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {

        holder.textView.setText(headerIcons.get(position).getName());
        Glide.with(mContext)
                .load(headerIcons.get(position).getImage())
                .centerCrop()
                .into(holder.imageView);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "clicked!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return count;
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
       private LinearLayout layout;

        GridViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.header_icon);
            textView = itemView.findViewById(R.id.header_name);
            layout=itemView.findViewById(R.id.header_layout);

        }
    }
}
