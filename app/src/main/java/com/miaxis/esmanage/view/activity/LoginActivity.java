package com.miaxis.esmanage.view.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.miaxis.esmanage.R;
import com.miaxis.esmanage.presenter.ILoginPresenter;
import com.miaxis.esmanage.presenter.impl.LoginPresenter;
import com.miaxis.esmanage.view.ILoginView;
import com.miaxis.esmanage.view.fragment.ConfigFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView, ConfigFragment.ConfigFragmentListener{

    @BindView(R.id.iv_config)
    ImageView ivConfig;
    @BindView(R.id.fl_config)
    FrameLayout flConfig;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.cd_login)
    CardView cdLogin;

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter.checkConfig();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void alert(String message) {

    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFail(String failMessage) {

    }

    @Override
    public void showConfig() {
        flConfig.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideConfig() {
        flConfig.setVisibility(View.GONE);
    }

    @Override
    public void showLogin() {
        cdLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLogin() {
        cdLogin.setVisibility(View.GONE);
    }

    @OnClick
    void onIvConfigClick() {
        if (cdLogin.getVisibility() == View.VISIBLE) {
            cdLogin.setVisibility(View.GONE);
            flConfig.setVisibility(View.VISIBLE);
        } else {
            loginPresenter.checkConfig();
        }

    }

    @Override
    public void onConfigSaveSuccess() {

    }

    @Override
    public void onConfigSaveCancel() {

    }
}
