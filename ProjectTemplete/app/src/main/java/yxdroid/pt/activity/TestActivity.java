package yxdroid.pt.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import yxdroid.pt.R;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_test);
    }
}
