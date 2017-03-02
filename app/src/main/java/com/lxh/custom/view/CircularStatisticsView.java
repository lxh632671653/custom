package com.lxh.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.text.NumberFormat;

/**
 * Created by lxh on 2017/2/10.
 * QQ-632671653
 */

public class CircularStatisticsView extends View {


    /**
     * 第一圈的颜色
     */
    private int mFirstColor = Color.BLUE;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor = Color.RED;
    /**
     * 圈的宽度
     */
    private float mCircleWidth = 200;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mProgress = 20;
    /**
     * 剩余文字
     */
    private String reminderText = "剩余";
    /**
     * 进度文字
     */
    private String progressText = "已使用";
    /**
     * 中心x坐标
     */
    int centreX;
    /**
     * 中心y坐标
     */
    int centerY;
    /**
     * 控件宽度
     */
    int widthSizeView;
    /**
     * 控件高度
     */
    int heightSizeView;
    /**
     * 圆环半径
     */
    int radius;
    /**
     * 折线偏移量
     */
    int offset;

    Rect progressTextRect,reminderTextRect;

    Paint reminderTextPaint,progressTextPaint;


    public CircularStatisticsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularStatisticsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircularStatisticsView(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSizeView = MeasureSpec.getSize(widthMeasureSpec);
        heightSizeView = MeasureSpec.getSize(heightMeasureSpec);
        radius = widthSizeView / 6;// 半径
        mCircleWidth = widthSizeView / 7;
        offset = widthSizeView/35;
        int textSize = widthSizeView / 30;
        progressTextRect = new Rect();
        progressTextPaint = new Paint();
        progressTextPaint.setAntiAlias(true); // 消除锯齿
        progressTextPaint.setColor(mSecondColor);
        progressTextPaint.setTextSize(textSize);
        progressTextPaint.getTextBounds(progressText, 0, progressText.length(), progressTextRect);

        reminderTextRect = new Rect();
        reminderTextPaint = new Paint();
        reminderTextPaint.setAntiAlias(true); // 消除锯齿
        reminderTextPaint.setColor(mFirstColor);
        reminderTextPaint.setTextSize(textSize);
        reminderTextPaint.getTextBounds(progressText, 0, progressText.length(), reminderTextRect);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        centreX = getWidth() / 2; // 获取圆心的x坐标
        centerY = getHeight() / 2;
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(centreX - radius, centerY - radius, centreX + radius, centerY + radius); // 用于定义的圆弧的形状和大小的界限
        mPaint.setColor(mFirstColor); // 设置圆环的颜色
        canvas.drawCircle(centreX, centerY, radius, mPaint); // 画出圆环
        mPaint.setColor(mSecondColor); // 设置圆弧的颜色
        canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧

        //获取进度圆弧的中心点
        float textX1 = (float) (centreX + (radius + mCircleWidth / 2) * Math.cos(2 * Math.PI * (mProgress / 2 - 90) / 360));
        float textY1 = (float) (centerY + (radius + mCircleWidth / 2) * Math.sin(2 * Math.PI * (mProgress / 2 - 90) / 360));
        //绘制文字
        drawText(canvas, progressText + getPer(mProgress, 360), textX1, textY1, progressTextPaint,progressTextRect);
        //获取剩余进度圆弧的中心点
        float textX2 = (float) (centreX + (radius + mCircleWidth / 2) * Math.cos(2 * Math.PI * ((mProgress - 360) / 2 - 90) / 360));
        float textY2 = (float) (centerY + (radius + mCircleWidth / 2) * Math.sin(2 * Math.PI * ((mProgress - 360) / 2 - 90) / 360));
        //绘制文字
        drawText(canvas, reminderText + getPer(360 - mProgress, 360), textX2, textY2, reminderTextPaint,reminderTextRect);
    }

    /**
     * 计算百分比
     *
     * @param now
     * @param total
     * @return
     */
    private String getPer(int now, int total) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();

        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);

        String result = numberFormat.format((float) now / (float) total * 100);

        return result + "%";
    }

    /**
     * 设置剩余进度颜色
     *
     * @param color
     */
    public void setReminderColor(int color) {
        mFirstColor = color;
        invalidate();
    }

    /**
     * 设置当前进度颜色
     *
     * @param color
     */
    public void setProgressColor(int color) {
        mSecondColor = color;
        invalidate();
    }

    /**
     * 设置剩余进度文字
     *
     * @param text
     */
    public void setReminderText(String text) {
        reminderText = text;
        invalidate();
    }

    /**
     * 设置当前进度文字
     *
     * @param text
     */
    public void setProgressText(String text) {
        progressText = text;
        invalidate();
    }

    /**
     * 设置圆环宽度
     *
     * @param width
     */
    public void setCircleWidth(int width) {
        mCircleWidth = width;
        invalidate();
    }

    /**
     * 设置当前进度
     *
     * @param total 总量
     * @param now   当前进度量
     */
    public void setProgress(float total, float now) {
        mProgress = (int) ((now / total) * 360);
        postInvalidate();
    }

    /**
     * 绘制文字
     *
     * @param canvas
     * @param string
     * @param firstX
     * @param firstY
     * @param paint
     */
    private void drawText(Canvas canvas, String string, float firstX, float firstY, Paint paint,Rect rect) {
        float endX1 = 0;
        float endY1 = 0;
        float endX2 = 0;
        float endY2 = 0;
        float textX = 0;
        float textY = 0;
        //初始点位于第四区间
        if (firstX <= centreX && firstY <= centerY) {
            endX1 = firstX - offset;
            endY1 = firstY - offset;
            endX2 = firstX - rect.width()*2;
            endY2 = firstY - offset;
            textX = endX2;
            textY = endY2 - 10;
        }
        //初始点位于第三区间
        if (firstX < centreX && firstY > centerY) {
            endX1 = firstX - offset;
            endY1 = firstY + offset;
            endX2 = firstX - rect.width()*2;
            endY2 = firstY + offset;
            textX = endX2;
            textY = endY2 + rect.height()+5;
        }
        //初始点位于第一区间
        if (firstX > centreX && firstY < centerY) {
            endX1 = firstX + offset;
            endY1 = firstY - offset;
            endX2 = firstX + rect.width()*2;
            endY2 = firstY - offset;
            textX = endX1;
            textY = endY2 - 10;
        }
        //初始点位于第二区间
        if (firstX >= centreX && firstY >= centerY) {
            endX1 = firstX + offset;
            endY1 = firstY + offset;
            endX2 = firstX + rect.width()*2;
            endY2 = firstY + offset;
            textX = endX1;
            textY = endY2 + rect.height()+5;
        }
        canvas.drawLine(firstX, firstY, endX1, endY1, paint);
        canvas.drawLine(endX1, endY1, endX2, endY2, paint);
        canvas.drawText(string, textX, textY, paint);
    }

}
