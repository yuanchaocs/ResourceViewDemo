package com.feicuiedu.resourceviewdemo.demod;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * 作者：yuanchao on 2016/9/13 0013 11:34
 * 邮箱：yuanchao@feicuiedu.com
 */
public abstract class BaseItemView<Model> extends FrameLayout{

    public BaseItemView(Context context) {
        super(context);
        initView();
    }

    protected abstract void initView();

    protected abstract void bindModel(Model model);
}
