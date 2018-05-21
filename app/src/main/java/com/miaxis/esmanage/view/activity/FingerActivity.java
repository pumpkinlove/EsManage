package com.miaxis.esmanage.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.device.Device;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miaxis.esmanage.R;
import com.miaxis.esmanage.presenter.IFingerPresenter;
import com.miaxis.esmanage.presenter.impl.FingerPresenter;
import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.view.IFingerView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.miaxis.esmanage.util.Constant.INTENT_EXTRA_FINGER_DATA;

public class FingerActivity extends BaseActivity implements IFingerView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sdv_finger)
    SimpleDraweeView sdvFinger;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    private IFingerPresenter fingerPresenter;
    private int fingerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fingerPresenter.getFinger();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_finger;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Device.cancel();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        fingerPresenter = new FingerPresenter(this);
        fingerPosition = getIntent().getIntExtra(Constant.INTENT_EXTRA_FINGER_POSITION, -1);
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("采集指纹");
    }


    @Override
    public void showLoading(String message) {
        tvMessage.setText(message);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void updateImage(byte[] fingerImgData) {
        Bitmap bmpFinger = BitmapFactory.decodeByteArray(fingerImgData, 0, fingerImgData.length);
        sdvFinger.setImageBitmap(bmpFinger);
    }

    @Override
    public void onGetFinger(String mb) {
        Intent i = new Intent();
        i.putExtra(INTENT_EXTRA_FINGER_DATA, mb);
        i.putExtra(Constant.INTENT_EXTRA_FINGER_POSITION, fingerPosition);
        setResult(Constant.REQUEST_CODE_GET_FINGER, i);
        finish();
    }

    @OnClick(R.id.btn_cancel)
    void onCancel() {
        Device.cancel();
    }

}
