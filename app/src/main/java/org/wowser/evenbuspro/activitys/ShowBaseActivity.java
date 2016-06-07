package org.wowser.evenbuspro.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro._customview.CustomDragView;
import org.wowser.evenbuspro.fragments.CameraFragment;
import org.wowser.evenbuspro.fragments.ChartFragment;
import org.wowser.evenbuspro.fragments.CustomDiBuFragment;
import org.wowser.evenbuspro.fragments.CustomDragViewFragment;
import org.wowser.evenbuspro.fragments.CustomGalleryFragment;
import org.wowser.evenbuspro.fragments.GreedaoFragment;

public class ShowBaseActivity extends BaseActiviy {
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame);
        show();
    }


    private void show() {
        Bundle bundle = getIntent().getBundleExtra("bund_Key");
        String tag = bundle.getString("tag");


//        Bundle bundle =  getIntent().getExtras();
//        String tag = bundle.getString("tag");

        if (tag.equals(MainActivity.CUSTOMDIBUFRAGMENT)) {
            fragment = new CustomDiBuFragment();
        } else if (tag.equals(CustomDragViewFragment.TAG)) {
            fragment = new CustomDragViewFragment();
        } else if (tag.equals(CameraFragment.TAG)) {
            fragment = new CameraFragment();
        } else if (tag.equals(ChartFragment.TAG)) {
            fragment = ChartFragment.newInstance();
        } else if (tag.equals(CustomGalleryFragment.TAG)) {
            fragment = CustomGalleryFragment.newInstance();
        } else if (tag.equals(GreedaoFragment.TAG)) {
            fragment = GreedaoFragment.newInstance();
        }
        showFragment(fragment);
    }


    private void showFragment(Fragment fragment) {
        if (fragment == null)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fram_content, fragment);
        transaction.commit();
    }
}
