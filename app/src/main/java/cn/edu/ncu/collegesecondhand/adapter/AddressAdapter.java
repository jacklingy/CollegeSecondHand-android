package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnAddressLayoutClickListener;
import cn.edu.ncu.collegesecondhand.entity.Address;
import cn.edu.ncu.collegesecondhand.ui.home.EditAddressActivity;

/**
 * Created by ren lingyun on 2020/4/27 0:41
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private Context mContext;
    private List<Address> addresses;
    private OnAddressLayoutClickListener listener;
    private int currentAddressId;

    public void setCurrentAddressId(int id) {
        this.currentAddressId = id;
        notifyDataSetChanged();
    }

    public void setListener(OnAddressLayoutClickListener listener) {
        this.listener = listener;
    }

    public AddressAdapter(Context mContex, List<Address> addresses) {
        this.mContext = mContex;
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        final Address address = addresses.get(position);
     /*   if (address.getSelected() == 1) {
            holder.select.setVisibility(View.VISIBLE);
        }*/
     /*   if (currentAddressId == address.getId()) {
            holder.select.setVisibility(View.VISIBLE);
        }*/
        holder.name.setText(address.getName());
        holder.phone.setText(address.getPhone());
        holder.address.setText(address.getAddress());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditAddressActivity.class);
                intent.putExtra("address", address);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                mContext.startActivity(intent);
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(address);

            }
        });


    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView phone;
        TextView address;
        ImageView select;
        ImageView edit;
        LinearLayout layout;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_buyer_name);
            phone = itemView.findViewById(R.id.item_buyer_phone);
            address = itemView.findViewById(R.id.item_buyer_address);
            select = itemView.findViewById(R.id.item_buyer_selected);
            edit = itemView.findViewById(R.id.item_buyer_edit);
            layout = itemView.findViewById(R.id.item_buyer_layout);
        }
    }

}
