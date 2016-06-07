package org.wowser.evenbuspro.activitys;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro._customview.CustomShape;
import org.wowser.evenbuspro.utils.EventHandler;

import butterknife.OnTouch;

public class CustomViewTest extends BaseActiviy {

    private CustomShape customProgress;
    private ImageView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_test);
        EventHandler.getInstance().setHandler(handler);



        customProgress = (CustomShape) findViewById(R.id.customProgress);

        progress = (ImageView) findViewById(R.id.image_progress_shape);


        ImageView imageView = (ImageView) findViewById(R.id.image_zi2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomViewTest.this, "hahah", Toast.LENGTH_LONG).show();
                //customProgress.setProgress(50);  //没有动画效果

                customProgress.setColor(android.support.design.R.color.design_fab_shadow_start_color);

                //customProgress.setProgressWithAnimation(100);

                //customProgress.setRotationAnimation(330);
                //customProgress.setScaleXAnimation(2.2f);


                /***
                 * 组合在一起
                 *
                 * */
//                customProgress.setProgressWithAnimation(100);
//                customProgress.setAlphaAnimation(0.2f);
//                customProgress.setTranslationXAnimation(200);
//                customProgress.setTranslationYAnimation(CustomShape.TRANSLATIONY);
//                customProgress.setScaleYAnimation(0.8f);


//Animation
                Animation animation = AnimationUtils.loadAnimation(CustomViewTest.this, R.anim.rotation);
                progress.startAnimation(animation);



                /***
                 * 把以上动画组合在一起
                 *
                 * */
                customProgress.setAnimationSet();

            }
        });

    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress.clearAnimation();
        }
    };


    @Subscribe
    public  void animationStop(boolean end){
        progress.clearAnimation();
    }

}
