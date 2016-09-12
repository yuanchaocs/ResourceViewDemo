package com.feicuiedu.resourceviewdemo.demoa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.feicuiedu.resourceviewdemo.R;
import com.feicuiedu.resourceviewdemo.SimpleAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DemoAActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_a);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleAdapter = new SimpleAdapter();
        recyclerView.setAdapter(simpleAdapter);

        for (int i = 0; i < 100; i++) {
            simpleAdapter.addItem("我是第"+i+"个数据");
        }
        simpleAdapter.notifyDataSetChanged();
    }
}
