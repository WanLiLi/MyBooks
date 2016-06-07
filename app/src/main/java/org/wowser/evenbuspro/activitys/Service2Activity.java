package org.wowser.evenbuspro.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.services.PollingService;
import org.wowser.evenbuspro.services.PollingUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Service2Activity extends BaseActiviy {

    @Bind(R.id.textview_ButterKnife)
    TextView textviewButterKnife;
    @Bind(R.id.textview_ButterKnife2)
    TextView textviewButterKnife2;
    @Bind(R.id.toolbarservice)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service2);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setActionBarSupportNavigateUp();
        //Start polling service
        System.out.println("Start polling service...");
        PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        //Stop polling service
        System.out.println("Stop polling service...");
        PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
    }

    @OnClick({R.id.textview_ButterKnife, R.id.textview_ButterKnife2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview_ButterKnife:
                textviewButterKnife.setText("1");
                break;
            case R.id.textview_ButterKnife2:
                textviewButterKnife2.setText("2");
                break;
        }
    }


}
