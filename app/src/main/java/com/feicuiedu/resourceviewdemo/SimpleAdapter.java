package com.feicuiedu.resourceviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：yuanchao on 2016/9/12 0012 16:55
 * 邮箱：yuanchao@feicuiedu.com
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.DemoViewHolder> {

    private final ArrayList<String> datas = new ArrayList<>();

    public void clear() {
        datas.clear();
    }

    public void addItem(String data) {
        datas.add(data);
    }

    @Override public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        return new DemoViewHolder(view);
    }

    @Override public void onBindViewHolder(DemoViewHolder holder, int position) {
        final String data = datas.get(position);
        holder.textView.setText(data);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(v.getContext(), data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override public int getItemCount() {
        return datas.size();
    }

    static class DemoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textView) TextView textView;

        public DemoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
