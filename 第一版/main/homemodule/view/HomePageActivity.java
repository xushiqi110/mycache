package com.ht.main.homemodule.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ht.main.bootmodule.R;
/**
 * autour: 徐仕奇
 * action：类的作用
 * date: 2016/12/14 0014 下午 8:11
 * update: 2016/12/14 0014
 */
public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);//设置导航栏图标
    }
}
