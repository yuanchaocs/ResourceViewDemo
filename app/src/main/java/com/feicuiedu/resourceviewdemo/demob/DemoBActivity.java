package com.feicuiedu.resourceviewdemo.demob;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.feicuiedu.resourceviewdemo.R;
import com.feicuiedu.resourceviewdemo.SimpleAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DemoBActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)SwipeRefreshLayout swipeRefreshLayout;

    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_b);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleAdapter = new SimpleAdapter();
        recyclerView.setAdapter(simpleAdapter);

        // 下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(this);
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
}
