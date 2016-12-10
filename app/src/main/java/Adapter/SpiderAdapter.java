package Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import DataBean.BaijiaBean;
import ViewHolder.BaseViewHolder;
import ViewHolder.RecyclerViewHolderBase;
import skkk.gogogo.cwinformation.R;

/**
 * Created by admin on 2016/11/21.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/21$ 21:24$.
*/
public class SpiderAdapter extends RecyclerViewBaseAdapter<String> {
    private Context context;
    private LayoutInflater inflater;

    public SpiderAdapter(Context context, List<String> mItemDataList) {
        super(mItemDataList);
        this.context = context;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public void showData(RecyclerViewHolderBase viewHolder, int i, List<String> mItemDataList) {
        //向下转型为子类
        BaseViewHolder holder= (BaseViewHolder) viewHolder;
        holder.tvItem.setText(mItemDataList.get(i));


    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        //加载item的布局
        View view = inflater.inflate(R.layout.item_information, viewGroup,false);
        return view;
    }

    @Override
    public RecyclerViewHolderBase createViewHolder(View view) {
        //直接返回viewholder对象
        return new BaseViewHolder(view);
    }
}
