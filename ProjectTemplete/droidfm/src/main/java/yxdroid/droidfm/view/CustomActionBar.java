package yxdroid.droidfm.view;

import android.app.ActionBar;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import yxdroid.droidfm.R;

public class CustomActionBar {

    private ActionBar actionBar;

    private Activity baseActivity;

    private ActionBarListener actionBarListener;

    private TextView btnLeft;

    private TextView tvTitle;

    private TextView tvRight;

    private ImageView btnRight;

    private int rightBtnResId;

    public interface ActionBarListener {
        /**
         * 返回单击
         */
        void onBack();

        /**
         * 右边按钮单击
         */
        void onRightBtnClick();

        /**
         * 右边tv 单击
         */
        void onRightTvClick();
    }

    public CustomActionBar(Activity baseActivity, ActionBarListener actionBarListener) {
        this.baseActivity = baseActivity;
        this.actionBarListener = actionBarListener;
        actionBar = baseActivity.getActionBar();
    }

    /**
     * 父类初始化actionBar
     * 自定义ActionBar
     */
    public void initActionBar(Integer btnLeftImage, String title,
                              Integer btnRightImage, String tvRightStr) {
        if (tvTitle != null) {
            tvTitle.setText(title);
            return;
        }


        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        View actionBarCustomView = baseActivity.getLayoutInflater().inflate(R.layout.custom_action_bar_layout, null);
        actionBar.setCustomView(actionBarCustomView, lp);

        btnLeft = (TextView) actionBarCustomView.findViewById(R.id.btn_left);
        btnLeft.setOnClickListener(new ActionBarClickListener());

        tvTitle = (TextView) actionBarCustomView.findViewById(R.id.tv_title);
        tvTitle.setText(title);

        // 判断是否显示右边按钮 和 tv
        btnRight = (ImageView) actionBarCustomView.findViewById(R.id.btn_right);
        tvRight = (TextView) actionBarCustomView.findViewById(R.id.tv_right);
        if (btnRightImage != null) {
            rightBtnResId = btnRightImage;
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setImageResource(btnRightImage);
            btnRight.setOnClickListener(new ActionBarClickListener());
        }

        if (tvRightStr != null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(tvRightStr);
            tvRight.setOnClickListener(new ActionBarClickListener());
        }
    }

    /**
     * 初始化actionBar
     *
     * @param title
     */
    public void initActionBar(String title) {
        initActionBar(null, title, null, null);
    }

    /**
     * 初始化actionBar
     *
     * @param title
     */
    public void initActionBar(int title) {
        initActionBar(baseActivity.getString(title));
    }

    public void hideRightTitle() {
        tvRight.setVisibility(View.GONE);
    }

    public void showRightTitle() {
        tvRight.setVisibility(View.VISIBLE);
    }


    public void showRightBtn(int img) {
        rightBtnResId = img;
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(img);
        btnRight.setOnClickListener(new ActionBarClickListener());
    }

    public int getRightBtnResId() {
        return rightBtnResId;
    }

    public void hideLeftBtn() {
        btnLeft.setVisibility(View.INVISIBLE);
    }

    public void hideRightBtn() {
        btnRight.setVisibility(View.GONE);
    }

    /**
     * 自定义ActionBar 单击事件
     */
    public class ActionBarClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            if (actionBarListener == null) {
                return;
            }

            int viewId = view.getId();
            if (viewId == R.id.btn_left) {
                actionBarListener.onBack();
            } else if (viewId == R.id.btn_right) {
                actionBarListener.onRightBtnClick();
            } else if (viewId == R.id.tv_right) {
                actionBarListener.onRightTvClick();
            }
        }
    }
}
