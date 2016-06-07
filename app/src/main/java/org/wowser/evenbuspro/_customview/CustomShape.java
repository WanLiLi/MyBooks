package org.wowser.evenbuspro._customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.baidu.mapapi.map.MyLocationData;

import org.wowser.evenbuspro.BusProvider;
import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.utils.EventHandler;
import org.wowser.evenbuspro.utils.MLog;

/**
 * Created by wanli on 2016/6/2.
 * <p>
 * <p>
 * v4包下的是Animation，而app包下的是Animator；
 * Animation一般动画就是我们前面学的帧动画和补间动画！Animator则是本节要讲的属性动画！
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
 * <p>
 * AccelerateInterpolator  在动画开始的地方速率改变比较慢，然后开始加速
 * <p>
 * AnticipateInterpolator 开始的时候向后然后向前甩
 * <p>
 * AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
 * <p>
 * BounceInterpolator   动画结束的时候弹起
 * <p>
 * CycleInterpolator   动画循环播放特定的次数，速率改变沿着正弦曲线
 * <p>
 * DecelerateInterpolator 在动画开始的地方快然后慢
 * <p>
 * LinearInterpolator   以常量速率改变
 * <p>
 * OvershootInterpolator    向前甩一定值后再回到原来位置
 */
@TargetApi(Build.VERSION_CODES.M)
public class CustomShape extends View {
    public static float[] TRANSLATIONY = {0, 1, 2, 3, 10, 40, 60, 80, 150, 100, 60, 30, 20, 10, 0};

    //默认进度是100
    private float progress = 100;  //进度不可能设置为360，所以为了能表达在360度中占据多少百分比。公式为：360*progress/100
    private int color = getContext().getColor(R.color.red);

    private int max = 100;
    private RectF rectArc;
    private int strokeWidth = 10;

    public CustomShape(Context context) {
        this(context, null);
    }

    public CustomShape(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomShape(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rectArc = new RectF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = getSuggestedMinimumHeight();
        int w = getSuggestedMinimumWidth();
        MLog.d("customShape", "onMeasure" + "  h:" + h);
        MLog.d("customShape", "onMeasure" + "  w:" + w);
        final int height = getDefaultSize(h, heightMeasureSpec); //2140
        final int width = getDefaultSize(w, widthMeasureSpec);   //245
        MLog.d("customShape", "onMeasure" + "  height:" + height);
        MLog.d("customShape", "onMeasure" + "  width:" + width);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        MLog.d("customShape", "onMeasure" + "  min:" + min);

        int wid = getMeasuredWidth();
        int hei = getMeasuredHeight();
        MLog.d("customShape", "onMeasure" + "  getMeasuredWidth:" + wid);
        MLog.d("customShape", "onMeasure" + "  getMeasuredHeight:" + hei);
//        int specMode = MeasureSpec.getMode(widthMeasureSpec);
//        int specSize = MeasureSpec.getSize(widthMeasureSpec);


        //坐 上 右 下
        rectArc.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画圆弧
         *
         * */
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        //paint.setAlpha();


        //坐 上 右 下
        //rectArc.set(100,100,800,700);
        float angle = 360 * progress / max;  //一个在360度中占据的百分比
        canvas.drawArc(rectArc, 0, angle, true, paint);   //起始点，结束点。。 起始点是从右边（800）开始??  true:是否包含圆心，true通常用来绘制扇形
    }


    /**
     * 把深色变成淡色
     *
     * */
    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();  //重新绘制.
    }


    public void setColor(int color) {
        this.color = color;
        invalidate();  //重新绘制.
    }


    /**
     * objectAnimator
     *  //objectAnimator.setRepeatCount(5);
     //objectAnimator.setRepeatMode(Animation.REVERSE);
     //objectAnimator.setStartDelay();
     *
     * */
    /**
     * 神奇的地方，也是最关键的地方就是：
     * 会自动调用setProgress设置这个属性，所以setProgress()中必须要有invalidate()方法
     */
    public Animator setProgressWithAnimation(float progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(1400);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
        return  objectAnimator;
    }


    /**
     * 自定义的属性color
     *
     * */
    public Animator setColorWithAnimation(int color) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "color", color);
        objectAnimator.setDuration(1200);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
        return objectAnimator;
    }


    public Animator setRotationAnimation(float rotation) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotation", 0f, 300f, 0f);
        animator.setDuration(3000);
        animator.start();
        return animator;
    }


    public Animator setAlphaAnimation(float alpha) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "alpha", 1f, alpha, 1f);
        animator.setDuration(3000);
        animator.start();
        return animator;
    }

    /**
     *
     *
     *
     * */
    public Animator setTranslationXAnimation(float translationX) {
        float curTranslationX = getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", curTranslationX, translationX, curTranslationX);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(5000);
        animator.start();
        return animator;
    }


    public Animator setTranslationYAnimation(float... startY) {
        float curTranslationY = getTranslationY();
        ObjectAnimator animatorTranslationY = ObjectAnimator.ofFloat(this, "translationY", startY);
        animatorTranslationY.setInterpolator(new BounceInterpolator());
        animatorTranslationY.setDuration(6000);
        animatorTranslationY.start();



        animatorTranslationY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                MLog.d("animation","onAnimationStart"+animation.getDuration());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                MLog.d("animation","onAnimationEnd"+animation.getDuration());


                BusProvider.getInstance().post(true);  //不在一个线程中


                Handler handler = EventHandler.getInstance().getHandler();
                Message msg = new Message();
                msg.arg1 = 0 ;
                handler.sendMessage(msg);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        return animatorTranslationY;
    }






    public Animator setScaleYAnimation(float scaleY) {
        float curScaleY = getScaleY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scaleY", curScaleY, scaleY, curScaleY);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setDuration(2000);
        animator.start();
        return animator;
    }


    public Animator setScaleXAnimation(float scaleX) {
        float curScaleX = getScaleX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scaleX", curScaleX, scaleX, curScaleX);
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(2000);
        animator.start();
        return animator;
    }

    /***
     * 组合动画
     * <p>
     * 实现组合动画功能主要需要借助AnimatorSet这个类，这个类提供了一个play()方法，如果我们向这个
     * 方法中传入一个Animator对象(ValueAnimator或ObjectAnimator)将会返回一个AnimatorSet.Builder的实例，AnimatorSet.Builder中包括以下四个方法：
     * <p>
     * after(Animator anim)   将现有动画插入到传入的动画之后执行
     * after(long delay)   将现有动画延迟指定毫秒后执行
     * before(Animator anim)   将现有动画插入到传入的动画之前执行
     * with(Animator anim)   将现有动画和传入的动画同时执行
     */

    public void setAnimationSet() {
        ObjectAnimator animatorTranslationXStart = ObjectAnimator.ofFloat(this, "translationX", -500, 0f);
        animatorTranslationXStart.setDuration(1000);
        animatorTranslationXStart.setInterpolator(new LinearInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(setAlphaAnimation(0.2f)).with(setProgressWithAnimation(100)).with(setTranslationXAnimation(200)).with(setTranslationYAnimation(TRANSLATIONY)).with(setScaleYAnimation(0.8f)).after(animatorTranslationXStart);
        set.start();


    }







}
