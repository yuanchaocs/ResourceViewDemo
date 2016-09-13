package com.feicuiedu.resourceviewdemo.democ;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.feicuiedu.resourceviewdemo.R;
import com.feicuiedu.resourceviewdemo.SimpleAdapter;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DemoCActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        MugenCallbacks {

    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progressBar)ProgressBar progressBar;

    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_c);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleAdapter = new SimpleAdapter();
        recyclerView.setAdapter(simpleAdapter);

        // 下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(this);
        // 上拉处理(Mugen库)
        Mugen.with(recyclerView,this).start();
    }

    @Override public void onRefresh() {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                simpleAdapter.clear();
                for (int i = 0; i < 20; i++) {
                    simpleAdapter.addItem("我是新刷新到的第"+i+"个数据");
                }
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        simpleAdapter.notifyDataSetChanged();
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
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 20; i++) {
                    simpleAdapter.addItem("我是新加载到的第"+i+"个数据");
                }
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        simpleAdapter.notifyDataSetChanged();
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