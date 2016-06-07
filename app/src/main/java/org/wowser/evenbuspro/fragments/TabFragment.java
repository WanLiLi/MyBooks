package org.wowser.evenbuspro.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wowser.evenbuspro.R;

/**
 * Created by wanli on 2016/4/23.
 */
public class TabFragment extends Fragment {
    public static String TITLE = "title";
    private String mTitle = "default";

    public static  TabFragment newInstance(String title) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setTextSize(22);
        textView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        textView.setTextColor(getResources().getColor(R.color.red));
        textView.setText(mTitle);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
