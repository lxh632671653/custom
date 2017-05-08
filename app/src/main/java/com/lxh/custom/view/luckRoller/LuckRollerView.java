package com.lxh.custom.view.luckRoller;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.lxh.custom.R;
import java.util.List;

/**
 * Created by lxh on 2017/3/24.
 * QQ-632671653
 */

public class LuckRollerView extends RelativeLayout {

    /**
     * 转盘控件
     */
    private LuckyRoller luckyRoller;
    /**
     * 开始图片
     */
    private ImageView imageView;
    /**
     * 旋转角度
     */
    private int rotatiion = 0;

    /**
     * 是否循环标志
     */
    private boolean isRunning = false;
    /**
     * 结果回调监听
     */
    private OnLuckyRollerListenner onLuckyRollerListenner;
    /**
     * 是否已经开始
     */
    private boolean isStart = false;
    /**
     * 是否第一次开始
     */
    private boolean isFirst = true;
    /**
     * 速度控制
     */
    private int speed = 0,sleep = 4;

    /**
     * 转盘数据
     */
    public static List<LuckyBean> dataList;

    public LuckRollerView(Context context) {
        super(context);
    }

    public LuckRollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LuckRollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 利用handler切换到UI线程进行转盘旋转
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    rotatiion += speed;
                    luckyRoller.setRotation(rotatiion);
                    break;
            }
        }
    };

    /**
     * 任务线程
     */
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (isRunning) {
                if (isStart) {
                    handler.sendEmptyMessage(1);
                    try {
                        thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });



    /**
     * 初始化控件
     * @param context
     */
    private void initView(Context context) {
        luckyRoller = new LuckyRoller(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        luckyRoller.setLayoutParams(layoutParams);
        imageView = new ImageView(context);
        LayoutParams imgLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(imgLp);
        imageView.setImageResource(R.mipmap.start);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart) {
                    pause();
                } else {
                    startOrResume();
                }
            }
        });
        addView(luckyRoller);
        addView(imageView);
    }

    /**
     * 根据旋转角度确定选中区域
     * @param rotatiion
     */
    public void calInExactArea(int rotatiion) {
        // 让指针从水平向右开始计算
        float rotate = rotatiion+90;
        int mItemCount = dataList.size();
        rotate %= 360.0;//相对于原位置的旋转角度
        for (int i = 0; i < mItemCount; i++) {
            // 每个的中奖范围
            float from = 360 - (i + 1) * (360 / mItemCount);
            float to = from + 360 - (i) * (360 / mItemCount);
            if ((rotate > from) && (rotate <= to)) {
                if (onLuckyRollerListenner!=null){
                    onLuckyRollerListenner.onFinish(dataList.get(i).getName());
                }
                return;
            }
        }
    }

    /**
     * 开始或者恢复旋转
     */
    private void startOrResume() {
        if (isFirst) {
            isRunning = true;
            isStart = true;
            isFirst = false;
            thread.start();
        } else {
            resume();
        }
        countDownTimer.start();
        stopViewTimer.start();
    }

    private void resume() {
        isStart = true;
    }

    /**
     * 暂停
     */
    private void pause() {
        isStart = false;
        speed = 0;
        imageView.setImageResource(R.mipmap.start);
        calInExactArea(rotatiion);
    }

    /**
     * 为控件设置数据
     * @param context
     * @param data
     */
    public void setData(Context context,List<LuckyBean> data){
        this.dataList = data;
        initView(context);
    }


    /**
     * 通过计时器来设定速度和最短旋转时间
     */
    private CountDownTimer countDownTimer = new CountDownTimer(sleep*1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            speed+=1;
            if (sleep>1)
            sleep-=1;
            imageView.setClickable(false);
            imageView.setImageResource(R.mipmap.stop2);
        }

        @Override
        public void onFinish() {
            imageView.setClickable(true);
            imageView.setImageResource(R.mipmap.stop);
        }
    };

    /**
     * 通过计时器来设定旋转最长时限
     */
    private CountDownTimer stopViewTimer = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (isStart){
                imageView.performClick();
            }
        }
    };

    /**
     * 中断线程，回收内存
     */
    public void destory(){
        thread.interrupt();
    }

    /**
     * 设置监听
     * @param onLuckyRollerListenner
     */
    public void setOnLuckyRollerListenner(OnLuckyRollerListenner onLuckyRollerListenner) {
        this.onLuckyRollerListenner = onLuckyRollerListenner;
    }

    public interface OnLuckyRollerListenner{
        void onFinish(String data);
    }

}
