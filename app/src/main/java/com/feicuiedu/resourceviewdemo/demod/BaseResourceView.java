package com.feicuiedu.resourceviewdemo.demod;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.feicuiedu.resourceviewdemo.R;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 带下拉刷新及上拉加载的自定义视图,以及完成数据加载和数据适配显示的核心业务流程
 * <p/>
 * 列表视图使用 {@link RecyclerView}实现
 * <p/>
 * 下拉刷新使用 {@link SwipeRefreshLayout}实现
 * <p/>
 * 分页加载使用 {@link Mugen} + {@link ProgressBar} 实现
 * <p/>
 * 作者：yuanchao on 2016/9/13 0013 10:54
 * 邮箱：yuanchao@feicuiedu.com
 */
public abstract class BaseResourceView<Model, ItemView extends BaseItemView<Model>> extends FrameLayout implements
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
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progressBar) ProgressBar progressBar;

    protected ModelAdapter adapter; // 数据适配器

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.base_resource_view, this, true);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ModelAdapter();
        recyclerView.setAdapter(adapter);
        // 下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(this);
        // 上拉处理(Mugen库)
        Mugen.with(recyclerView, this).start();
    }

    public void autoRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    protected abstract List<Model> queryData(int limit, int skip);

    protected abstract int getLimit();

    protected abstract ItemView createItemView();

    private int skip = 0;

    @Override public void onRefresh() {
        List<Model> models = queryData(getLimit(), 0);
//        if (数据 == 成功加载到) {
//            skip = models.size();
//            adapter.clear();
//            adapter.addData(models);
//        }
    }

    @Override public void onLoadMore() {
        progressBar.setVisibility(View.VISIBLE);
        List<Model> models = queryData(getLimit(), skip);
//        if (数据 == 成功加载到) {
//            skip = skip + models.size();
//            adapter.addData(models);
//        }
    }

    @Override public boolean isLoading() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    @Override public boolean hasLoadedAllItems() {
        return false;
    }

    protected class ModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final ArrayList<Model> dataSet = new ArrayList<>();

        public void clear() {
            dataSet.clear();
            notifyDataSetChanged();
        }

        public void addData(Collection<Model> data) {
            dataSet.addAll(data);
            notifyDataSetChanged();
        }

        @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemView itemView = createItemView();
            return new RecyclerView.ViewHolder(itemView) {
            };
        }

        @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Model model = dataSet.get(position);
            ItemView itemView = (ItemView) holder.itemView;
            itemView.bindModel(model);
        }

        @Override public int getItemCount() {
            return dataSet.size();
        }
    }
}
