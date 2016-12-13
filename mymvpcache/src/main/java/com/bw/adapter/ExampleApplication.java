package com.bw.adapter;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * autour: 徐仕奇
 * action：类的作用:LeakCanary.install() 会返回一个预定义的 RefWatcher，
 *          同时也会启用一个 ActivityRefWatcher，用于自动监控调用
 *          Activity.onDestroy() 之后泄露的 activity。
 * date: 2016/12/13 0013 下午 9:16
 * update: 2016/12/13 0013
 */
public class ExampleApplication extends Application {
    public static RefWatcher getRefWatcher(Context context) {
        ExampleApplication application = (ExampleApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }
}
