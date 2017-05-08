package com.lxh.custom.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lxh on 2017/5/8.
 * QQ-632671653
 */

public class GoogleIconView extends View {

    //上下文
    private Context mContext;
    //控件的宽度
    private int widthView;
    //控件的高度
    private int heightView;
    //控件半径
    private int IconRadius;
    //中心坐标
    private int centerX,centerY;
    //圆环半径
    private int CircularRadius;
    //红色区域
    private Region re1=new Region();
    //黄色区域
    private Region re2=new Region();
    //绿色区域
    private Region re3=new Region();
    //各个区域画笔
    private Paint redPaint,yellowPaint,greenPaint,cyclePaint;


    public GoogleIconView(Context context) {
        super(context);
        mContext = context;
    }

    public GoogleIconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public GoogleIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取控件的宽高
        widthView = MeasureSpec.getSize(widthMeasureSpec);
        heightView = MeasureSpec.getSize(heightMeasureSpec);
        //计算中心点坐标
        centerX = widthView / 2;
        centerY = heightView / 2;
        //控件半径
        IconRadius = widthView / 2;
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        cyclePaint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCenterCycle(canvas,cyclePaint);
        drawCircular(canvas);
    }

    private void drawCenterCycle(Canvas canvas, Paint paint){
        //绘制白色底层圆环
        paint.setAntiAlias(true);//抗锯齿设置
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(IconRadius/6);
        canvas.drawCircle(widthView/2,heightView/2,IconRadius/4,paint);
        CircularRadius = IconRadius/4 + IconRadius/6/2;
        //绘制中心蓝色圆
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(widthView/2,heightView/2,IconRadius/4,paint);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawCircular(Canvas canvas){
        redPaint.setAntiAlias(true);
        /**
         * 绘制红色区域
         */
        Path path1 = new Path();
        //获取进度初始坐标
        float X1 = (float) (centerX + CircularRadius * Math.cos(2 * Math.PI * (-120 - 90) / 360));
        float Y1 = (float) (centerY + CircularRadius * Math.sin(2 * Math.PI * (-120 - 90) / 360));
        path1.moveTo(X1,Y1);
        //将小圆弧加入绘制路径
        path1.arcTo(centerX-CircularRadius,centerY-CircularRadius,centerX+CircularRadius,centerY+CircularRadius,-210,120,false);
        //获取大圆弧坐标起点
        float X2 = (float) (centerX + IconRadius * Math.cos(2 * Math.PI * (70 - 90) / 360));
        float Y2 = (float) (centerY + IconRadius * Math.sin(2 * Math.PI * (70 - 90) / 360));
        path1.lineTo(X2,Y2);
        //将大圆弧添加到绘制路径
        path1.arcTo(centerX-IconRadius,centerY-IconRadius,centerX+IconRadius,centerY+IconRadius,-20,-120,false);
        //路径闭合
        path1.close();
        canvas.drawPath(path1,redPaint);

        /**
         * 绘制黄色区域
         */
        yellowPaint.setAntiAlias(true);
        Path path2 = new Path();
        //获取进度圆弧的中心点
        float X3 = (float) (centerX + CircularRadius * Math.cos(2 * Math.PI * (-120+120 - 90) / 360));
        float Y3 = (float) (centerY + CircularRadius * Math.sin(2 * Math.PI * (-120+120 - 90) / 360));
        path2.moveTo(X3,Y3);
        path2.arcTo(centerX-CircularRadius,centerY-CircularRadius,centerX+CircularRadius,centerY+CircularRadius,-210+120,120,false);
        float X4 = (float) (centerX + IconRadius * Math.cos(2 * Math.PI * (70+120 - 90) / 360));
        float Y4 = (float) (centerY + IconRadius * Math.sin(2 * Math.PI * (70+120 - 90) / 360));
        path2.lineTo(X4,Y4);
        path2.arcTo(centerX-IconRadius,centerY-IconRadius,centerX+IconRadius,centerY+IconRadius,-20+120,-120,false);
        path2.close();
        canvas.drawPath(path2,yellowPaint);
        /**
         * 绘制绿色区域
         */
        greenPaint.setAntiAlias(true);
        Path path3 = new Path();
        //获取进度圆弧的中心点
        float X5 = (float) (centerX + CircularRadius * Math.cos(2 * Math.PI * (-120+120+120 - 90) / 360));
        float Y5 = (float) (centerY + CircularRadius * Math.sin(2 * Math.PI * (-120+120+120 - 90) / 360));
        path3.moveTo(X5,Y5);
        path3.arcTo(centerX-CircularRadius,centerY-CircularRadius,centerX+CircularRadius,centerY+CircularRadius,-210+120+120,120,false);
        float X6 = (float) (centerX + IconRadius * Math.cos(2 * Math.PI * (70+120+120 - 90) / 360));
        float Y6 = (float) (centerY + IconRadius * Math.sin(2 * Math.PI * (70+120+120 - 90) / 360));
        path3.lineTo(X6,Y6);
        path3.arcTo(centerX-IconRadius,centerY-IconRadius,centerX+IconRadius,centerY+IconRadius,-20+120+120,-120,false);
        path3.close();
        canvas.drawPath(path3,greenPaint);
        /**
         * 计算红色区域范围
         */
        RectF rectF1 = new RectF();
        //计算控制点的边界
        path1.computeBounds(rectF1,true);
        //设置区域路径并剪辑描述的区域
        re1.setPath(path1,new Region((int)rectF1.left,(int)rectF1.top,(int)rectF1.right,(int)rectF1.bottom));
        /**
         * 计算黄色区域范围
         */
        RectF rectF2 = new RectF();
        path2.computeBounds(rectF2,true);
        re2.setPath(path2,new Region((int)rectF2.left,(int)rectF2.top,(int)rectF2.right,(int)rectF2.bottom));
        /**
         * 计算绿色区域范围
         */
        RectF rectF3 = new RectF();
        path3.computeBounds(rectF3,true);
        re3.setPath(path3,new Region((int)rectF3.left,(int)rectF3.top,(int)rectF3.right,(int)rectF3.bottom));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (re1.contains((int)event.getX(),(int)event.getY())){
                redPaint.setColor(Color.BLACK);
                invalidate();
                Toast.makeText(mContext,"你点击了红色区域！",Toast.LENGTH_SHORT).show();
            }
            if (re2.contains((int)event.getX(),(int)event.getY())){
                yellowPaint.setColor(Color.BLACK);
                invalidate();
                Toast.makeText(mContext,"你点击了黄色区域！",Toast.LENGTH_SHORT).show();
            }
            if (re3.contains((int)event.getX(),(int)event.getY())){
                greenPaint.setColor(Color.BLACK);
                invalidate();
                Toast.makeText(mContext,"你点击了绿色区域！",Toast.LENGTH_SHORT).show();
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            redPaint.setColor(Color.RED);
            yellowPaint.setColor(Color.YELLOW);
            greenPaint.setColor(Color.GREEN);
            invalidate();
        }
        return true;
    }
}
