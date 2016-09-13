package com.feicuiedu.resourceviewdemo.demod;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.feicuiedu.resourceviewdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DemoDActivity extends AppCompatActivity {

    @Bind(R.id.baseResourceView) BaseResourceView baseResourceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_d);
        ButterKnife.bind(this);
        baseResourceView.autoRefresh();
    }
}
