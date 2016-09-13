package com.feicuiedu.resourceviewdemo.demod;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.feicuiedu.resourceviewdemo.R;
import com.feicuiedu.resourceviewdemo.SimpleAdapter;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：yuanchao on 2016/9/13 0013 10:54
 * 邮箱：yuanchao@feicuiedu.com
 */
public class BaseResourceView extends FrameLayout implements
        SwipeRefreshLayout.OnRefreshListener,
        MugenCallbacks {
    public BaseResourceView(Context context) {
        this(context, null);
    }

    public BaseResourceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseResourceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progressBar)ProgressBar progressBar;

    protected SimpleAdapter adapter; // 数据适配器

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.base_resource_view, this, true);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SimpleAdapter();
        recyclerView.setAdapter(adapter);
        // 下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(this);
        // 上拉处理(Mugen库)
        Mugen.with(recyclerView, this).start();
    }

    public void autoRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override public void onRefresh() {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                post(new Runnable() {
                    @Override public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override public void onLoadMore() {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                post(new Runnable() {
                    @Override public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    @Override public boolean isLoading() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    @Override public boolean hasLoadedAllItems() {
        return false;
    }
}
