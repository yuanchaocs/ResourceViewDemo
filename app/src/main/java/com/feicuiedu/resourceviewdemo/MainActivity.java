package com.feicuiedu.resourceviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feicuiedu.resourceviewdemo.demoa.DemoAActivity;
import com.feicuiedu.resourceviewdemo.demob.DemoBActivity;
import com.feicuiedu.resourceviewdemo.democ.DemoCActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.listview) ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String[] datas = {"RecyclerView基本运用", "下拉刷新","上拉加载"};
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(arrayAdapter);
    }

    @OnItemClick(R.id.listview)
    public void onItemClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, DemoAActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, DemoBActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, DemoCActivity.class);
                startActivity(intent);
                break;
            default:
                throw new RuntimeException("error");
        }
    }
}
