package org.wowser.evenbuspro.activitys;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.fragments.FragmentTwo;

public class FragmentLifeTest extends BaseActiviy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_life_test);

        View veiw = findViewById(R.id.fram_cotent);
        FragmentTwo two = new FragmentTwo();
        //getSupportFragmentManager().beginTransaction().add(two,"two").commit();  //这么写为什么不行
        getSupportFragmentManager().beginTransaction().add(R.id.fram_cotent,two,"two").commit();
    }
}
