package com.ht.fragment.history.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ht.fragment.history.bean.HistoryBean;
import com.ht.main.bootmodule.R;

import java.util.List;

/**
 * autour: 徐仕奇
 * action：类的作用：RecyclerView的Adapter负责展示数据
 * date: 2016/12/16 0016 下午 9:37
 * update: 2016/12/16 0016
 */
public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener{

    Context context;
    List<HistoryBean.ResultBean> list;
    LayoutInflater inflater;
    //自定义内部接口的对象(点击)
    private OnClickItemListener mOnClickItemListener = null;

    public HistoryRecyclerViewAdapter(Context context, List<HistoryBean.ResultBean> list) {
        inflater=LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }
    /**
     * 点击接口回调
     */
    //实现条目点击事件用的方法
    //define（定义） interface（接口）
    public static interface OnClickItemListener {
        //两个参数可改（自定义）
        void onClick(View view , String e_id);
    }
    //提供的对外调用setOnItemClickListener的方法，参数是内部接口
    public void setOnItemClickListener(OnClickItemListener listener) {
        this.mOnClickItemListener = listener;
    }

    //点击方法（实现的是实现的接口的抽象方法）
    //点击的实现靠它，因为它是实现的点击方法
    @Override
    public void onClick(View v) {
        if (mOnClickItemListener != null) {
            //（内部点击接口）注意这里使用getTag方法获取数据
            //参数对应内部点击接口参数一致
            mOnClickItemListener.onClick(v,(String)v.getTag());
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView=inflater.inflate(R.layout.history_recycler_item,parent,false);
        MyViewHolder VH=new MyViewHolder(mView);
        //将创建的View注册点击事件
        mView.setOnClickListener(this);
        return VH;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            ((MyViewHolder) holder).date.setText(list.get(position).getDate());
            ((MyViewHolder) holder).title.setText(list.get(position).getTitle());
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(list.get(position).getE_id());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.History_recycler_item_data);
            title= (TextView) itemView.findViewById(R.id.History_recycler_item_title);
        }
    }
}
