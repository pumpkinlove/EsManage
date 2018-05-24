package com.miaxis.esmanage.view.fragment;

import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.miaxis.esmanage.R;
import com.miaxis.esmanage.app.EsManageApp;
import com.miaxis.esmanage.model.local.greenDao.gen.DaoMaster;
import com.miaxis.esmanage.view.ISystemView;

import java.util.Objects;

import butterknife.OnClick;

public class SystemFragment extends BaseFragment implements ISystemView {

    public SystemFragment() {
        // Required empty public constructor
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initData() {

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

    @OnClick(R.id.ll_logout)
    void onSignOutClick() {
        final MaterialDialog d = new MaterialDialog.Builder(Objects.requireNonNull(getContext()))
                .content("您确定要退出吗？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        Objects.requireNonNull(getActivity()).finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        d.show();
    }

    @OnClick(R.id.ll_clear_all)
    void onClearAllClick() {
        final MaterialDialog d = new MaterialDialog.Builder(Objects.requireNonNull(getContext()))
                .content("恢复出厂设置将会清除所有数据，您确定要恢复出厂设置吗？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        DaoMaster.dropAllTables(EsManageApp.getInstance().getDaoSession().getDatabase(), true);
                        DaoMaster.createAllTables(EsManageApp.getInstance().getDaoSession().getDatabase(), true);
                        dialog.dismiss();
                        Objects.requireNonNull(getActivity()).finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        d.show();
    }

}
