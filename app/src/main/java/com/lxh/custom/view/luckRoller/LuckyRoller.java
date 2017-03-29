package com.lxh.custom.view.luckRoller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.lxh.custom.R;

import java.util.List;

/**
 * Created by lxh on 2017/3/22.
 * QQ-632671653
 */

public class LuckyRoller extends View {


    private int mPaddingLeft;

    /**
     * 控件中心坐标
     */
    private int centerX, centerY;

    /**
     * 控件宽度
     */
    private int with;

    /**
     * 外圆环宽度
     */
    private int circleWith;

    /**
     * 圆环半径
     */
    private int circleRadius;

    /**
     * 转盘半径
     */
    private int rollerRadius;

    /**
     * 绘制盘块的范围
     */
    private RectF mRange;

    /**
     * 盘块的个数
     */
    private int mItemCount;

    /**
     * 与文字对应图片的bitmap数组
     */
    private Bitmap[] mImgsBitmap;


    private List<LuckyBean> luckyBeanList;

    /**
     * 文字的大小
     */
    private float mTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());


    public LuckyRoller(Context context) {
        super(context);
    }

    public LuckyRoller(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LuckyRoller(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        with = MeasureSpec.getSize(widthMeasureSpec);//控件宽度
        setMeasuredDimension(with, with);//重设控件宽高
        centerX = getMeasuredWidth() / 2; //控件X坐标
        centerY = getMeasuredHeight() / 2;//控件Y坐标
        circleWith = with / 12;     //外圆环的宽度
        mPaddingLeft = getPaddingLeft();  //控件padding值
        circleRadius = centerX - mPaddingLeft;  //圆环的半径
        rollerRadius = circleRadius - circleWith / 2;  //扇形块的半径
        luckyBeanList = LuckRollerView.dataList;   //填充的数据
        if (luckyBeanList!=null&&luckyBeanList.size()>=1){
            mItemCount = luckyBeanList.size();
            if (0 != mItemCount) {
                // 圆弧的绘制范围
                mRange = new RectF(centerX - circleRadius + circleWith / 2, centerY - circleRadius + circleWith / 2,
                        centerX + circleRadius - circleWith / 2, centerY + circleRadius - circleWith / 2);
                // 初始化图片
                mImgsBitmap = new Bitmap[mItemCount];
                for (int i = 0; i < mItemCount; i++) {
                    mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(), luckyBeanList.get(i).getImg());
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (0 != mItemCount) {
            Paint paint = new Paint();
            drawCircle(canvas, paint);
            Paint rollerPaint = new Paint();
            rollerPaint.setAntiAlias(true);//抗锯齿
            rollerPaint.setDither(true);//防抖动
            Paint mTextPaint = new Paint();
            mTextPaint.setColor(0xFFffffff);
            mTextPaint.setTextSize(mTextSize);
            float tmpAngle = 0;
            float sweepAngle = (float) (360 / 6);
            for (int i = 0; i < 6; i++) {
                drawRoller(canvas, rollerPaint, i, tmpAngle, sweepAngle);
                drawText(tmpAngle, sweepAngle, luckyBeanList.get(i).getName(), canvas, mTextPaint);
                drawIcon(tmpAngle, mImgsBitmap[i], canvas);
                tmpAngle += sweepAngle;
            }
        }
    }


    /**
     * 绘制文本
     *
     * @param startAngle
     * @param sweepAngle
     * @param string
     */
    private void drawText(float startAngle, float sweepAngle, String string, Canvas mCanvas, Paint mTextPaint) {
        Path path = new Path();
        path.addArc(mRange, startAngle, sweepAngle);
        float textWidth = mTextPaint.measureText(string);
        // 利用水平偏移让文字居中
        float hOffset = (float) (rollerRadius * Math.PI / mItemCount - textWidth / 2);// 水平偏移
        float vOffset = rollerRadius / 6;// 垂直偏移
        mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
    }


    /**
     * 根据给定的宽和高进行缩放
     *
     * @param origin    原图
     * @param newWidth  新图的宽
     * @param newHeight 新图的高
     * @return new Bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return newBM;
    }


    /**
     * 绘制icon图片
     *
     * @param startAngle
     * @param bitmap
     * @param mCanvas
     */
    private void drawIcon(float startAngle, Bitmap bitmap, Canvas mCanvas) {
        // 设置图片的宽度为直径的1/8
        int imgWidth = rollerRadius / 4;
        // 确定绘制图片的位置
        int x = centerX;
        int y = (int) (centerY - rollerRadius / 1.8);
        //将位图进行缩放
        Bitmap mBitmap = scaleBitmap(bitmap, imgWidth, imgWidth);
        mCanvas.save();//保存当前画布状态
        mCanvas.rotate(startAngle + 120, centerX, centerY);//画布绕中心点旋转
        mCanvas.drawBitmap(mBitmap, x - imgWidth / 2, y - imgWidth / 2, null);//绘制位图
        mCanvas.restore();//恢复画图状态到保存前
    }

    public int getmItemCount() {
        return luckyBeanList.size();
    }

    /**
     * 绘制圆环
     *
     * @param mCanvas
     * @param mPaint
     */
    private void drawCircle(Canvas mCanvas, Paint mPaint) {
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(circleWith);
        mPaint.setStyle(Paint.Style.STROKE);
        mCanvas.drawCircle(centerX, centerY, circleRadius - circleWith / 2, mPaint);
    }

    /**
     * 绘制转盘中的扇形
     *
     * @param mCanvas
     * @param mPaint
     * @param i
     * @param tmpAngle
     * @param sweepAngle
     */
    private void drawRoller(Canvas mCanvas, Paint mPaint, int i, float tmpAngle, float sweepAngle) {
        // 绘制快快
        mPaint.setColor(luckyBeanList.get(i).getColor());
        mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true, mPaint);
    }
}
