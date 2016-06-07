package org.wowser.evenbuspro._customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.wowser.evenbuspro.tool.BitmapUtil;
import org.wowser.evenbuspro.utils.MLog;

/**
 * Created by wanli on 2016/5/3.
 */
public class CustomDragView extends RelativeLayout {
    private ViewDragHelper mDragger;

    private View mDragView;
    private View mAutoBackView;
    private ImageView view3;
    private View mEdgeTrackerView;


    public CustomDragView(Context context) {
        super(context);
        init(context);
    }


    public CustomDragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragger.processTouchEvent(ev);
        return true;
    }


    private void init(Context context) {
        mDragger = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        MLog.d("DragLayout", "onFinishInflate==");
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        view3 = (ImageView) getChildAt(2);
    }

    class DragHelperCallback extends ViewDragHelper.Callback {
        //是起始位置的数值
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getTop();
            int bottom = child.getBottom();


            //
            view3.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapUtil.createDraggingItemImage(child, null);
            view3.setImageBitmap(bitmap);
            ViewCompat.setTranslationX(view3, child.getLeft());
            ViewCompat.setTranslationY(view3, child.getTop());
            MLog.d("DragLayout=","tryCaptureView=="+"left:"+left+"right:"+right+"top:"+top+"bottom:"+bottom);
            return true;
        }

        //当captureview的位置发生改变时回调,抬起后最后的数值
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //super.onViewPositionChanged(changedView, left, top, dx, dy);
            MLog.d("DragLayout=","onViewPositionChanged=="+"left:"+left+" top: "+top);
            ViewCompat.setTranslationX(view3, left);
            ViewCompat.setTranslationY(view3, top);
        }

        //拖动后最后的数值
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            MLog.d("DragLayout", "clampViewPositionHorizontal==" + left + "," + dx);
            if (child == mAutoBackView) {
                int leftBound = getPaddingLeft();  //40
                int rightBound = getWidth() - mAutoBackView.getWidth();  //1080-236
                MLog.d("DragLayout", "mAutoBackView.getWidth() " + mAutoBackView.getWidth()+"leftBound:"+leftBound+"  getWidth():"+getWidth());
                final int newLeft = Math.min(Math.max(left,leftBound), rightBound);
                return newLeft;
            } else if (child == mDragView) {
                final int leftBound = getPaddingLeft();                //父布局的
                MLog.d("DragLayout", "getPaddingLeft " + leftBound);
                MLog.d("DragLayout", "getWidth " + getWidth());        //父布局的
                MLog.d("DragLayout", "mDragView.getWidth() " + mDragView.getWidth());
                final int rightBound = getWidth() - mDragView.getWidth() - leftBound;   //620-40=580
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);    //279,40 580
                MLog.d("DragLayout", "newLeft " + newLeft);
                return newLeft;
            }
            return left;
        }


        //拖动后最后的数值
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            MLog.d("DragLayout", "clampViewPositionVertical==" + top + "," + dy);
            int topBound = getPaddingTop();
            int bottomBound = getHeight() - mDragView.getHeight();
            int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }


        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            MLog.d("DragLayout", "onViewReleased==" + xvel + "," + yvel);
            view3.setVisibility(View.GONE);
            //ViewCompat.setTranslationZ(releasedChild,100);
            //ViewCompat.offsetLeftAndRight(releasedChild,100);
        }

        //在边界拖动时回调  //当触摸到边界时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            //mDragger.captureChildView(mEdgeTrackerView, pointerId);
            MLog.d("DragLayout", "onEdgeDragStarted==" + edgeFlags + "," + pointerId);
        }
    }

}
