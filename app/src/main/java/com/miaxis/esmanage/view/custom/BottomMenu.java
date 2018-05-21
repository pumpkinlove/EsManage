package com.miaxis.esmanage.view.custom;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.miaxis.esmanage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu.nan on 2016/9/21.
 */
public class BottomMenu implements View.OnTouchListener {

    @BindView(R.id.btn_menu_1)
    TextView btnMenu1;
    @BindView(R.id.btn_menu_2)
    TextView btnMenu2;
    @BindView(R.id.btn_menu_3)
    TextView btnMenu3;
    @BindView(R.id.pop_layout)
    LinearLayout popLayout;

    private Activity context;
    private View.OnClickListener listener;

    public PopupWindow popupWindow;

    public BottomMenu(Activity context, View.OnClickListener listener) {

        this.context = context;
        this.listener = listener;

        LayoutInflater inflater = LayoutInflater.from(context);
        View menuView = inflater.inflate(R.layout.menu_bottom, null);
        ButterKnife.bind(this, menuView);

        popupWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setOutsideTouchable(true);

        menuView.setOnTouchListener(this);

    }

    public void show() {
        //得到当前activity的rootView
        View rootView = ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {

        int height = popLayout.getTop();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y < height) {
                popupWindow.dismiss();
            }
        }
        return true;
    }

    @OnClick(R.id.btn_menu_1)
    void onClickButton1(View view) {
        listener.onClick(view);
    }

    @OnClick(R.id.btn_menu_2)
    void onClickButton2(View view) {
        listener.onClick(view);
    }

    @OnClick(R.id.btn_menu_3)
    void onClickButton3(View view) {
        listener.onClick(view);
    }

}
