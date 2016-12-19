package com.ht.fragment.history;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ht.fragment.history.adapter.HistoryRecyclerViewAdapter;
import com.ht.fragment.history.bean.HistoryBean;
import com.ht.main.bootmodule.R;
import com.ht.main.historydetails.HistoryDetailsActivity;
import com.ht.util.OkHttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Request;

/**
 * autour: 徐仕奇
 * action：类的作用：历史上的今天的模块页面
 * date: 2016/12/16 0016 下午 2:34
 * update: 2016/12/16 0016
 */
public class HistoryFragment extends Fragment {

    @InjectView(R.id.history_CL_SwipeRefresh_Appbar_Toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.history_CL_SwipeRefresh_Appbar)
    AppBarLayout mAppbar;
    @InjectView(R.id.history_CL_SwipeRefresh_NestedScrollView_RecyclerView)
    RecyclerView mRecyclerView;
    @InjectView(R.id.history_CL_SwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @InjectView(R.id.histery_CL_FABotton)
    FloatingActionButton mFABotton;

    List<HistoryBean.ResultBean> list=new ArrayList();
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.inject(this, mView);
        //设置SwipeRefreshView下拉刷新控件的属性
        setUpSwipeRefreshView();
        //数据请求
        Historydata();

        return mView;
    }

    private void Historydata() {
        String url="http://v.juhe.cn/todayOnhistory/queryEvent.php?key=69a7eeba7869f8bdcdee7b2bc3bb5aa2&date=11/1";
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson=new Gson();
                HistoryBean bean=gson.fromJson(result,HistoryBean.class);
                list=bean.getResult();
                //设置主页列表
                setUpRecyclerView();
            }
        });
    }

    /**
     * 设置RecyclerView的属性
     */
    private void setUpRecyclerView() {
        LinearLayoutManager mManager=new LinearLayoutManager(getActivity());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        //为recyclerview添加布局管理器
        mRecyclerView.setLayoutManager(mManager);
        HistoryRecyclerViewAdapter adapter=new HistoryRecyclerViewAdapter(getActivity(),list);
        mRecyclerView.setAdapter(adapter);
        //为adapter注册条目点击监听
        adapter.setOnItemClickListener(new HistoryRecyclerViewAdapter.OnClickItemListener() {
            @Override
            public void onClick(View view, String e_id) {
                //跳转历史详情页面
                Intent intent=new Intent(getActivity(), HistoryDetailsActivity.class);
                intent.putExtra("e_id",e_id);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置SwipeRefreshView下拉刷新控件的属性
     */
    private void setUpSwipeRefreshView() {

        // 设置下拉进度的主题颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                mSwipeRefresh.setRefreshing(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // 加载完数据设置为不刷新状态，将下拉进度收起来
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "刷新了一条数据", Toast.LENGTH_SHORT).show();
                        // 这个不能写在外边，不然会直接收起来
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        mSwipeRefresh.setRefreshing(false);
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
