package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.ncu.collegesecondhand.R;
import cn.edu.ncu.collegesecondhand.adapter.interf.CategoryClickListener;
import cn.edu.ncu.collegesecondhand.entity.Category;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private static final String TAG = "CategoryAdapter";

    private List<Category> categories;
    private Context mContext;
    private CategoryClickListener listener;

    public void setListener(CategoryClickListener listener) {
        this.listener = listener;
    }

    public CategoryAdapter(List<Category> categories, Context mContext) {
        Log.e(TAG, "CategoryAdapter: constructed");
        this.categories = categories;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        holder.textView.setText(categories.get(position).getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, categories.get(position).getId() + " clicked!", Toast.LENGTH_SHORT).show();
                listener.click(categories.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout layout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_item_text);
            layout = itemView.findViewById(R.id.category_item_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        Log.e(TAG, "onClick: listener not null");
                        listener.click(getAdapterPosition());
                    }
                    else {
                        Log.e(TAG, "onClick: listener null");


                    }

                }
            });

        }
    }



}
