package com.miaxis.esmanage.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.device.Device;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miaxis.esmanage.R;
import com.miaxis.esmanage.app.EsManageApp;
import com.miaxis.esmanage.entity.Car;
import com.miaxis.esmanage.presenter.ICarDetailPresenter;
import com.miaxis.esmanage.presenter.impl.CarDetailPresenter;
import com.miaxis.esmanage.util.CommonUtil;
import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.util.DateUtil;
import com.miaxis.esmanage.view.ICarDetailView;
import com.miaxis.esmanage.view.custom.BottomMenu;

import java.io.File;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.miaxis.esmanage.util.Constant.INTENT_ESCORT_DETAIL_CAR;
import static com.miaxis.esmanage.util.Constant.INTENT_DETAIL_OP;
import static com.miaxis.esmanage.util.Constant.INTENT_EXTRA_COM_ID;
import static com.miaxis.esmanage.util.Constant.MODE_ADD;
import static com.miaxis.esmanage.util.Constant.MODE_MOD;

public class CarDetailActivity extends BaseActivity implements ICarDetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_car_plate_no)
    EditText etCarPlateNo;
    @BindView(R.id.et_car_code)
    EditText etCarCode;
    @BindView(R.id.sdv_car)
    SimpleDraweeView sdvCar;
    @BindView(R.id.tv_car_rfid)
    TextView tvCarRfid;
    @BindView(R.id.tv_car_comp)
    TextView tvCarComp;
    @BindView(R.id.tv_car_operator)
    TextView tvCarOperator;
    @BindView(R.id.et_car_remark)
    EditText etCarRemark;
    @BindView(R.id.ll_input_info)
    LinearLayout llInputInfo;

    private static final int CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE = 97;
    private int mode;           // 1001 mod or  1002 add
    private BottomMenu bottomMenu;
    private View.OnClickListener menuListener;
    private boolean isCameraCapture;
    private String filePathCache;

    private Car mCar;
    private ICarDetailPresenter presenter;
    private ProgressDialog pdCar;
    private int compId;

    @Override
    protected int setContentView() {
        return R.layout.activity_car_detail;
    }

    @Override
    protected void initData() {
        presenter = new CarDetailPresenter(this);
        mode = getIntent().getIntExtra(INTENT_DETAIL_OP, -1);
        compId = getIntent().getIntExtra(INTENT_EXTRA_COM_ID, -1);
        switch (mode) {
            case Constant.MODE_ADD:
                mCar = new Car();
                mCar.setCompid(compId);
                break;
            case Constant.MODE_VIEW:
                mCar = (Car) getIntent().getSerializableExtra(INTENT_ESCORT_DETAIL_CAR);
                break;
        }
        menuListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_menu_1:
                        isCameraCapture = true;
                        bottomMenu.popupWindow.dismiss();
                        filePathCache = Environment.getExternalStorageDirectory() + "/esManage/carPhoto/" + new Date().getTime() + ".jpg";
                        File vFile = new File(filePathCache);
                        if (!vFile.exists()) {
                            File vDirPath = vFile.getParentFile(); //new File(vFile.getParent());
                            vDirPath.mkdirs();
                        }
                        Uri uri = Uri.fromFile(vFile);
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(i, CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE);
                        break;
                    case R.id.btn_menu_2:
                        isCameraCapture = false;
                        bottomMenu.popupWindow.dismiss();
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE);
                        break;
                    case R.id.btn_menu_3:
                        bottomMenu.popupWindow.dismiss();
                        break;
                }
            }
        };
    }

    @Override
    protected void initView() {
        pdCar = new ProgressDialog(this);
        pdCar.setCanceledOnTouchOutside(false);
        pdCar.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Device.cancel();
                byte[] message = new byte[200];
                Device.closeRfid(message);
                Device.closeFinger(message);
            }
        });

        switch (mode) {
            case -1:
                break;
            case Constant.MODE_ADD:
                toolbar.setTitle("新增车辆");
                showCarInfo(mCar);
                presenter.findCarComp(mCar);
                break;
            case Constant.MODE_VIEW:
                toolbar.setTitle(mCar.getPlateno());
                setEditable(false);
                showCarInfo(mCar);
                presenter.findCarComp(mCar);
                break;
        }

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        bottomMenu = new BottomMenu(this, menuListener);

    }

    @Override
    public void showLoading(String message) {
        pdCar.setMessage(message);
        if (!pdCar.isShowing()) {
            pdCar.show();
        }
    }

    @Override
    public void hideLoading() {
        if (pdCar.isShowing()) {
            pdCar.dismiss();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_op, menu);
        switch (mode) {
            case -1:
                break;
            case Constant.MODE_ADD:
                toolbar.setTitle("新增车辆");
                toolbar.getMenu().findItem(R.id.action_save).setVisible(true);
                toolbar.getMenu().findItem(R.id.action_mod).setVisible(false);
                toolbar.getMenu().findItem(R.id.action_del).setVisible(false);
                break;
            case Constant.MODE_VIEW:
                toolbar.setTitle(mCar.getPlateno());
                toolbar.getMenu().findItem(R.id.action_save).setVisible(false);
                toolbar.getMenu().findItem(R.id.action_mod).setVisible(true);
                toolbar.getMenu().findItem(R.id.action_del).setVisible(true);
                setEditable(false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                if (mode == Constant.MODE_ADD) {
                    addCar();
                } else if (mode == Constant.MODE_MOD) {
                    modCar();
                }
                break;
            case R.id.action_mod:
                mode = Constant.MODE_MOD;
                toolbar.getMenu().findItem(R.id.action_save).setVisible(true);
                toolbar.getMenu().findItem(R.id.action_mod).setVisible(false);
                toolbar.getMenu().findItem(R.id.action_del).setVisible(false);
                setEditable(true);
                break;
            case R.id.action_del:
                delCar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE:
                if (isCameraCapture) {
                    if (resultCode == RESULT_OK) {
                        updateCarPhoto(filePathCache);
                    }
                } else {
                    if (resultCode == RESULT_OK) {
                        try {
                            Uri originalUri = data.getData();
                            updateCarPhoto(CommonUtil.getFileByUri(originalUri, this).getAbsolutePath());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void showCarInfo(Car car) {
        etCarPlateNo.setText(car.getPlateno());
        if (!TextUtils.isEmpty(car.getCarcode())) {
            etCarCode.setText(car.getCarcode().split("-")[1]);
        }
        tvCarRfid.setText(car.getRfid());
        tvCarComp.setText(car.getCompname());
        tvCarOperator.setText(car.getOpusername());
        etCarRemark.setText(car.getRemark());
        if (!TextUtils.isEmpty(car.getCarphoto())) {
            Uri uri = Uri.parse(car.getCarphoto());
            sdvCar.setImageURI(uri);
        }
    }

    @Override
    public void onSaveSuccess() {
        switch (mode) {
            case Constant.MODE_ADD:
                alert("添加成功！");
                finish();
                break;
            case Constant.MODE_MOD:
                alert("保存成功！");
                setEditable(false);
                toolbar.getMenu().findItem(R.id.action_save).setVisible(false);
                toolbar.getMenu().findItem(R.id.action_mod).setVisible(true);
                toolbar.getMenu().findItem(R.id.action_del).setVisible(true);
                break;
        }
    }

    @Override
    public void onDelSuccess() {
        finish();
    }

    @Override
    public void updateCarPhoto(String photoUrl) {
        mCar.setCarphoto(photoUrl);
        sdvCar.setImageURI(Uri.fromFile(new File(photoUrl)));
    }

    @Override
    public void setEditable(boolean editable) {
        etCarPlateNo.setEnabled(editable);
        etCarCode.setEnabled(editable);
        tvCarRfid.setEnabled(editable);
        tvCarComp.setEnabled(editable);
        etCarRemark.setEnabled(editable);
        sdvCar.setEnabled(editable);
        if (editable) {
            etCarPlateNo.requestFocus();
        }
        if (mode == MODE_MOD) {
            etCarCode.setEnabled(false);
        }
    }

    @Override
    public void showRfid(String rfid) {
        tvCarRfid.setText(rfid);
    }

    private void addCar() {
        mCar.setPlateno(etCarPlateNo.getText().toString().trim());
        mCar.setCarcode("abc-" + etCarCode.getText().toString().trim());
        mCar.setRfid(tvCarRfid.getText().toString().trim());
        mCar.setOpdate(DateUtil.toAll(new Date()));
        mCar.setOpuser(EsManageApp.getInstance().getCurUserCode());
        mCar.setOpusername(EsManageApp.getInstance().getCurUserCode());
        mCar.setRemark(etCarRemark.getText().toString().trim());
        presenter.addCar(mCar);
    }

    private void modCar() {
        mCar.setPlateno(etCarPlateNo.getText().toString().trim());
        mCar.setCarcode("abc-" + etCarCode.getText().toString().trim());
        mCar.setRfid(tvCarRfid.getText().toString().trim());
        mCar.setOpdate(DateUtil.toAll(new Date()));
        mCar.setRemark(etCarRemark.getText().toString().trim());
        mCar.setOpuser(EsManageApp.getInstance().getCurUserCode());
        mCar.setOpusername(EsManageApp.getInstance().getCurUserCode());
        presenter.modCar(mCar);
    }

    private void delCar() {
        final MaterialDialog d = new MaterialDialog.Builder(this)
                .content("您确定要删除吗？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        presenter.delCar(mCar);
                        dialog.dismiss();
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

    @OnClick(R.id.sdv_car)
    void onGetPhotoClick() {
        bottomMenu.show();
    }

    @OnClick(R.id.tv_car_rfid)
    void onGetRfidClick() {
        presenter.getRfid();
    }
}
