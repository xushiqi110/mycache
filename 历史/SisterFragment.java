package com.ht.fragment.sister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ht.fragment.sister.adapter.SisterRecyclerAdapter;
import com.ht.fragment.sister.bean.SisterBean;
import com.ht.main.SisterDetails.SisterDetailsActivity;
import com.ht.main.bootmodule.R;
import com.ht.util.OkHttp;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Request;

/**
 * autour: 徐仕奇
 * action：类的作用：妹纸模块页面
 * date: 2016/12/17 0017 下午 12:01
 * update: 2016/12/17 0017
 */
public class SisterFragment extends Fragment {

    @InjectView(R.id.Sister_SRL_RecyclerView)
    RecyclerView SisterSRLRecyclerView;
    @InjectView(R.id.Sister_SRL)
    SwipeRefreshLayout SisterSRL;
    List<SisterBean.ResultsBean> list;
    public static SisterFragment newInstance() {
        SisterFragment fragment = new SisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_sister, container, false);
        ButterKnife.inject(this, mView);
        //设置SwipeRefreshView下拉刷新控件的属性
        setUpSwipeRefreshView();
        //网络请求数据
        requestDate();
        //设置主页列表
        setUpRecyclerView();
        return mView;
    }
    /**
     * 网络请求数据
     */
    private void requestDate(){
        String url="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/16/1";
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                SisterBean bean = gson.fromJson(result, SisterBean.class);
                list=bean.getResults();
                SisterRecyclerAdapter adapter=new SisterRecyclerAdapter(getActivity(),list);
                SisterSRLRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new SisterRecyclerAdapter.OnClickItemListener() {
                    @Override
                    public void onClick(View view, String data) {
                        Intent intent=new Intent(getActivity(), SisterDetailsActivity.class);
                        intent.putExtra("url",data);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    /**
     * 设置RecyclerView的属性
     */
    private void setUpRecyclerView() {
        //为recyclerview添加布局管理器
        SisterSRLRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        //为adapter注册条目点击监听
    }
    /**
     * 设置SwipeRefreshView下拉刷新控件的属性
     */
    private void setUpSwipeRefreshView() {

        // 设置下拉进度的主题颜色
        SisterSRL.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        SisterSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                SisterSRL.setRefreshing(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // 加载完数据设置为不刷新状态，将下拉进度收起来
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "刷新了一条数据", Toast.LENGTH_SHORT).show();
                        // 这个不能写在外边，不然会直接收起来
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        SisterSRL.setRefreshing(false);
                    }
                }, 1200);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
