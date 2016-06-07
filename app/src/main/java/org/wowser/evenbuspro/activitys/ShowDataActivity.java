package org.wowser.evenbuspro.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.preferencesUtil.pp.Loginpreferences;

public class ShowDataActivity extends AppCompatActivity {


    private ImageView imag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        imag = (ImageView) findViewById(R.id.image_login_2);

        //getIntent().getExtras();
    }

    void ShowGuiPage(){
        if(new Loginpreferences(this).getLoginCacheCishu() == 1){
            imag.setVisibility(View.VISIBLE);
            imag.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imag.setVisibility(View.GONE);
                    TextView text = (TextView) findViewById(R.id.textView_data);
                    String data = getIntent().getStringExtra("data");
                    text.setText("dadsa");
                }
            },3000);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        ShowGuiPage();
    }
}
