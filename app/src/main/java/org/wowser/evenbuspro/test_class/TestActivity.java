package org.wowser.evenbuspro.test_class;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.activitys.BaseActiviy;

public class TestActivity extends BaseActiviy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        findViewById(R.id.btn_huidiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * new 一个小李
                 */
                Li li = new Li();

                /**
                 * new 一个小王
                 */
                Wang wang = new Wang(li);

                /**
                 * 小王问小李问题
                 */
                wang.askQuestion("1 + 1 = ?");
            }
        });
    }






}
