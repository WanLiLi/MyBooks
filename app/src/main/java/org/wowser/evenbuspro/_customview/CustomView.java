package org.wowser.evenbuspro._customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.wowser.evenbuspro.R;

/**
 * Created by wanli on 2016/3/27.
 * 只是       滑动
 */
public class CustomView extends ViewGroup implements View.OnTouchListener, GestureDetector.OnGestureListener {
    /**
     * 是否可以拖拽，默认可以
     */
    private boolean isDrag = true;

    /**
     * 执行顺序
     * dispatchTouchEventt-ACTION_DOWN
     * onTouch
     * onTouchEvent-ACTION_DOWN
     * <p/>
     * dispatchTouchEvent-ACTION_MOVE
     * onTouch-onTouch
     * onTouchEvent-ACTION_MOVE()x:590-y:612
     * .....MOVE....
     * <p/>
     * dispatchTouchEvent-ACTION_UP
     * onTouch-onTouch
     * onTouchEvent-ACTION_UP()x:590-y:605
     */

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("custom", "getWidth" + getWidth());   //500
        Log.d("custom", "getHeight" + getHeight()); //500
        Log.d("custom", "getLeft" + getLeft());     //110
        Log.d("custom", "getTop" + getTop());       //0
        Log.d("custom", "getRight" + getRight());   //610
        Log.d("custom", "getBottom" + getBottom()); //500


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("custom", "onSizeChanged" + "-w:" + w + "-h:" + h + "-oldw:" + oldw + "-oldh:" + oldh);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("custom", "dispatchTouchEvent-ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("custom", "dispatchTouchEvent-ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("custom", "dispatchTouchEvent-ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
        //return super.onInterceptTouchEvent(ev);  //只是拦截父，也只有父（viewGruop）有的方法
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //super.onLayout(changed, left, top, right, bottom);
        Log.d("custom", "onLayout()" + "-changed:" + changed + "-" + left + "-" + top + "-" + right + "-" + bottom);
    }

    int lastX = 0;
    int lastY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isDrag) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("custom", "ACTION_DOWN()" + "x:" + x + "-y:" + y);
                    lastX = x;
                    lastY = y;
                    //pointToPosition();   //根据按下的X,Y坐标获取所点击item的position
                    //getChildAt(getFirstVisiblePosition()); //根据position获取该item所对应的View
                    break;
                case MotionEvent.ACTION_MOVE:
                    int offsetX = x - lastX;
                    int offsetY = y - lastY;
                    Log.d("custom", "ACTION_MOVE()" + "x:" + x + "-y:" + y);
                    //Log.d("custom","ACTION_MOVE()"+"offsetX:"+offsetX+"-offsetY:"+offsetY);
                    //layout(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY);
                    offsetLeftAndRight(offsetX);
                    offsetTopAndBottom(offsetY);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d("custom", "ACTION_UP()" + "x:" + x + "-y:" + y);
                    break;
            }
        }
        return super.onTouchEvent(event);  //传递给父View了
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        Log.d("custom", "onDragEvent");
        return super.onDragEvent(event);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("custom", "onTouch-onTouch");
        return false;
    }


    ////////////////////////////
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("custom", "onDown()");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("custom", "onShowPress()");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("custom", "onSingleTapUp()");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("custom", "onScroll()");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("custom", "onLongPress()");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
