package cn.edu.ncu.collegesecondhand.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnOrderClickListener;
import cn.edu.ncu.collegesecondhand.entity.OrderBean;
import cn.edu.ncu.collegesecondhand.ui.my.order.OrderDetailActivity;

/**
 * Created by ren lingyun on 2020/4/22 17:49
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<OrderBean> orderBeans;
    private OnOrderClickListener listener;

    public void setListener(OnOrderClickListener listener) {
        this.listener = listener;
    }

    public OrderAdapter(Context context, List<OrderBean> orderBeans) {
        this.context = context;
        this.orderBeans = orderBeans;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);

        return new OrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        final OrderBean orderBean = orderBeans.get(position);
        holder.textView_productName.setText(orderBean.getProductName());
        holder.textView_price.setText(orderBean.getProductPrice() + "");
        Glide.with(context)
                .load(orderBean.getProductCover().substring(0,orderBean.getProductCover().indexOf(";")))
                .centerCrop()
                .into(holder.imageView_productCover);

        Glide.with(context)
                .load(orderBean.getSellerAvatar())
                .centerCrop()
                .into(holder.imageView_sellerAvatar);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     if (orderBean!=null){
                 //   Toast.makeText(context, "order not null", Toast.LENGTH_SHORT).show();

                    listener.click(orderBean);}
                else {
                    Toast.makeText(context, "order null", Toast.LENGTH_SHORT).show();
                }*/
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderBean", orderBean);
                context.startActivity(intent);
            }
        });
        switch (orderBean.getStatus()) {
            case 0:
                holder.textView_status.setText("未付款");
                break;
            case 1:
                holder.textView_status.setText("待收货");
                break;
            case 2:
                holder.textView_status.setText("已完成");
                break;
            case 3:
                holder.textView_status.setText("退款中");
                break;
            case 4:

                holder.textView_status.setTextColor( context.getResources().getColor(R.color.colorPrimary));
                holder.textView_status.setText("退款完成");
                break;
        }


    }

    @Override
    public int getItemCount() {
        return orderBeans.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView_sellerAvatar;
        ImageView imageView_productCover;
        TextView textView_sellerName;
        TextView textView_productName;
        TextView textView_price;
        LinearLayout layout;
        TextView textView_status;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_sellerAvatar = itemView.findViewById(R.id.order_item_seller_avatar);
            imageView_productCover = itemView.findViewById(R.id.order_item_product_cover);
            textView_price = itemView.findViewById(R.id.order_item_price);
            textView_sellerName = itemView.findViewById(R.id.order_item_seller_name);
            textView_productName = itemView.findViewById(R.id.order_item_product_name);

            layout = itemView.findViewById(R.id.order_item_layout);
            textView_status = itemView.findViewById(R.id.status);
        }
    }
}
