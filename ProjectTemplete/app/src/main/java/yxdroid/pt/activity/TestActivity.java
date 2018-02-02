package yxdroid.pt.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import butterknife.OnClick;
import yxdroid.droidfm.base.BaseMVPActivity;
import yxdroid.droidfm.fragment.FragmentCommunication;
import yxdroid.droidfm.fragment.FunctionOnlyParam;
import yxdroid.droidfm.fragment.FunctionParamAndResult;
import yxdroid.droidfm.mvp.presenter.BasePresenter;
import yxdroid.pt.R;

public class TestActivity extends BaseMVPActivity {

    private Fragment curFragment;

    @Override
    protected int bindLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void onInit() {
        switchFragment(Fragment1.class.getName());
    }

    @Override
    public void onBack() {
        super.onBack();
        close();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void switchFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (curFragment != null) {
            transaction.hide(curFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if (fragment == null) {
            if (Fragment1.class.getName().equals(tag)) {
                fragment = new Fragment1();
            } else if (Fragment2.class.getName().equals(tag)) {
                fragment = new Fragment2();
            } else if (Fragment3.class.getName().equals(tag)) {
                fragment = new Fragment3();
            } else if (Fragment4.class.getName().equals(tag)) {
                fragment = new Fragment4();
            }
        }

        if (!fragment.isAdded()) {
            transaction.add(R.id.container, fragment, fragment.getClass().getName());
        } else {
            transaction.show(fragment);
        }
        curFragment = fragment;
        transaction.commitNowAllowingStateLoss();
    }

    @Override
    public void setFragmentCommunicationInterface(String tag) {
        super.setFragmentCommunicationInterface(tag);
        FragmentCommunication.getInstance().add(new FunctionParamAndResult<String, String>(getClass().getName(), Fragment1.COMMUNICATION_GETTIP) {
            @Override
            public String run(String s) {
                return "hello world yxdroid";
            }
        }).add(new FunctionOnlyParam<Integer>(getClass().getName(), Fragment2.COMMUNICATION_SETVALUE) {
            @Override
            public void run(Integer integer) {
                showTip(integer + "");
            }


        });
    }

    @OnClick({R.id.rb1, R.id.rb2, R.id.rb3, R.id.rb4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb1:
                switchFragment(Fragment1.class.getName());
                break;
            case R.id.rb2:
                switchFragment(Fragment2.class.getName());
                break;
            case R.id.rb3:
                switchFragment(Fragment3.class.getName());
                break;
            case R.id.rb4:
                switchFragment(Fragment4.class.getName());
                break;

        }
    }
}
