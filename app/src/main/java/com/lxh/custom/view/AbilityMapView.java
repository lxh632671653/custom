package com.lxh.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lxh on 2017/7/14.
 * QQ-632671653
 */

public class AbilityMapView extends View {

    //能力层级
    private int LAYER = 3;
    //能力数组
    private String[] ability;
    //各能力分值
    private int[] abilityScore;
    //各能力满分分值
    private int abilityFullMark = 100;
    //View宽度
    private int viewWidth;
    //View高度
    private int viewHeight;
    //中心x坐标
    private int centerX;
    //中心Y坐标
    private int centerY;
    //padding值
    private int padding;
    //大圆半径
    private int Radius;
    //各项能力颜色值
    private int[] colors = {Color.BLUE,Color.BLACK,Color.CYAN,Color.GREEN,Color.MAGENTA};
    //程序上下文
    private Context context;
    public AbilityMapView(Context context) {
        super(context);
        this.context = context;
    }

    public AbilityMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        padding = dip2px(70);
        if (viewWidth<viewHeight){
            viewHeight = viewWidth;
        }else {
            viewWidth = viewHeight;
        }
        setMeasuredDimension(viewWidth,viewHeight);
        centerX = viewWidth / 2;
        centerY = viewWidth / 2;
        Radius = viewWidth / 2 - padding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        drawLayer(canvas,paint,LAYER);
        if (ability.length>0){
            drawAbilityLine(canvas,paint,ability.length);
            if (ability.length != colors.length){
                Log.e("AbilityMapView","色值数量与能力数量不匹配!");
                return;
            }
            if (ability.length != abilityScore.length){
                Log.e("AbilityMapView","分数值数量与能力数量不匹配!");
                return;
            }
            drawAbilityMap(canvas,paint);
        }

    }

    /**
     * 绘制文字
     * @param canvas
     * @param x
     * @param y
     * @param text
     * @param color
     * @param paint
     */
    private void drawText(Canvas canvas,float x,float y,String text,int color,Paint paint){
        paint.reset();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(color);
        //绘制圆点标记
        canvas.drawCircle(x,y,dip2px(3),paint);
        paint.setTextSize(dip2px(10));
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int w = rect.width(); //获取宽度
        int h = rect.height();//获取高度
        float textX = 0;
        float textY = 0;
        //坐标点位于右上
        if (x>=centerX&&y<centerY){
            textX = x+dip2px(5);
            textY = y;
        }
        //坐标点位于右下
        if (x>=centerX&&y>=centerY){
            textX = x;
            textY = y+h+dip2px(5);
        }
        //坐标点位于左下
        if (x<centerX&&y>centerY){
            textX = x-w-dip2px(5);
            textY = y+h+dip2px(5);
        }
        //坐标点位于左上
        if (x<centerX&&y<=centerY){
            textX = x-w-dip2px(5);
            textY = y-h;
        }
        canvas.drawText(text,textX,textY,paint);
    }

    /**
     * 绘制能力分布
     * @param canvas
     * @param paint
     */
    private void drawAbilityMap(Canvas canvas,Paint paint){
        int angle = 360 / ability.length;//两种能力虚线之间的夹角
        paint.setColor(Color.parseColor("#FF435AD7"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        for (int i = 0;i<ability.length;i++){
            Path path = new Path();
            path.moveTo(centerX,centerY);
            float radius = (abilityScore[i]*Radius)/abilityFullMark;//计算每个分值在大圆半径上的长度
            //计算分值在能力虚线上的坐标
            float tempX  = (float) (centerX + radius * Math.cos(2 * Math.PI * (- 90+angle*i) / 360));
            float tempY = (float) (centerY + radius * Math.sin(2 * Math.PI * (- 90+angle*i) / 360));
            path.lineTo(tempX,tempY);
            int index = i+1;
            if (index>=abilityScore.length){
                index = 0;
            }
            float endRadius = (abilityScore[index]*Radius)/abilityFullMark;//计算每个分值在大圆半径上的长度
            //计算分值在能力虚线上的坐标
            float endX  = (float) (centerX + endRadius * Math.cos(2 * Math.PI * (- 90+angle*(index)) / 360));
            float endY = (float) (centerY + endRadius * Math.sin(2 * Math.PI * (- 90+angle*(index)) / 360));
            //构造线性渐变
            LinearGradient lg=new LinearGradient(tempX,tempY,endX,endY,Color.parseColor("#FFDF9C28"),
                    Color.parseColor("#FFECBB67"), Shader.TileMode.MIRROR);
            paint.setShader(lg);
            path.lineTo(endX,endY);
            path.close();
            canvas.drawPath(path,paint);
            //计算标记点坐标
            float flagX = (float) (centerX + (Radius+30) * Math.cos(2 * Math.PI * (- 90+angle*(i)) / 360));
            float flagY = (float) (centerY + (Radius+30) * Math.sin(2 * Math.PI * (- 90+angle*(i)) / 360));
            drawText(canvas,flagX,flagY,ability[i]+" "+abilityScore[i],colors[i],paint);
        }
    }

    /**
     * 绘制层级圆
     * @param canvas
     * @param paint
     * @param layer
     */
    private void drawLayer(Canvas canvas, Paint paint,int layer){
        int radius;
        paint.setColor(Color.parseColor("#FFcccccc"));
        paint.setStrokeWidth(dip2px(1));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        for(int i = 1;i<=layer;i++){
            radius = (Radius/layer)*i;
            canvas.drawCircle(centerX,centerY,radius,paint);
        }
    }

    /**
     * 绘制能力虚线
     * @param canvas
     * @param paint
     * @param abilityNumber
     */
    private void drawAbilityLine(Canvas canvas,Paint paint,int abilityNumber){
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.parseColor("#FFcccccc"));
        paint.setAntiAlias(true);//抗锯齿
        paint.setPathEffect(new DashPathEffect(new float[] {15, 15}, 0));//设置虚线15sp实线，15sp虚线
        int angle = 360 / abilityNumber;//两种能力虚线之间的夹角
        float X1;//能力虚线在大圆上的X坐标
        float Y1;//能力虚线在大圆上的Y坐标
        for(int i = 0;i<abilityNumber;i++){
            X1 = (float) (centerX + Radius * Math.cos(2 * Math.PI * (- 90+angle*i) / 360));
            Y1 = (float) (centerY + Radius * Math.sin(2 * Math.PI * (- 90+angle*i) / 360));
            Path mPath = new Path();
            mPath.reset();
            mPath.moveTo(centerX, centerY);//设置中心点为虚线路径起点
            mPath.lineTo(X1, Y1);//设置圆上坐标点为虚线路径终点
            canvas.drawPath(mPath, paint);
        }
    }

    //设置能力分级
    public void setLayer(int layer){
        this.LAYER = layer;
        postInvalidate();
    }

    public void setAbility(String[] ability) {
        this.ability = ability;
        postInvalidate();
    }

    public void setAbilityScore(int[] abilityScore) {
        this.abilityScore = abilityScore;
        postInvalidate();
    }

    public void setAbilityFullMark(int abilityFullMark) {
        this.abilityFullMark = abilityFullMark;
        postInvalidate();
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        postInvalidate();
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public int dip2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
