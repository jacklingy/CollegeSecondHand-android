package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.interf.ReleaseImageClickListener;
import cn.edu.ncu.collegesecondhand.entity.ReleaseImage;

/**
 * Created by ren lingyun on 2020/4/24 17:04
 */
public class ReleaseImageAdapter extends RecyclerView.Adapter<ReleaseImageAdapter.ReleaseImageViewHolder> {
    private List<ReleaseImage> releaseImages;
    private Context context;
    private ReleaseImageClickListener listener;

    public ReleaseImageAdapter(Context context, List<ReleaseImage> releaseImages) {
        this.context = context;
        this.releaseImages = releaseImages;
    }

    public void setListener(ReleaseImageClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ReleaseImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.release_image_item, parent, false);

        return new ReleaseImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReleaseImageViewHolder holder, final int position) {
        ReleaseImage image = releaseImages.get(position);
        if (image.getUri() == null) {
            Glide.with(context)
                    .load(image.getNetPath())
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            Glide.with(context)
                    .load(image.getUri())
                    .centerCrop()
                    .into(holder.imageView);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(position);

            }
        });

    }

    public void scrollToTop() {

    }

    @Override
    public int getItemCount() {
        return releaseImages.size();
    }

    class ReleaseImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public ReleaseImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.release_image_view);
        }
    }
}
