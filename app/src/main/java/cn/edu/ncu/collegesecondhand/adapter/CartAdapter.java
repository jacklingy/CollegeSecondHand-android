package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.interf.CartCheckListener;
import cn.edu.ncu.collegesecondhand.entity.Cart;
import cn.edu.ncu.collegesecondhand.entity.CartBean;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

/**
 * Created by ren lingyun on 2020/4/20 1:53
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartBean> cartBeans;
    private CartCheckListener cartCheckListener;

    public CartAdapter(Context context, List<CartBean> cartBeans) {
        this.context = context;
        this.cartBeans = cartBeans;
    }

    public void setCartCheckListener(CartCheckListener cartCheckListener) {
        this.cartCheckListener = cartCheckListener;

    }
    public List<CartBean> getCartBeans(){
        return this.cartBeans;
    }

    public void setAllChecked(boolean isChecked) {
        if (isChecked) {
            for (CartBean cartBean : cartBeans) {
                cartBean.setChecked(true);

            }
        } else {
            for (CartBean cartBean : cartBeans) {
                cartBean.setChecked(false);
            }

        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        final CartBean cartBean = cartBeans.get(position);
        Glide.with(context)
                .load(cartBean.getUserAvatar())
                .into(holder.circleImageView);
        holder.userName.setText(cartBean.getUserName());
        Glide.with(context)
                .load(cartBean.getProductCover())
                .centerCrop()
                .into(holder.productCover);
        holder.productName.setText(cartBean.getProductName());
        holder.productPrice.setText(String.format("%s", cartBean.getProductPrice()));
        if (cartBean.isChecked()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //toast id
                    cartCheckListener.isChecked(cartBean);
                } else {
                    cartCheckListener.isNotChecked(cartBean);

                    //Toasty.info(context, "unChecked!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //

    }

    @Override
    public int getItemCount() {
        return cartBeans.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView userName;
        private CheckBox checkBox;
        private ImageView productCover;
        private TextView productName;
        private TextView productPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.cart_user_avatar);
            userName = itemView.findViewById(R.id.cart_user_name);
            checkBox = itemView.findViewById(R.id.cart_item_check_box);
            productCover = itemView.findViewById(R.id.cart_product_cover);
            productName = itemView.findViewById(R.id.cart_product_name);
            productPrice = itemView.findViewById(R.id.cart_product_price);

            //click listener
        }
    }
}
