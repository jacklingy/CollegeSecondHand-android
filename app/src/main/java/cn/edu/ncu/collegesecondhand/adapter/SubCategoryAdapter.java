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

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.entity.SubCategory;
import cn.edu.ncu.collegesecondhand.ui.home.ProductActivity;
import cn.edu.ncu.collegesecondhand.ui.home.SearchActivity;


public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    private List<SubCategory> subCategories;
    private Context mContext;

    public SubCategoryAdapter(List<SubCategory> subCategories, Context mContext) {
        this.subCategories = subCategories;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_item, parent, false);

        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        final SubCategory subCategory = subCategories.get(position);
        holder.textView.setText(subCategory.getName());
        Glide.with(mContext)
                .load(subCategory.getImage())
                .centerCrop()
                .into(holder.imageView);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra("keyword", subCategory.getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                mContext.startActivity(intent);
               // Toast.makeText(mContext, "sub category " + subCategory.getName() + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout layout;
        ImageView imageView;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_sub_category_name);
            layout = itemView.findViewById(R.id.category_sub_category_layout);
            imageView = itemView.findViewById(R.id.category_sub_category_image);

        }
    }
}
