package yxdroid.pt.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import yxdroid.droidfm.base.BaseFragment;
import yxdroid.pt.R;

public class Fragment1 extends BaseFragment {

    public static final String COMMUNICATION_GETTIP = Fragment1.class.getName() + "_gettip";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_1, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @OnClick(R.id.btn_fm1)
    public void onClick(View view) {
        String result = fragmentCommunication.invoke(COMMUNICATION_GETTIP, "yxdroid", String.class);
        showTip(result);
    }
}
