package com.bw.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

import com.bw.adapter.ExampleApplication;
import com.bw.adapter.PhotoWallAdapter;
import com.bw.model.Images;
import com.squareup.leakcanary.RefWatcher;

/**
 * autour: 徐仕奇
 * action：类的作用:MainActivity实现了IMainView接口，实现了未实现的方法，
 *          在代码中可以看出MainActivity并没有做一些逻辑处理工作，
 *          数据处理的工作都是调用IMainPresenter
 * date: 2016/12/13 0013 下午 7:53
 * update: 2016/12/13 0013
 */
public class MainActivity extends AppCompatActivity
        implements IMainView {
    /**
     * 用于展示照片墙的GridView
     */
    private RecyclerView mPhotoWall;

    /**
     * GridView的适配器
     */
    private PhotoWallAdapter mAdapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageThumbSize = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_spacing);
        mPhotoWall = (RecyclerView) findViewById(R.id.photo_wall);
        mPhotoWall.setLayoutManager(new GridLayoutManager(this,3));
        mAdapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls,mPhotoWall);
        mPhotoWall.setAdapter(mAdapter);
        mPhotoWall.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        final int numColumns = (int) Math.floor(mPhotoWall
                                .getWidth()
                                / (mImageThumbSize + mImageThumbSpacing));
                        if (numColumns > 0) {
                            int columnWidth = (mPhotoWall.getWidth() / numColumns)
                                    - mImageThumbSpacing;
                            mAdapter.setItemHeight(columnWidth);
                            mPhotoWall.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    }
                });
    }
    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.fluchCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //使用 RefWatcher 监控那些本该被回收的对象
        RefWatcher refWatcher = ExampleApplication.getRefWatcher(this);
        refWatcher.watch(this);
        // 退出程序时结束所有的下载任务
        mAdapter.cancelAllTasks();
    }
}
