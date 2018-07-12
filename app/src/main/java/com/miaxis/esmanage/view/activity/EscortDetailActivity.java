package com.miaxis.esmanage.view.activity;

import android.app.ProgressDialog;
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
import com.facebook.drawee.view.SimpleDraweeView;
import com.miaxis.esmanage.R;
import com.miaxis.esmanage.app.EsManageApp;
import com.miaxis.esmanage.entity.Escort;
import com.miaxis.esmanage.presenter.IEscortDetailPresenter;
import com.miaxis.esmanage.presenter.impl.EscortDetailPresenter;
import com.miaxis.esmanage.util.CommonUtil;
import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.util.DateUtil;
import com.miaxis.esmanage.view.IEscortDetailView;
import com.miaxis.esmanage.view.custom.BottomMenu;

import java.io.File;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.miaxis.esmanage.util.Constant.INTENT_ESCORT_DETAIL_ESCORT;
import static com.miaxis.esmanage.util.Constant.INTENT_DETAIL_OP;
import static com.miaxis.esmanage.util.Constant.INTENT_EXTRA_COM_ID;
import static com.miaxis.esmanage.util.Constant.MODE_MOD;
import static com.miaxis.esmanage.util.Constant.REQUEST_CODE_GET_FINGER;

public class EscortDetailActivity extends BaseActivity implements IEscortDetailView {

    private static final int CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE = 97;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_escort_name)
    EditText etEscortName;
    @BindView(R.id.et_escort_code)
    EditText etEscortCode;
    @BindView(R.id.sdv_escort)
    SimpleDraweeView sdvEscort;
    @BindView(R.id.tv_escort_finger0)
    TextView tvEscortFinger0;
    @BindView(R.id.tv_escort_finger1)
    TextView tvEscortFinger1;
    @BindView(R.id.tv_escort_comp)
    TextView tvEscortComp;
    @BindView(R.id.et_escort_id_card)
    EditText etEscortIdCard;
    @BindView(R.id.et_escort_phone)
    EditText etEscortPhone;
    @BindView(R.id.ll_input_info)
    LinearLayout llInputInfo;
    @BindView(R.id.tv_escort_operator)
    TextView tvEscortOperator;

    private int mode;
    private BottomMenu bottomMenu;
    private View.OnClickListener menuListener;
    private boolean isCameraCapture;
    private String filePathCache;
    private Escort escort;

    private IEscortDetailPresenter presenter;
    private int compId;
    private ProgressDialog pdEscort;

    @Override
    protected int setContentView() {
        return R.layout.activity_escort_detail;
    }

    @Override
    protected void initData() {
        presenter = new EscortDetailPresenter(this);
        mode = getIntent().getIntExtra(INTENT_DETAIL_OP, -1);
        compId = getIntent().getIntExtra(INTENT_EXTRA_COM_ID, -1);
        switch (mode) {
            case Constant.MODE_ADD:
                escort = new Escort();
                escort.setComid(compId + "");
                break;
            case Constant.MODE_VIEW:
                escort = (Escort) getIntent().getSerializableExtra(INTENT_ESCORT_DETAIL_ESCORT);
                break;
        }
        menuListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_menu_1:
                        isCameraCapture = true;
                        bottomMenu.popupWindow.dismiss();
                        filePathCache = Environment.getExternalStorageDirectory() + "/esManage/escortPhoto/" + new Date().getTime() + ".jpg";
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
        switch (mode) {
            case -1:
                break;
            case Constant.MODE_ADD:
                toolbar.setTitle("新增押运员");
                presenter.findEscortComp(escort);
                break;
            case Constant.MODE_VIEW:
                toolbar.setTitle(escort.getEsname());
                presenter.findEscortComp(escort);
                setEditable(false);
                break;
        }
        pdEscort = new ProgressDialog(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        bottomMenu = new BottomMenu(this, menuListener);
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
                toolbar.setTitle("新增押运员");
                toolbar.getMenu().findItem(R.id.action_save).setVisible(true);
                toolbar.getMenu().findItem(R.id.action_mod).setVisible(false);
                toolbar.getMenu().findItem(R.id.action_del).setVisible(false);
                break;
            case Constant.MODE_VIEW:
                toolbar.setTitle(escort.getEsname());
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
                    addEscort();
                } else if (mode == Constant.MODE_MOD) {
                    modEscort();
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
                delEscort();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading(String message) {
        pdEscort.setMessage(message);
        if (!pdEscort.isShowing()) {
            pdEscort.show();
        }
    }

    @Override
    public void hideLoading() {
        if (pdEscort.isShowing()) {
            pdEscort.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case CAPTURE_PHOTO_ACTIVITY_REQUEST_CODE:
                if (isCameraCapture) {
                    if (resultCode == RESULT_OK) {
                        updateEscortPhoto(filePathCache);
                    }
                } else {
                    if (resultCode == RESULT_OK) {
                        try {
                            Uri originalUri = data.getData();
                            updateEscortPhoto(CommonUtil.getFileByUri(originalUri, this).getAbsolutePath());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                break;
            case REQUEST_CODE_GET_FINGER:
                if (data != null) {
                    int fingerPosition = data.getIntExtra(Constant.INTENT_EXTRA_FINGER_POSITION, -1);
                    String fingerMb = data.getStringExtra(Constant.INTENT_EXTRA_FINGER_DATA);
                    if (fingerPosition == 0) {
                        escort.setFinger0(fingerMb);
                        tvEscortFinger0.setText("指纹一\r\n（已采集）");
                        tvEscortFinger0.setTextColor(getResources().getColor(R.color.darkgreen));
                    } else if (fingerPosition == 1) {
                        escort.setFinger1(fingerMb);
                        tvEscortFinger1.setText("指纹二\r\n（已采集）");
                        tvEscortFinger1.setTextColor(getResources().getColor(R.color.darkgreen));
                    }
                }
                break;
        }
    }

    @Override
    public void updateEscortInfo(Escort escort) {
        etEscortName.setText(escort.getEsname());
        String esCode = escort.getEscode();
        if (!TextUtils.isEmpty(esCode)) {
            etEscortCode.setText(esCode.split("-")[1]);
        }
        tvEscortComp.setText(escort.getCompname());
        etEscortIdCard.setText(escort.getIdcard());
        etEscortPhone.setText(escort.getPhoneno());
        if (TextUtils.isEmpty(escort.getFinger0())) {
            tvEscortFinger0.setText("指纹一\r\n（未采集）");
            tvEscortFinger0.setTextColor(getResources().getColor(R.color.dark));
        } else {
            tvEscortFinger0.setText("指纹一\r\n（已采集）");
            tvEscortFinger0.setTextColor(getResources().getColor(R.color.darkgreen));
        }
        if (TextUtils.isEmpty(escort.getFinger1())) {
            tvEscortFinger1.setText("指纹二\r\n（未采集）");
            tvEscortFinger1.setTextColor(getResources().getColor(R.color.dark));
        } else {
            tvEscortFinger1.setText("指纹二\r\n（已采集）");
            tvEscortFinger1.setTextColor(getResources().getColor(R.color.darkgreen));
        }
        if (!TextUtils.isEmpty(escort.getPhotoUrl())) {
            Uri uri = Uri.parse(escort.getPhotoUrl());
            sdvEscort.setImageURI(uri);
        }

    }

    @Override
    public void updateEscortPhoto(String photoUrl) {
        escort.setPhotoUrl(photoUrl);
        sdvEscort.setImageURI(Uri.fromFile(new File(photoUrl)));
    }

    @Override
    public void updateFinger(int position, String finger, boolean getFingerSuccess) {

    }

    @Override
    public void setEditable(boolean editable) {
        etEscortName.setEnabled(editable);
        etEscortCode.setEnabled(editable);
        etEscortIdCard.setEnabled(editable);
        etEscortPhone.setEnabled(editable);
        sdvEscort.setEnabled(editable);
        tvEscortFinger0.setEnabled(editable);
        tvEscortFinger1.setEnabled(editable);
        tvEscortComp.setEnabled(editable);
        if (editable) {
            etEscortName.requestFocus();
        }
        if (mode == MODE_MOD) {
            etEscortCode.setEnabled(false);
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

    @OnClick(R.id.sdv_escort)
    void onPhotoClick() {
        bottomMenu.show();
    }

    @OnClick(R.id.tv_escort_finger0)
    void onGetFinger0Click() {
        Intent i = new Intent(this, FingerActivity.class);
        i.putExtra(Constant.INTENT_EXTRA_FINGER_POSITION, 0);
        startActivityForResult(i, Constant.REQUEST_CODE_GET_FINGER);
    }

    @OnClick(R.id.tv_escort_finger1)
    void onGetFinger1Click() {
        Intent i = new Intent(this, FingerActivity.class);
        i.putExtra(Constant.INTENT_EXTRA_FINGER_POSITION, 1);
        startActivityForResult(i, Constant.REQUEST_CODE_GET_FINGER);
    }

    private void addEscort() {
        escort.setEsname(etEscortName.getText().toString().trim());
        escort.setEscode("abc-" + etEscortCode.getText().toString().trim());
        escort.setCompname(tvEscortComp.getText().toString().trim());
        escort.setIdcard(etEscortIdCard.getText().toString().trim());
        escort.setPhoneno(etEscortPhone.getText().toString().trim());
        escort.setOpdate(DateUtil.toAll(new Date()));
        escort.setOpuser(EsManageApp.getInstance().getCurUserCode());
        escort.setOpusername(EsManageApp.getInstance().getCurUserCode());
        presenter.addEscort(escort);
    }

    private void modEscort() {
        escort.setEsname(etEscortName.getText().toString().trim());
        escort.setEscode("abc-" + etEscortCode.getText().toString().trim());
        escort.setCompname(tvEscortComp.getText().toString().trim());
        escort.setIdcard(etEscortIdCard.getText().toString().trim());
        escort.setPhoneno(etEscortPhone.getText().toString().trim());
        escort.setOpdate(DateUtil.toAll(new Date()));
        escort.setOpuser(EsManageApp.getInstance().getCurUserCode());
        escort.setOpusername(EsManageApp.getInstance().getCurUserCode());
        presenter.modEscort(escort);
    }

    private void delEscort() {
        final MaterialDialog d = new MaterialDialog.Builder(this)
                .content("您确定要删除吗？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        presenter.delEscort(escort);
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

}
