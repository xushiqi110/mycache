package com.ht.fragment.about;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ht.fragment.about.adapter.AboutRecyclerAdapter;
import com.ht.main.bootmodule.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * autour: 徐仕奇
 * action：类的作用:关于模块页面
 * date: 2016/12/17 0017 下午 12:06
 * update: 2016/12/17 0017
 */
public class AboutFragment extends Fragment {

    @InjectView(R.id.about_img)
    ImageView aboutImg;
    @InjectView(R.id.about_recycler)
    RecyclerView aboutRecycler;

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.inject(this, mView);
        setUpRecyclerView();
        aboutImg.setImageResource(R.mipmap.default_img);
        return mView;
    }

    /**
     * 设置RecyclerView的属性
     */
    private void setUpRecyclerView() {
        LinearLayoutManager mManager = new LinearLayoutManager(getActivity());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        //为recyclerview添加布局管理器
        aboutRecycler.setLayoutManager(mManager);
        AboutRecyclerAdapter adapter = new AboutRecyclerAdapter(getActivity());
        aboutRecycler.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
