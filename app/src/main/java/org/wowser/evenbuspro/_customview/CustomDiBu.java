package org.wowser.evenbuspro._customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.utils.MLog;

/**
 * Created by wanli on 2016/4/22.
 */
public class CustomDiBu extends View {

    private int mColor = Color.RED;
    private Bitmap mIconBitmap;
    private String mText = "weiwei";
    private float  mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());

    private Canvas mCanvas;
    private Bitmap mBitmap;       //Xfermode
    private Paint  mPaint;        //Paint

    private float mAlpha;  //= 1.0f;

    private Rect mIconRect;      //rect
    private Rect mTextRect;     //rect

    private Paint mTextPaint;    //Paint


    public CustomDiBu(Context context) {
        this(context, null);
    }

    public CustomDiBu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomDiBu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiBucaidan);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.DiBucaidan_changeWithIcon:
                    //矢量图怎么搞？
                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.DiBucaidan_changeWithColor:
                    mColor = a.getColor(attr, mColor);
                    break;
                case R.styleable.DiBucaidan_changeWithTextContent:
                    mText = a.getString(attr);
                    break;
                case R.styleable.DiBucaidan_changeWithTextSize:
                    mTextSize = a.getDimension(attr, mTextSize);
                    break;
            }
        }

        a.recycle();

        mTextRect = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setColor(mColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);   //获取文本的宽高边界，//////并且给mTextRect赋值了？
    }


    Rect rect;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //得到Icon宽高，并且设置边长为最小的值。所以图标是正方形。
        int icon_width =  getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int icon_height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextRect.height();
        int icon_length = Math.min(icon_width, icon_height);

        //设置Icon的Rect
        int left = getMeasuredWidth() / 2 - icon_length / 2;   //类似于getleft
        int top =  getMeasuredHeight() / 2 - (icon_length + mTextRect.height()) / 2;   //mTextRect.height() 获取文本的高度
        mIconRect = new Rect(left, top, left + icon_length, top + icon_length);

        //rect = new Rect(left, top, left + icon_length + 20 + 20, top + icon_length + 20);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //画一个图片
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

//        mPaint = new Paint();
//        mPaint.setColor(mColor);
//        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
//        mPaint.setAlpha(100);
//        canvas.drawRect(mIconRect,mPaint);

        int alpha = (int) Math.ceil(255 * mAlpha);
        MLog.d("onDraw","alpha:"+alpha);
        setUpTargetBitmap(alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);     //canvas.drawBitmap(mBitmap,null, mIconRect, null);   //mIconRect此处rect可能是基于图标图片的宽高

        drawSourceText(canvas, alpha);
        drawTargerText(canvas, alpha);
    }


    /**
     * 设置一个完全一样的图标bitmap
     */
    private void setUpTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);  //画一个跟图标一样大小的矩形
        //************************************很重要的**********************************************************
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));  //也就是先绘制图片，再绘制形状了~~ 根据模式DST_IN显示在原始图片上面并且只覆盖成跟图片刚刚好的样子  （可以屏蔽此代码看效果）
        mPaint.setAlpha(255);
        //test Bitmap mIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_dianccct);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);              //画一层view（bitmap）
    }


    /**
     * 绘制原文本
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0Xff333333);  //0Xff333333
        mTextPaint.setAlpha(255-alpha);
        int x = getMeasuredWidth() / 2 - mTextRect.width() / 2;
        int y = mIconRect.bottom + mTextRect.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }


    /**
     * 绘制变色文本
     */
    private void drawTargerText(Canvas canvas, int alpha) {
        //mTextPaint.setColor(getResources().getColor(R.color.colorAccent));  //粉色
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - mTextRect.width() / 2;  //类似于getLeft
        int y = mIconRect.bottom + mTextRect.height();           //错误的理解：图片的从底部到view顶部的高度+文本的高度。  类似于文本从底部到view顶部的高度int y = mTextRect.bottom;
        canvas.drawText(mText, x, y, mTextPaint);
    }


    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView(){
        if(Looper.getMainLooper() == Looper.myLooper()){
            //必须是在UI线程中进行工作

//invalidate:  View本身调用迫使view重画。必须是在UI线程中进行工作。比如在修改某个view的显示时，调用invalidate()才能看到重新绘制的界面。
            invalidate();

            //requestLayout: 当view确定自身已经不再适合现有的区域时，该view本身调用这个方法要求parent view重新调用他的onMeasure onLayout来对重新设置自己位置。
//            特别的当view的layoutparameter发生改变，并且它的值还没能应用到view上，这时候适合调用这个方法。必须是在UI线程中进行工作
            //requestLayout();
        }else{
            //在非UI线程中进行。
            postInvalidate();
        }
    }




}
