package org.wowser.evenbuspro.activitys;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.fragments.CustomDiBuFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wanli on 2016/4/22.
 */
public class ShowContentActivity extends BaseActiviy {


    private Fragment fragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }




}
