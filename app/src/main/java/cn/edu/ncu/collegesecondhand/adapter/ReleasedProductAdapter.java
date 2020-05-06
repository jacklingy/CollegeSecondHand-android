package cn.edu.ncu.collegesecondhand.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnReleasedProductDeleteListener;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnReleasedProductEditListener;
import cn.edu.ncu.collegesecondhand.entity.Product;
import cn.edu.ncu.collegesecondhand.entity.ProductBean;
import cn.edu.ncu.collegesecondhand.ui.home.ProductActivity;

/**
 * Created by ren lingyun on 2020/4/25 16:41
 */
public class ReleasedProductAdapter extends RecyclerView.Adapter<ReleasedProductAdapter.ReleasedProductViewHolder> {
    private Context mContext;
    private List<Product> products;

    private OnReleasedProductDeleteListener deleteListener;
    private OnReleasedProductEditListener editListener;

    public void setDeleteListener(OnReleasedProductDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setEditListener(OnReleasedProductEditListener editListener) {
        this.editListener = editListener;
    }

    public ReleasedProductAdapter(Context mContext, List<Product> products) {
        this.mContext = mContext;
        this.products = products;
    }

    @NonNull
    @Override
    public ReleasedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.released_item, parent, false);
        return new ReleasedProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReleasedProductViewHolder holder, int position) {
        final Product product = products.get(position);
        Glide.with(mContext)
                .load(product.getImages()) //images在服务端经过处理 得到第一张图片
                .centerCrop()
                .into(holder.imageView);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice().toString());
        holder.originalPrice.setText(product.getOriginalPrice().toString());

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                deleteListener.delete(product);
            }
        });

        holder.button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editListener.edit(product);
            }
        });
        switch (product.getStatus()) {
            //已卖出
            case 0:
                holder.status.setText("已完成");
                holder.status.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.button_edit.setVisibility(View.GONE);

                break;
            case 1:
                holder.status.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));

                break;
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //jump to the product detail activity;
                Intent intent = new Intent(mContext, ProductActivity.class);
                //put from released code;
                intent.putExtra("id", product.getId());
                intent.putExtra("self","true");
                mContext.startActivity(intent);

              //  Toast.makeText(mContext, "item clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ReleasedProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView price;
        TextView originalPrice;
        Button button_delete;
        Button button_edit;

        TextView status;
        RelativeLayout layout;


        public ReleasedProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.released_image);
            name = itemView.findViewById(R.id.released_name);
            price = itemView.findViewById(R.id.released_price);
            originalPrice = itemView.findViewById(R.id.released_original_price);
            button_delete = itemView.findViewById(R.id.released_button_delete);
            button_edit = itemView.findViewById(R.id.released_button_edit);
            status = itemView.findViewById(R.id.released_status);
            layout = itemView.findViewById(R.id.released_layout);

        }
    }
}
