package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.interf.OnPopupCateListener;
import cn.edu.ncu.collegesecondhand.entity.Category;

/**
 * Created by ren lingyun on 2020/4/24 22:36
 */
public class PopupCategoryAdapter extends RecyclerView.Adapter<PopupCategoryAdapter.PopupCategoryViewHolder> {

    private Context mContext;
    private List<Category> categories;
    private OnPopupCateListener listener;

    public PopupCategoryAdapter(Context mContext, List<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }

    public void setListener(OnPopupCateListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PopupCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items, parent, false);
        return new PopupCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopupCategoryViewHolder holder, int position) {
        final Category category=categories.get(position);
        holder.textView.setText(category.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, category.getName()+"clicked", Toast.LENGTH_SHORT).show();
                listener.click(category);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class PopupCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public PopupCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.category_item_text);
        }
    }

}
