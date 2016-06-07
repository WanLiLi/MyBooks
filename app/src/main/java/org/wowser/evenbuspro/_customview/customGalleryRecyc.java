package org.wowser.evenbuspro._customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import org.wowser.evenbuspro.utils.MLog;

import butterknife.OnTouch;

/**
 * Created by wanli on 2016/5/24.
 * 1.ontouchListener: bug>用力滚动recycView，获取的view并不是最终滚动到的第一个child
 * 2.OnScrolled:  可以解决ontouchListener的bug
 */
public class customGalleryRecyc extends RecyclerView implements RecyclerView.OnItemTouchListener{

    public customGalleryRecyc(Context context) {
        super(context);
    }

    public customGalleryRecyc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public customGalleryRecyc(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private View mCurrentView;

    /**
     * OnItemScrollChangeListener  滚动时回调的接口
     */
    public interface OnItemScrollChangeListener {
        void onChange(View view, int position);
    }

    private OnItemScrollChangeListener mItemScrollChangeListener;

    public void setOnItemScrollChangeListener(OnItemScrollChangeListener mItemScrollChangeListener) {
        this.mItemScrollChangeListener = mItemScrollChangeListener;
    }


    /**
     * 加载完所有的item后，将永远不会执行此方法了；
     * <p/>
     * 但是，我单击了item后，执行了回调方法后，会执行。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mCurrentView = getChildAt(0);  //获取的是当前界面的第一个child

        int count = getChildCount();
        MLog.d("wanli", "onLayout " + " count：" + count);
    }


    /**
     * onTouchEvent 的情况下
     *
     * */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            mCurrentView = getChildAt(2);    //获取的是当前界面的第一个child

            int adapterPosition = getChildAdapterPosition(mCurrentView);
            MLog.d("wanli", "onTouchEvent " + " adapterPosition：" + getChildAdapterPosition(mCurrentView) + "childPosition: " + getChildPosition(mCurrentView));
            if (mItemScrollChangeListener != null) {
                //onTouch情况下的
                //mItemScrollChangeListener.onChange(mCurrentView, adapterPosition);
            }
        }
        return super.onTouchEvent(e);
    }


    /**
     * scrolled 的情况下
     *
     * */
    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        View oneView = getChildAt(0);
        int positon =  getChildAdapterPosition(oneView);

        if(positon == 0){
            View view = getChildAt(1);
            mItemScrollChangeListener.onChange(view,
                    getChildAdapterPosition(view));
        }else {
            View newView = getChildAt(2);
            if (mItemScrollChangeListener != null)
            {
                if (newView != null && newView != mCurrentView)
                {
                    mCurrentView = newView;
                    //scrolled情况下的
                    mItemScrollChangeListener.onChange(mCurrentView,
                            getChildAdapterPosition(mCurrentView));

                }
            }
        }
    }




    /**
     * 触摸（滚动）监听事件
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        MLog.d("wanli", "OnItemTouchListener" + e.getAction());
        if (MotionEvent.ACTION_MOVE == e.getAction()) {
            MLog.d("wanli", "OnItemTouchListener" + "ACTION_MOVE");
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


}
