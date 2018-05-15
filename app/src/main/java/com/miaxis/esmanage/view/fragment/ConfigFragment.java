package com.miaxis.esmanage.view.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.miaxis.esmanage.R;
import com.miaxis.esmanage.view.IConfigView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigFragment extends BaseFragment implements IConfigView {

    private ConfigFragmentListener mListener;
    private ProgressDialog pdSaveDialog;

    public ConfigFragment() {
        // Required empty public constructor
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void alert(String message) {

    }

    @Override
    public void onConfigSaveSuccess() {
        mListener.onConfigSaveSuccess();
    }

    public interface ConfigFragmentListener {
        void onConfigSaveSuccess();
        void onConfigSaveCancel();
    }

}
