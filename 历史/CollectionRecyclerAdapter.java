package com.ht.fragment.collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ht.main.bootmodule.R;

/**
 * Created by Administrator on 2016/12/18 0018.
 */
public class CollectionRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {

    Context context;
//    List<Integer> list;
    LayoutInflater inflater;
    //自定义内部接口的对象(点击)
    private OnClickItemListener mOnClickItemListener = null;

    public CollectionRecyclerAdapter(Context context) {
        inflater=LayoutInflater.from(context);
        this.context = context;
//        this.list = list;
    }
    /**
     * 点击接口回调
     */
    //实现条目点击事件用的方法
    //define（定义） interface（接口）
    public static interface OnClickItemListener {
        //两个参数可改（自定义）
        void onClick(View view , String data);
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
        View mView=inflater.inflate(R.layout.collection_recycler_item,parent,false);
        MyViewHolder VH=new MyViewHolder(mView);
        //将创建的View注册点击事件
        mView.setOnClickListener(this);
        return VH;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
//            ((MyViewHolder) holder).text.setText(list.get(position)+"");
//            ((MyViewHolder) holder).text_bottom.setText(list.get(position)+"***");
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(position+"");
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    private class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mbody;
        ImageView mImg;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTitle= (TextView) itemView.findViewById(R.id.collection_recycler_item_cv_textTitle);
            mbody= (TextView) itemView.findViewById(R.id.collection_recycler_item_cv_textbody);
            mImg= (ImageView) itemView.findViewById(R.id.collection_recycler_item_cv_img);
        }
    }
}
