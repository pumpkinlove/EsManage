package com.miaxis.esmanage.view.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.miaxis.esmanage.R;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.presenter.IConfigPresenter;
import com.miaxis.esmanage.presenter.impl.ConfigPresenter;
import com.miaxis.esmanage.view.IConfigView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigFragment extends BaseFragment implements IConfigView {

    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.et_port)
    EditText etPort;
    @BindView(R.id.et_orgCode)
    EditText etOrgCode;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    Unbinder unbinder;
    private ConfigFragmentListener mListener;
    private ProgressDialog pdSaveConfig;

    private IConfigPresenter configPresenter;

    public ConfigFragment() {
        // Required empty public constructor
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initData() {
        configPresenter = new ConfigPresenter(this);
    }

    @Override
    protected void initView() {
        pdSaveConfig = new ProgressDialog(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        configPresenter.loadConfig();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConfigFragmentListener) {
            mListener = (ConfigFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConfigClickListener");
        }
    }

    @Override
    public void showLoading(String message) {
        pdSaveConfig.setMessage(message);
        if (!pdSaveConfig.isShowing()) {
            pdSaveConfig.show();
        }
    }

    @Override
    public void hideLoading() {
        if (pdSaveConfig.isShowing()) {
            pdSaveConfig.dismiss();
        }
    }

    @Override
    public void alert(String message) {
        MaterialDialog md = new MaterialDialog.Builder(getContext())
                .content(message)
                .build();
        md.show();
    }

    @Override
    public void showConfig(Config config) {
        etIp.setText(config.getIp());
        etPort.setText(config.getPort());
        etOrgCode.setText(config.getOrgCode());
    }

    @Override
    public void onConfigSaveSuccess() {
        mListener.onConfigSaveSuccess();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface ConfigFragmentListener {
        void onConfigSaveSuccess();
        void onConfigSaveCancel();
    }

    @OnClick(R.id.btn_confirm)
    void onConfirmClick() {
        String ip = etIp.getText().toString().trim();
        String port = etPort.getText().toString().trim();
        String orgCode = etOrgCode.getText().toString().trim();
        if (TextUtils.isEmpty(ip)) {
            Toast.makeText(getContext(), "IP地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(port)) {
            Toast.makeText(getContext(), "端口号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(orgCode)) {
            Toast.makeText(getContext(), "机构编号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        configPresenter.saveConfig(ip, port, orgCode);
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        mListener.onConfigSaveCancel();
    }

}
