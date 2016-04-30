package com.xiao.nanshi_check.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ZzZz on 2016/4/30/0030.
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
    }

    /**
     * 查找view
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化view
     */
    protected abstract void initView();

}
