package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

import cn.edu.ncu.collegesecondhand.entity.MyBanner;
import cn.edu.ncu.collegesecondhand.ui.home.ProductActivity;

/**
 * Created by ren lingyun on 2020/4/15 16:07
 */
public class ImageAdapter extends BannerAdapter<MyBanner, ImageAdapter.BannerViewHolder> {
    private Context mContext;
    private List<MyBanner> myBanners;

    public ImageAdapter(List<MyBanner> myBanners, Context context) {
        super(myBanners);
        this.mContext = context;
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, final MyBanner myBanner, int position, int size) {

        //final MyBanner myBanner1 = myBanners.get(position);

        Glide.with(mContext)
                .load(myBanner.getImagePath())
                .centerCrop()
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ProductActivity.class);
                intent.putExtra("id", myBanner.getProductId());
                mContext.startActivity(intent);
            }
        });
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}