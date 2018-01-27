package yxdroid.pt.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import yxdroid.droidfm.base.BaseFragment;
import yxdroid.pt.R;

public class Fragment2 extends BaseFragment {

    public static final String COMMUNICATION_SETVALUE = Fragment2.class.getName() + "_setvalue";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_2, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick(R.id.btn_fm2)
    public void onClick(View view) {
        fragmentCommunication.invoke(COMMUNICATION_SETVALUE, 12);
    }
}
