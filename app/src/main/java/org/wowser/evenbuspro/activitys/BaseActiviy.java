package org.wowser.evenbuspro.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.wowser.evenbuspro.BusProvider;
import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.utils.MLog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseActiviy extends AppCompatActivity {

//    @Bind(R.id.toolbarservice)
    //private Toolbar toolbarservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_activiy);
        //ButterKnife.bind(this);

//        toolbarservice = (Toolbar) findViewById(R.id.toolbarservice);
//        setSupportActionBar(toolbarservice);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void setActionBarSupportNavigateUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MLog.d("wanli", "BaseActiviy-onResume");
        BusProvider.getInstance().register(this);
    }


    //手势最好在activity中的dispatchTouchEvent中做，这个是无法被子view屏蔽的。
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        MLog.d("wanli", "BaseActiviy-onPause");
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ButterKnife.unbind(this);
        MLog.d("wanli", "BaseActiviy-onDestroy");
    }


    //popBackStack()
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
