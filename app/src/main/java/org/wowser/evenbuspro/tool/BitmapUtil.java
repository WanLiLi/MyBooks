package org.wowser.evenbuspro.tool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Log;
import android.view.View;

/**
 * Created by songzeyang on 16/4/12.
 */
public class BitmapUtil {
    public static Bitmap createDraggingItemImage(View v, NinePatchDrawable shadow) {
        int width = v.getWidth();
        int height = v.getHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(bitmap);

        if (shadow != null) {
            shadow.setBounds(0, 0, width, height);
            shadow.draw(canvas);
        }

        final int savedCount = canvas.save(Canvas.CLIP_SAVE_FLAG | Canvas.MATRIX_SAVE_FLAG);
        // NOTE: Explicitly set clipping rect. This is required on Gingerbread.
        canvas.clipRect(0, 0, width, height);
        canvas.translate(0, 0);
        v.draw(canvas);
        canvas.restoreToCount(savedCount);

        return bitmap;
    }









    /**
     * 把View绘制到Bitmap上
     * @param view 需要绘制的View
     * @param width 该View的宽度
     * @param height 该View的高度
     * @return 返回Bitmap对象
     * add by csj 13-11-6
     */
    public static  Bitmap getViewBitmap(View view, int width, int height) {
        Bitmap bitmap = null;
        if (view != null) {
            view.clearFocus();
            view.setPressed(false);

            boolean willNotCache = view.willNotCacheDrawing();
            view.setWillNotCacheDrawing(false);

            // Reset the drawing cache background color to fully transparent
            // for the duration of this operation
            int color = view.getDrawingCacheBackgroundColor();
            view.setDrawingCacheBackgroundColor(0);
            float alpha = view.getAlpha();
            view.setAlpha(1.0f);

            if (color != 0) {
                view.destroyDrawingCache();
            }

            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            view.measure(widthSpec, heightSpec);
            view.layout(0, 0, width, height);

            view.buildDrawingCache();
            Bitmap cacheBitmap = view.getDrawingCache();
            if (cacheBitmap == null) {
                Log.e("view.ProcessImageToBlur", "failed getViewBitmap(" + view + ")",
                        new RuntimeException());
                return null;
            }
            bitmap = Bitmap.createBitmap(cacheBitmap);
            // Restore the view
            view.setAlpha(alpha);
            view.destroyDrawingCache();
            view.setWillNotCacheDrawing(willNotCache);
            view.setDrawingCacheBackgroundColor(color);
        }
        return bitmap;
    }





    /**
     * View到Bitmap
     */
    public  static  Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("Folder", "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }


}
