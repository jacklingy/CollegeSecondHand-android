package cn.edu.ncu.collegesecondhand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;


public class BaseDelegateAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {

    private LayoutHelper mLayoutHelper;
    private int mCount = -1;
    private int mLayoutId = -1;
    private Context mContext;
    private int mViewTypeItem = -1;

    public BaseDelegateAdapter(Context context, LayoutHelper layoutHelper,
                               int layoutId, int count,int mViewTypeItem) {
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mLayoutId = layoutId;
        this.mContext = context;
        this.mViewTypeItem=mViewTypeItem;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext)
                .inflate(mLayoutId, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i) {

    }

    @Override
    public int getItemViewType(int position) {
        return mViewTypeItem;
    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
