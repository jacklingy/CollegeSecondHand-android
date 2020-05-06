package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.TestImage;

/**
 * Created by ren lingyun on 2020/4/15 12:15
 */
public class TestImageAdapter extends VirtualLayoutAdapter<TestImageAdapter.TestImageViewHolder> {
    private Context mContext;
    private List<TestImage> images;


    public TestImageAdapter(Context context, List<TestImage> list, VirtualLayoutManager virtualLayoutManager) {
        super(virtualLayoutManager);
        this.images = list;
        this.mContext = context;

    }


    @NonNull
    @Override
    public TestImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_image_item, parent, false);
        TestImageViewHolder holder = new TestImageViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestImageViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams=holder.imageView.getLayoutParams();
        layoutParams.height=200+position%7*20;
        holder.imageView.setLayoutParams(layoutParams);
        TestImage image = images.get(position);
        Glide.with(mContext)
                .load(image.getPath())
                .into(holder.imageView);
        holder.textView.setText(image.getName());

    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    class TestImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public TestImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
        }
    }

}
