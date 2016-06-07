package org.wowser.evenbuspro.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro._customview.CustomDiBu;
import org.wowser.evenbuspro.utils.MLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 *
 */
public class CustomDiBuFragment extends BaseFragments implements ViewPager.OnPageChangeListener{


    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.one)
    CustomDiBu one;
    @Bind(R.id.two)
    CustomDiBu two;
    @Bind(R.id.three)
    CustomDiBu three;
    @Bind(R.id.four)
    CustomDiBu four;


    private View rootVIew;
    private List<Fragment> fragments;
    private String[] titles = new String[]{"one", "two", "three", "four"};

    private List<CustomDiBu> customDiBusList = new ArrayList<CustomDiBu>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootVIew = inflater.inflate(R.layout.fragment_custom_di_bu, container, false);
        ButterKnife.bind(this, rootVIew);

        iniData();
        initEvent();
        return rootVIew;
    }


    private void iniData() {
        fragments = new ArrayList<Fragment>();

        for (String title : titles) {
            fragments.add(TabFragment.newInstance(title));
        }

        viewpager.setAdapter(new ViewpagerAdapter(getFragmentManager()));

        customDiBusList.add(one);
        customDiBusList.add(two);
        customDiBusList.add(three);
        customDiBusList.add(four);

        one.setIconAlpha(1.0f);
    }

    private void initEvent() {
         viewpager.addOnPageChangeListener(this);
    }

    @OnClick({R.id.one, R.id.two, R.id.three, R.id.four})
    public void onClick(View view) {
        resetOtherTabs();
        switch (view.getId()) {
            case R.id.one:
                customDiBusList.get(0).setIconAlpha(1.0f);
                viewpager.setCurrentItem(0);
                break;
            case R.id.two:
                customDiBusList.get(1).setIconAlpha(1.0f);
                viewpager.setCurrentItem(1);
                break;
            case R.id.three:
                customDiBusList.get(2).setIconAlpha(1.0f);
                viewpager.setCurrentItem(2);
                break;
            case R.id.four:
                customDiBusList.get(3).setIconAlpha(1.0f);
                viewpager.setCurrentItem(3);
                break;
        }
    }


    /**
     * 重置其它tab颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < customDiBusList.size(); i++) {
            customDiBusList.get(i).setIconAlpha(0);
        }
    }


    /**
     * left到right 0.0~1.0   right到left 1.0~0.0
     * */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        MLog.d("=","position:"+position);
        if(positionOffset > 0){
            MLog.d("=","position:"+position+"---positionOffset:"+positionOffset);
            CustomDiBu left =  customDiBusList.get(position);
            CustomDiBu right =  customDiBusList.get(position+1);
            left.setIconAlpha(1-positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class ViewpagerAdapter extends FragmentPagerAdapter {
        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void setIconAlpha() {

    }
}
