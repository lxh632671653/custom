package com.lxh.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lxh on 2017/3/17.
 * QQ-632671653
 */

public class HorizontalProgressView extends View {


    /**
     * 控件宽度
     */
    private int widthSizeView;
    /**
     * 控件高度
     */
    private int heightSizeView;

    private int progressColor = Color.BLUE;

    private int progressBGColor = Color.WHITE;

    private Paint mPaint;

    private float maxProgress = 100;

    private float mProgress = 0;

    public HorizontalProgressView(Context context) {
        super(context);
    }

    public HorizontalProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSizeView = MeasureSpec.getSize(widthMeasureSpec);
        heightSizeView = MeasureSpec.getSize(heightMeasureSpec);
        mPaint = new Paint();
        mPaint.setColor(progressBGColor);
        mPaint.setStrokeWidth(heightSizeView);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProggressBG(canvas,mPaint);
        drawProggress(canvas,mPaint,mProgress);
    }

    private void drawProggressBG(Canvas canvas, Paint paint){
        canvas.drawLine(0,heightSizeView/2,widthSizeView,heightSizeView/2,paint);
    }

    private void drawProggress(Canvas canvas, Paint paint,float progress){
        paint.setColor(progressColor);
        canvas.drawLine(0,heightSizeView/2,widthSizeView*(progress/maxProgress),heightSizeView/2,paint);
    }

    public float getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }

    public float getmProgress() {
        return mProgress;
    }

    public void setmProgress(float mProgress) {
        this.mProgress = mProgress;
        requestLayout();
    }
}
