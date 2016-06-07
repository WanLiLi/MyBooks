package org.wowser.evenbuspro.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.services.PollingService;
import org.wowser.evenbuspro.services.PollingUtils;

public class ServiceActivity extends BaseActiviy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start polling service
        System.out.println("Start polling service...");

        //PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Stop polling service
        System.out.println("Stop polling service...");
        //PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
    }

}
