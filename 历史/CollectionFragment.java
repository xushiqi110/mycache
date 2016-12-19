package com.ht.fragment.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ht.main.bootmodule.R;
import com.ht.main.historydetails.HistoryDetailsActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * autour: 徐仕奇
 * action：类的作用：收藏模块页面
 * date: 2016/12/17 0017 下午 12:04
 * update: 2016/12/17 0017
 */
public class CollectionFragment extends Fragment {

    @InjectView(R.id.collection_recycler)
    RecyclerView collectionRecycler;

    public static CollectionFragment newInstance() {
        CollectionFragment fragment = new CollectionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.inject(this, mView);
        //设置主页列表
        setUpRecyclerView();
        return mView;
    }

    /**
     * 设置RecyclerView的属性
     */
    private void setUpRecyclerView() {
        LinearLayoutManager mManager = new LinearLayoutManager(getActivity());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        //为recyclerview添加布局管理器
        collectionRecycler.setLayoutManager(mManager);
        CollectionRecyclerAdapter adapter = new CollectionRecyclerAdapter(getActivity());
        collectionRecycler.setAdapter(adapter);
        //为adapter注册条目点击监听
        adapter.setOnItemClickListener(new CollectionRecyclerAdapter.OnClickItemListener() {
            @Override
            public void onClick(View view, String data) {
                //跳转历史详情页面
                Intent intent = new Intent(getActivity(), HistoryDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
