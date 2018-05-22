package com.miaxis.esmanage.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.miaxis.esmanage.MainActivity;
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
    private ProgressDialog pdLogin;

    @Override
    protected void onResume() {
        super.onResume();
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
        pdLogin = new ProgressDialog(this);
    }

    @Override
    public void showLoading(String message) {
        pdLogin.setMessage(message);
        if (!pdLogin.isShowing()) {
            pdLogin.show();
        }
    }

    @Override
    public void hideLoading() {
        if (pdLogin.isShowing()) {
            pdLogin.dismiss();
        }
    }

    @Override
    public void onLoginSuccess() {
        Intent iToMain = new Intent(this, MainActivity.class);
        startActivity(iToMain);
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

    @OnClick(R.id.iv_config)
    void onIvConfigClick() {
        if (cdLogin.getVisibility() == View.VISIBLE) {
            cdLogin.setVisibility(View.GONE);
            flConfig.setVisibility(View.VISIBLE);
        } else {
            loginPresenter.checkConfig();
        }
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
//        onLoginSuccess();
        String account = etUsername.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        loginPresenter.doLogin(account, pwd);
    }

    @Override
    public void onConfigSaveSuccess() {
        cdLogin.setVisibility(View.VISIBLE);
        flConfig.setVisibility(View.GONE);
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigSaveCancel() {
        loginPresenter.checkConfig();
    }

}
