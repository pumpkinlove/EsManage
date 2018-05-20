package com.miaxis.esmanage.view.activity;

import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.miaxis.esmanage.view.IBaseView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public abstract class BaseActivity extends RxAppCompatActivity implements IBaseView {

    private Unbinder unbinder;
    private MaterialDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        unbinder = ButterKnife.bind(this);
        initData();
        initView();
    }

    protected abstract int setContentView();

    protected abstract void initData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void alert(String message) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .content(message)
                .build();
        dialog.show();
    }
}