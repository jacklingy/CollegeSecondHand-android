package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.Product;
import cn.edu.ncu.collegesecondhand.entity.ProductBean;
import cn.edu.ncu.collegesecondhand.ui.home.ProductActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.reflect.KVisibility;

/**
 * Created by ren lingyun on 2020/4/15 19:28
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductBean> productBeans;
    private Context context;

    public ProductAdapter(Context context,List<ProductBean> productBeans) {
        this.context = context;
        this.productBeans=productBeans;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductBean productBean = productBeans.get(position);

        Glide.with(context)
                .load(productBean.getProductCover())
                .centerCrop()
                .into(holder.imageView_home_productCover);
        holder.textView_home_productName.setText(productBean.getProductName());
        holder.textView_home_productPrice.setText(String.format("%s", productBean.getProductPrice()));
        Glide.with(context)
                .load(productBean.getUserAvatar())
                .centerCrop()
                .into(holder.imageView_home_sellerAvatar);
        holder.textView_home_sellerName.setText(productBean.getUserName());
        if (productBean.getIsBadge() == 1) {
            holder.layout_home_badge.setVisibility(View.VISIBLE);
            if (productBean.getIsBadgeAdmin() == 1) {
                holder.textView_home_badgeAdmin.setVisibility(View.VISIBLE);
            }
            if (productBean.getIsBadgeMember() == 1) {
                holder.textView_home_badgeMember.setVisibility(View.VISIBLE);
            }
            if (productBean.getIsBadgeNcu() == 1) {
                holder.textView_home_badgeNcu.setVisibility(View.VISIBLE);
            }

        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("id", productBean.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                context.startActivity(intent);
            }
        });



    }

    public void setData(List<ProductBean> productBeans) {
        this.productBeans = productBeans;
        notifyDataSetChanged();


    }

    public void addData(List<ProductBean> productBeans) {
        this.productBeans.addAll(productBeans);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return productBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_home_productCover;
        TextView textView_home_productName;
        TextView textView_home_productPrice;
        CircleImageView imageView_home_sellerAvatar;
        TextView textView_home_sellerName;
        LinearLayout layout_home_badge;
        TextView textView_home_badgeAdmin;
        TextView textView_home_badgeMember;
        TextView textView_home_badgeNcu;

        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_home_productCover = itemView.findViewById(R.id.home_product_cover);
            textView_home_productName = itemView.findViewById(R.id.home_product_name);
            textView_home_productPrice = itemView.findViewById(R.id.home_product_price);
            imageView_home_sellerAvatar = itemView.findViewById(R.id.home_seller_avatar);
            textView_home_sellerName = itemView.findViewById(R.id.home_seller_name);
            layout_home_badge = itemView.findViewById(R.id.home_badge);
            textView_home_badgeAdmin = itemView.findViewById(R.id.home_badge_admin);
            textView_home_badgeMember = itemView.findViewById(R.id.home_badge_member);
            textView_home_badgeNcu = itemView.findViewById(R.id.home_badge_ncu);

            layout = itemView.findViewById(R.id.home_product_whole);

        }
    }
}


